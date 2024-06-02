<template>
  <div class="app-container">
    <!-- Header -->
    <Toolbar>
      <template #center>
        <div class="font-bold text-xl">{{ configStore.title }}</div>
      </template>
      <template #end>
        <Button aria-controls="overlay_menu" aria-haspopup="true" icon="pi pi-bars" type="button" @click="toggle" />
        <Menu id="overlay_menu" ref="menu" :model="items" :popup="true" />
      </template>
    </Toolbar>

    <!-- Content -->
    <div>
      <RouterView />
    </div>

    <!-- Footer -->
    <Toolbar>
      <template #center>
        <div>{{ configStore.footer }}</div>
      </template>
    </Toolbar>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { RouterView } from "vue-router";
import { useConfigStore } from "@/stores/config.store";
import router from "@/router";

const configStore = useConfigStore();

const menu = ref();
const items = [
  {
    label: "Bestellung erfassen",
    icon: "pi pi-pen-to-square",
    command: () => {
      router.push({ name: "order-form" });
    }
  },
  {
    label: "Bestellstatistiken",
    icon: "pi pi-chart-line",
    command: () => {
      router.push({ name: "statistics" });
    }
  }
];

const toggle = (event: any) => {
  menu.value.toggle(event);
};
</script>
