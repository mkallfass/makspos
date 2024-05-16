import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';
import Toolbar from 'primevue/toolbar'
import Splitter from 'primevue/splitter';
import SplitterPanel from 'primevue/splitterpanel';
import Card from 'primevue/card'
import Dialog from 'primevue/dialog'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'

import './assets/main.css'
import 'primevue/resources/themes/aura-light-green/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PrimeVue, {
  ripple: true
});
app.use(ToastService);

app.component('Button', Button);
app.component('Toast', Toast);
app.component('Toolbar', Toolbar)
app.component('Splitter', Splitter)
app.component('SplitterPanel', SplitterPanel)
app.component('Card', Card)
app.component('Dialog', Dialog)
app.component('Button', Button)
app.component('InputText', InputText)

app.mount('#app')
