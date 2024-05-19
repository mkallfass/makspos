<template>
  <div class="app-container">
    <!-- Header -->
    <Toolbar>
      <template #center>
        <div class="font-bold text-xl">Mini POS</div>
      </template>
    </Toolbar>

    <!-- Content -->
    <div>
      <RouterView />
    </div>

    <!-- Footer -->
    <Toolbar>
      <template #center>
        <div>Made with ♥️ in Baiersbronn, Germany</div>
      </template>
    </Toolbar>
  </div>
</template>

<script setup lang="ts">
import { RouterView } from "vue-router";
</script>

<style>
</style>
