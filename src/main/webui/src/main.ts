import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';
import Menubar from 'primevue/menubar'
import Card from 'primevue/card'
import Dialog from 'primevue/dialog'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'

import './assets/main.css'
import 'primevue/resources/themes/saga-blue/theme.css';
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
app.component('Menubar', Menubar)
app.component('Card', Card)
app.component('Dialog', Dialog)
app.component('Button', Button)
app.component('InputText', InputText)

app.mount('#app')
