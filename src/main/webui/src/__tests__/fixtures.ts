import type { LineItem } from '@/models/pos.model'

/**
 * Shape of the JSON payload that `GET /api/config` returns and that the app keeps in
 * session storage: labels are a plain object there and only become a `Map` in `Config`.
 */
export const configJson = {
  currency: 'EUR',
  paymentPresets: [5, 10, 20],
  labels: {
    'cart.title': 'Bestellung',
    'cart.total': 'Summe',
    'payment.title': 'Bezahlung',
    'payment.payed': 'Bezahlt',
    'payment.change': 'Rückgeld'
  }
}

export const storeConfig = (config: unknown = configJson) =>
  sessionStorage.setItem('config', JSON.stringify(config))

/** Overrides `navigator.language`, which `formatCurrency` reads for locale-aware output. */
export const setNavigatorLanguage = (language: string) =>
  Object.defineProperty(window.navigator, 'language', { value: language, configurable: true })

export const lineItem = (overrides: Partial<LineItem> & Pick<LineItem, 'id'>): LineItem => ({
  name: `Product ${overrides.id}`,
  description: '',
  price: 1,
  quantity: 0,
  total: 0,
  ...overrides
})
