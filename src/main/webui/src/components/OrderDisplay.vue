<template>
  <Card>
    <template #content>
      <div class="grid ">
        <div class="col-6 xl:col-3 col-offset-0 xl:col-offset-6">
          <Button label="Abbrechen" severity="secondary" @click="posStore.reset()" style="width: 100%" />
        </div>
        <div class="col-6 xl:col-3">
          <Button label="Abschließen" @click="posStore.order()" style="width: 100%" />
        </div>
      </div>
    </template>
  </Card>


</template>

<script lang="ts" setup>
import { usePosStore } from "@/stores/pos.store";

const posStore = usePosStore()
</script>

