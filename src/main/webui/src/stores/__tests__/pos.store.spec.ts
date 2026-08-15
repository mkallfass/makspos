import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { createPinia, setActivePinia } from 'pinia'
import { usePosStore } from '@/stores/pos.store'
import type { LineItem, OrderStatistics } from '@/models/pos.model'
import { lineItem } from '@/__tests__/fixtures'

const products: LineItem[] = [
  lineItem({ id: 1, name: 'Bier', price: 3.5 }),
  lineItem({ id: 2, name: 'Wurst', price: 2 }),
  lineItem({ id: 3, name: 'Brezel', price: 1.5 })
]

const statistics: OrderStatistics = {
  orderCount: 2,
  overallRevenue: 12.5,
  productStatistics: [{ id: 1, name: 'Bier', description: '', orderedCount: 3, revenue: 10.5 }]
}

/** Minimal `fetch` double that answers the three endpoints the store talks to. */
const stubFetch = (payloads: Record<string, unknown> = {}) => {
  const fetchMock = vi.fn((...args: [url: string, init?: RequestInit]) =>
    Promise.resolve({
      ok: true,
      statusText: 'OK',
      json: async () => payloads[args[0]] ?? []
    })
  )
  vi.stubGlobal('fetch', fetchMock)
  return fetchMock
}

describe('pos store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    vi.spyOn(console, 'info').mockImplementation(() => {})
  })

  afterEach(() => {
    vi.unstubAllGlobals()
    vi.restoreAllMocks()
  })

  describe('cartLineItems', () => {
    it('keeps only selected items and totals them up', () => {
      const store = usePosStore()
      store.lineitems = [
        { ...products[0], quantity: 2 },
        { ...products[1], quantity: 0 },
        { ...products[2], quantity: 3 }
      ]

      const cart = store.cartLineItems

      expect(cart.map((item) => item.name)).toEqual(['Bier', 'Brezel'])
      expect(cart.map((item) => item.total)).toEqual([7, 4.5])
      expect(store.total).toBe(11.5)
    })

    it('normalises items without a quantity instead of producing NaN totals', () => {
      const store = usePosStore()
      store.lineitems = [{ ...products[0], quantity: undefined as unknown as number }]

      expect(store.cartLineItems).toEqual([])
      expect(store.lineitems[0].quantity).toBe(0)
      expect(store.lineitems[0].total).toBe(0)
      expect(store.total).toBe(0)
    })

    it('is empty and free of charge when nothing is selected', () => {
      const store = usePosStore()
      store.lineitems = products.map((product) => ({ ...product }))

      expect(store.cartLineItems).toEqual([])
      expect(store.total).toBe(0)
    })

    it('reflects a quantity change on a subsequent read', () => {
      const store = usePosStore()
      store.lineitems = [{ ...products[0], quantity: 1 }]
      expect(store.total).toBe(0)

      void store.cartLineItems
      expect(store.total).toBe(3.5)

      store.lineitems[0].quantity = 4
      expect(store.cartLineItems[0].total).toBe(14)
      expect(store.total).toBe(14)
    })
  })

  describe('returnAmount', () => {
    const cartWorth = (store: ReturnType<typeof usePosStore>, total: number) => {
      store.lineitems = [{ ...products[0], price: total, quantity: 1 }]
      void store.cartLineItems
    }

    it('is zero as long as nothing has been paid', () => {
      const store = usePosStore()
      cartWorth(store, 11.5)

      expect(store.returnAmount).toBe(0)
    })

    it('is the change owed to the customer', () => {
      const store = usePosStore()
      cartWorth(store, 11.5)
      store.givenAmount = 20

      expect(store.returnAmount).toBe(8.5)
    })

    it('goes negative when the customer paid too little', () => {
      const store = usePosStore()
      cartWorth(store, 11.5)
      store.givenAmount = 10

      expect(store.returnAmount).toBe(-1.5)
    })
  })

  describe('fetchProducts', () => {
    it('fills the line items from the API', async () => {
      stubFetch({ '/api/products': products })
      const store = usePosStore()

      store.fetchProducts()

      await vi.waitFor(() => expect(store.lineitems).toHaveLength(3))
      expect(store.lineitems.map((item) => item.name)).toEqual(['Bier', 'Wurst', 'Brezel'])
    })

    it('keeps the previous line items when the API fails', async () => {
      const error = vi.spyOn(console, 'error').mockImplementation(() => {})
      vi.stubGlobal(
        'fetch',
        vi.fn().mockResolvedValue({ ok: false, statusText: 'Internal Server Error' })
      )
      const store = usePosStore()
      store.lineitems = [{ ...products[0] }]

      store.fetchProducts()

      await vi.waitFor(() => expect(error).toHaveBeenCalled())
      expect(store.lineitems).toHaveLength(1)
    })
  })

  describe('fetchStatistics', () => {
    it('fills the order statistics from the API', async () => {
      stubFetch({ '/api/orders/statistics': statistics })
      const store = usePosStore()

      store.fetchStatistics()

      await vi.waitFor(() => expect(store.orderStatistics.orderCount).toBe(2))
      expect(store.orderStatistics.overallRevenue).toBe(12.5)
      expect(store.orderStatistics.productStatistics).toHaveLength(1)
    })
  })

  describe('order', () => {
    it('posts only the selected items with their total and resets the cart', async () => {
      const fetchMock = stubFetch({ '/api/products': [] })
      const store = usePosStore()
      store.lineitems = [
        { ...products[0], quantity: 2 },
        { ...products[1], quantity: 0 }
      ]
      store.givenAmount = 20

      store.order()

      const post = fetchMock.mock.calls.find(([url]) => url === '/api/orders')
      expect(post).toBeDefined()
      const body = JSON.parse(post![1]!.body as string)
      expect(body.lineitems).toHaveLength(1)
      expect(body.lineitems[0]).toMatchObject({ name: 'Bier', quantity: 2, total: 7 })
      expect(body.total).toBe(7)

      expect(store.total).toBe(0)
      expect(store.givenAmount).toBe(0)
      await vi.waitFor(() => expect(store.lineitems).toEqual([]))
    })
  })
})
