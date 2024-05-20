<template>
  <div class="app-container">
    <!-- Header -->
    <Toolbar>
      <template #center>
        <div class="font-bold text-xl">{{ configStore.title }}</div>
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

<script setup lang="ts">
import { RouterView } from "vue-router";
import { useConfigStore } from "@/stores/config.store";

const configStore = useConfigStore();
</script>

<style>
</style>
