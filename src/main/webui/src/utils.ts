import type { Config } from "@/models/config.model";

export const formatCurrency = (value : any, config : Config) => {
  return value.toLocaleString(config.locale, { style: 'currency', currency: config.currency })
}