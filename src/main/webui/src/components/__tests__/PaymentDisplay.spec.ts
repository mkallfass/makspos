import { afterEach, beforeEach, describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import { createPinia, setActivePinia } from 'pinia'
import PrimeVue from 'primevue/config'
import Card from 'primevue/card'
import InputNumber from 'primevue/inputnumber'
import Button from 'primevue/button'
import PaymentDisplay from '@/components/PaymentDisplay.vue'
import { usePosStore } from '@/stores/pos.store'
import { lineItem, setNavigatorLanguage, storeConfig } from '@/__tests__/fixtures'

const mountPayment = () =>
  mount(PaymentDisplay, {
    global: {
      plugins: [PrimeVue],
      components: { Card, InputNumber, Button }
    }
  })

/** Puts a single item worth `total` into the cart and lets the getter compute the total. */
const fillCart = (store: ReturnType<typeof usePosStore>, total: number) => {
  store.lineitems = [lineItem({ id: 1, name: 'Bier', price: total, quantity: 1 })]
  void store.cartLineItems
}

describe('PaymentDisplay', () => {
  const originalLanguage = navigator.language

  beforeEach(() => {
    setActivePinia(createPinia())
    sessionStorage.clear()
    storeConfig()
    setNavigatorLanguage('en-US')
  })

  afterEach(() => setNavigatorLanguage(originalLanguage))

  it('shows the change once an amount has been paid', async () => {
    const store = usePosStore()
    fillCart(store, 11.5)
    const wrapper = mountPayment()

    store.givenAmount = 20
    await wrapper.vm.$nextTick()

    expect(wrapper.text()).toContain('€8.50')
  })

  it('marks a shortfall in red', async () => {
    const store = usePosStore()
    fillCart(store, 11.5)
    const wrapper = mountPayment()

    store.givenAmount = 10
    await wrapper.vm.$nextTick()

    expect(wrapper.text()).toContain('-€1.50')
    expect(wrapper.find('.text-red-500').exists()).toBe(true)
  })

  it('shows no change before anything has been paid', () => {
    const store = usePosStore()
    fillCart(store, 11.5)

    const wrapper = mountPayment()

    expect(wrapper.text()).toContain('€0.00')
    expect(wrapper.find('.text-red-500').exists()).toBe(false)
  })

  it('renders a button per configured payment preset', () => {
    const wrapper = mountPayment()

    const labels = wrapper.findAll('button').map((button) => button.text())
    expect(labels).toEqual(['€5.00', '€10.00', '€20.00'])
  })

  it('takes over the amount of a clicked preset', async () => {
    const store = usePosStore()
    fillCart(store, 11.5)
    const wrapper = mountPayment()

    await wrapper.findAll('button')[2].trigger('click')

    expect(store.givenAmount).toBe(20)
    expect(wrapper.text()).toContain('€8.50')
  })
})
