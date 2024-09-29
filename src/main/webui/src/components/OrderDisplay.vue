<template>
  <Card>
    <template #content>
      <div class="grid grid-cols-12 gap-4">
        <div class="col-span-6 xl:col-span-3 col-start-1 xl:col-start-7">
          <Button
            :label="getLabel('order.cancel')"
            severity="secondary"
            style="width: 100%"
            @click="posStore.$reset()"
          />
        </div>
        <div class="col-span-6 xl:col-span-3">
          <Button :label="getLabel('order.order')" style="width: 100%" @click="posStore.order()" />
        </div>
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { usePosStore } from '@/stores/pos.store'
import { getLabel } from '@/utils'

const posStore = usePosStore()
</script>
