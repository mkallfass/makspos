<template>
  <Menubar :model="items" class="bg-zinc-700 text-white">
    <template #start>
      <div class="text-2xl font-bold flex items-center gap-2 mr-4">
        <img alt="logo" src="https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png" class="h-10">
        APP
      </div>
    </template>
    <template #item="{ item }">
      <RouterLink :to="item.to" custom v-slot="{ href, navigate, isActive, isExactActive }">
        <a :href="href" @click="navigate" class="m-1 p-2 rounded hover:bg-zinc-500 hover:text-white align-middle" :class="[{ 'active-link': isActive, 'active-link-exact': isExactActive }, isActive ? 'underline decoration-2 underline-offset-4 decoration-primary' : 'text-white/60']">{{ item.label }}</a>
      </RouterLink>
    </template>
    <template #end>
      <InputText placeholder="Search" type="text" />
    </template>
  </Menubar>
</template>

<script setup lang="ts">
  import { RouterLink } from 'vue-router'
  import { ref } from 'vue'
  const items = ref([
    {
      label:'Home',
      to: '/'
    }
  ])
</script>

<style scoped>
  .p-menubar {
    border: 0;
    border-radius: 0;
  }
</style>
