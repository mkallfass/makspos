<template>
  <div class="app-container">
    <!-- Header -->
    <AppNavbar />

    <!-- Content -->
    <div>
      <RouterView />
    </div>
  </div>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppNavbar from './components/layout/AppNavbar.vue'
</script>

<style>
</style>
