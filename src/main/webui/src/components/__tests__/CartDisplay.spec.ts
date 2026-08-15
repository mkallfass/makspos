import { afterEach, beforeEach, describe, expect, it } from 'vitest'
import { mount } from '@vue/test-utils'
import { createPinia, setActivePinia } from 'pinia'
import PrimeVue from 'primevue/config'
import Card from 'primevue/card'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import CartDisplay from '@/components/CartDisplay.vue'
import { usePosStore } from '@/stores/pos.store'
import { lineItem, setNavigatorLanguage, storeConfig } from '@/__tests__/fixtures'

const mountCart = () =>
  mount(CartDisplay, {
    global: {
      plugins: [PrimeVue],
      components: { Card, DataTable, Column }
    }
  })

describe('CartDisplay', () => {
  const originalLanguage = navigator.language

  beforeEach(() => {
    setActivePinia(createPinia())
    sessionStorage.clear()
    storeConfig()
    setNavigatorLanguage('en-US')
  })

  afterEach(() => setNavigatorLanguage(originalLanguage))

  it('lists only the selected items with their line and cart totals', () => {
    const store = usePosStore()
    store.lineitems = [
      lineItem({ id: 1, name: 'Bier', price: 3.5, quantity: 2 }),
      lineItem({ id: 2, name: 'Wurst', price: 2, quantity: 0 }),
      lineItem({ id: 3, name: 'Brezel', price: 1.5, quantity: 3 })
    ]

    const text = mountCart().text()

    expect(text).toContain('Bier')
    expect(text).toContain('Brezel')
    expect(text).not.toContain('Wurst')
    // 2 × 3.50 and 3 × 1.50, summing to 11.50
    expect(text).toContain('€7.00')
    expect(text).toContain('€4.50')
    expect(text).toContain('€11.50')
  })

  it('uses the configured labels for the header', () => {
    const text = mountCart().text()
    expect(text).toContain('Bestellung')
    expect(text).toContain('Summe')
  })
})
