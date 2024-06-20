<template>
  <Card>
    <template #content>
      <div class="grid ">
        <div class="col-6 xl:col-3 col-offset-0 xl:col-offset-6">
          <Button :label="getLabel('order.cancel')" severity="secondary" style="width: 100%"
                  @click="posStore.$reset()" />
        </div>
        <div class="col-6 xl:col-3">
          <Button :label="getLabel('order.order')" style="width: 100%" @click="posStore.order()" />
        </div>
      </div>
    </template>
  </Card>


</template>

<script lang="ts" setup>
import { usePosStore } from "@/stores/pos.store";
import { getLabel } from "@/utils";

const posStore = usePosStore();
</script>

