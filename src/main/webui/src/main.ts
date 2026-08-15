import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import Aura from '@primeuix/themes/aura'
import ToastService from 'primevue/toastservice'
import Toast from 'primevue/toast'
import Toolbar from 'primevue/toolbar'
import Menu from 'primevue/menu'
import Card from 'primevue/card'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import InputNumber from 'primevue/inputnumber'
import Button from 'primevue/button'

import './assets/main.css'
import 'primeicons/primeicons.css'
import { fetchConfig } from '@/config'

await fetchConfig()

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ToastService)
// No `cssLayer` here on purpose. The block that used to sit at this level was never read —
// PrimeVue expects it under `theme.options`, so the app has always run with PrimeVue's CSS
// unlayered. That is also the setup we want: Tailwind 4 emits its preflight into its own `base`
// layer, which is not part of a `tailwind-base, primevue, tailwind-utilities` ordering and would
// therefore win over a layered `primevue`, leaving buttons and cards unstyled.
app.use(PrimeVue, {
  theme: {
    preset: Aura
  },
  ripple: true
})

// PrimeVue components are registered under their canonical vendor names. These are
// single-word and partly reserved HTML names by design; renaming them here would break
// every template that uses them, so both naming rules are switched off for this block.
/* eslint-disable vue/multi-word-component-names, vue/no-reserved-component-names */
app.component('Toast', Toast)
app.component('Toolbar', Toolbar)
app.component('Button', Button)
app.component('Menu', Menu)
app.component('Card', Card)
app.component('DataTable', DataTable)
app.component('Column', Column)
app.component('InputNumber', InputNumber)
/* eslint-enable vue/multi-word-component-names, vue/no-reserved-component-names */

app.mount('#app')
