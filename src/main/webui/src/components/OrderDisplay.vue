<template>
  <Card>
    <template #content>
      <div class="flex align-items-end justify-content-end">
        <Button label="Stornieren" severity="secondary" @click="posStore.reset()" />
        <Button label="Abschließen" @click="posStore.order()" />
      </div>
    </template>
  </Card>


</template>

<script lang="ts" setup>
import { usePosStore } from "@/stores/pos.store";

const posStore = usePosStore()
</script>

