import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import PrimeVue from "primevue/config";
import ToastService from "primevue/toastservice";
import Toast from "primevue/toast";
import Toolbar from "primevue/toolbar";
import Menu from "primevue/menu";
import Card from "primevue/card";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import InputNumber from "primevue/inputnumber";
import Button from "primevue/button";

import "./assets/main.css";
import "primevue/resources/themes/aura-light-green/theme.css";
import "primevue/resources/primevue.min.css";
import "primeflex/primeflex.css";
import "primeicons/primeicons.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(ToastService);
app.use(PrimeVue, {
  ripple: true
});

app.component("Toast", Toast);
app.component("Toolbar", Toolbar);
app.component("Button", Button);
app.component("Menu", Menu);
app.component("Card", Card);
app.component("DataTable", DataTable);
app.component("Column", Column);
app.component("InputNumber", InputNumber);

app.mount("#app");