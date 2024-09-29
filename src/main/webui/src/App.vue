<template>
  <div class="app-container">
    <Toolbar>
      <template #center>
        <div class="font-bold text-xl">{{ getLabel('app.title') }}</div>
      </template>
      <template #end>
        <Button
          aria-controls="overlay_menu"
          aria-haspopup="true"
          icon="pi pi-bars"
          type="button"
          @click="toggle"
        />
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
        <div>{{ getLabel('app.footer') }}</div>
      </template>
    </Toolbar>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { RouterView } from 'vue-router'
import router from '@/router'
import { getLabel } from '@/utils'

const menu = ref()
const items = [
  {
    label: getLabel('app.menu.order'),
    icon: 'pi pi-pen-to-square',
    command: () => {
      router.push({ name: 'order-form' })
    }
  },
  {
    label: getLabel('app.menu.statistics'),
    icon: 'pi pi-chart-line',
    command: () => {
      router.push({ name: 'statistics' })
    }
  }
]

const toggle = (event: any) => {
  menu.value.toggle(event)
}
</script>
