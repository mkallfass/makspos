<template>
  <Card>
    <template #content>
      <div class="flex align-items-end justify-content-end">
        <Button label="Stornieren" severity="secondary"  />
        <Button label="Abschließen"  />
      </div>
    </template>
  </Card>


</template>

<script lang="ts" setup>
// @click="posStore.reset()"
// @click="posStore.order()"
import { usePosStore } from "@/stores/pos.store";

const posStore = usePosStore()
</script>

