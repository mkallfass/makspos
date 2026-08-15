import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { fetchConfig, getConfig, jsonToConfig } from '@/config'
import { configJson, storeConfig } from './fixtures'

describe('jsonToConfig', () => {
  it('turns the labels object into a Map and keeps the other fields', () => {
    const config = jsonToConfig(JSON.stringify(configJson))

    expect(config.currency).toBe('EUR')
    expect(config.paymentPresets).toEqual([5, 10, 20])
    expect(config.labels).toBeInstanceOf(Map)
    expect(config.labels.get('cart.title')).toBe('Bestellung')
    expect(config.labels.size).toBe(Object.keys(configJson.labels).length)
  })

  it('produces an empty label Map when the config carries no labels', () => {
    const config = jsonToConfig(JSON.stringify({ ...configJson, labels: {} }))
    expect(config.labels.size).toBe(0)
  })
})

describe('getConfig', () => {
  beforeEach(() => sessionStorage.clear())

  it('reads the config from session storage', () => {
    storeConfig()
    const config = getConfig()

    expect(config.currency).toBe('EUR')
    expect(config.labels.get('payment.payed')).toBe('Bezahlt')
  })

  it('returns an empty config when session storage is empty', () => {
    const error = vi.spyOn(console, 'error').mockImplementation(() => {})

    expect(getConfig()).toEqual({})
    expect(error).toHaveBeenCalled()
    error.mockRestore()
  })
})

describe('fetchConfig', () => {
  beforeEach(() => sessionStorage.clear())
  afterEach(() => vi.unstubAllGlobals())

  it('stores the API response in session storage', async () => {
    const fetchMock = vi.fn().mockResolvedValue({ ok: true, json: async () => configJson })
    vi.stubGlobal('fetch', fetchMock)

    await fetchConfig()

    expect(fetchMock).toHaveBeenCalledWith('/api/config')
    expect(sessionStorage.getItem('config')).toBe(JSON.stringify(configJson))
    expect(getConfig().currency).toBe('EUR')
  })
})
