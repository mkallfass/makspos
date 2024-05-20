import type { Config } from "@/models/config.model";

export const CONFIG: Config =
  {
    "locale": "de-DE",
    "currency": "EUR",
    "title": "Mini POS (OGV Baiersbronn)",
    "footer": "Made with ♥️ in Baiersbronn, Germany",
    "paymentPresets": [5, 10, 15, 20, 25, 30]
  } as Config;