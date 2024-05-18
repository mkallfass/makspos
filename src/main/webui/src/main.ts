import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import PrimeVue from "primevue/config";
import ToastService from "primevue/toastservice";
import Toast from "primevue/toast";
import Toolbar from "primevue/toolbar";
import Splitter from "primevue/splitter";
import SplitterPanel from "primevue/splitterpanel";
import Card from "primevue/card";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import InputNumber from "primevue/inputnumber";
import Dialog from "primevue/dialog";
import Button from "primevue/button";
import InputText from "primevue/inputtext";

import "./assets/main.css";
import "primevue/resources/themes/aura-light-green/theme.css";
import "primevue/resources/primevue.min.css";
import "primeicons/primeicons.css";

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ToastService);
app.use(PrimeVue, {
  ripple: true
});

app.component('Toolbar', Toolbar)
app.component('Splitter', Splitter)
app.component('SplitterPanel', SplitterPanel)
app.component('Card', Card)
app.component("DataTable", DataTable);
app.component("Column", Column);
app.component("InputNumber", InputNumber);
app.component("Toast", Toast);
app.component('Dialog', Dialog)
app.component('Button', Button)
app.component('InputText', InputText)

app.mount('#app')
