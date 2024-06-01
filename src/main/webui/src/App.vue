<template>
  <div class="app-container">
    <!-- Header -->
    <Toolbar>
      <template #center>
        <div class="font-bold text-xl">{{ configStore.title }}</div>
      </template>
      <template #end>
        <SplitButton :model="items" label="Menu" />
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
import { RouterView } from "vue-router";
import { useConfigStore } from "@/stores/config.store";
import router from "@/router";

const configStore = useConfigStore();

const items = [
  {
    label: "Bestellung erfassen", command: () => {
      router.push("/order-form");
    }
  },
  {
    label: "Bestellstatistiken", command: () => {
      router.push("/statistics");
    }
  }
];
</script>
