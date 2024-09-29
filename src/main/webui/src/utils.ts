import { getConfig } from '@/config'

export const getLabel = (key: string) => {
  const config = getConfig()
  if (config.labels && config.labels.has(key)) {
    return config.labels.get(key)
  }
  return 'KEY ' + key + ' NOT AVAILABLE'
}

export const formatCurrency = (value: any) => {
  return value.toLocaleString(navigator.language, {
    style: 'currency',
    currency: getConfig().currency
  })
}
