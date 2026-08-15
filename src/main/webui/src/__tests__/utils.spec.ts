import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { formatCurrency, getLabel } from '@/utils'
import { configJson, setNavigatorLanguage, storeConfig } from './fixtures'

describe('getLabel', () => {
  beforeEach(() => sessionStorage.clear())

  it('returns the label for a known key', () => {
    storeConfig()
    expect(getLabel('payment.change')).toBe('Rückgeld')
  })

  it('falls back to a marker for an unknown key', () => {
    storeConfig()
    expect(getLabel('does.not.exist')).toBe('KEY does.not.exist NOT AVAILABLE')
  })

  it('falls back when no config is stored at all', () => {
    const error = vi.spyOn(console, 'error').mockImplementation(() => {})
    expect(getLabel('cart.title')).toBe('KEY cart.title NOT AVAILABLE')
    expect(error).toHaveBeenCalled()
    error.mockRestore()
  })
})

describe('formatCurrency', () => {
  const originalLanguage = navigator.language

  beforeEach(() => sessionStorage.clear())
  afterEach(() => setNavigatorLanguage(originalLanguage))

  it('formats using the currency from the config', () => {
    storeConfig()
    setNavigatorLanguage('en-US')
    expect(formatCurrency(12.5)).toBe('€12.50')
  })

  it('honours a different currency from the config', () => {
    storeConfig({ ...configJson, currency: 'USD' })
    setNavigatorLanguage('en-US')
    expect(formatCurrency(12.5)).toBe('$12.50')
  })

  it('formats according to the browser locale', () => {
    storeConfig()
    setNavigatorLanguage('de-DE')
    // de-DE puts the symbol last and separates it with a non-breaking space
    expect(formatCurrency(1234.5)).toMatch(/^1\.234,50\s€$/)
  })

  it('renders negative amounts, as used for an insufficient payment', () => {
    storeConfig()
    setNavigatorLanguage('en-US')
    expect(formatCurrency(-2.5)).toBe('-€2.50')
  })
})
