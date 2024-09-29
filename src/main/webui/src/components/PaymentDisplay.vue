<template>
  <Card>
    <template #title>
      <div class="bg-surface-50 dark:bg-surface-950 font-bold text-xl">
        {{ getLabel('payment.title') }}
      </div>
    </template>
    <template #content>
      <div class="grid grid-cols-12 gap-4 font-bold text-xl">
        <div class="col-span-6">{{ getLabel('payment.payed') }}</div>
        <div class="col-span-6 md:col-start-10 md:col-span-3">
          <InputNumber
            v-model="posStore.givenAmount"
            :currency="getConfig().currency"
            :inputStyle="{
              width: '100%',
              'text-align': 'right',
              'font-size': '1.25rem',
              'line-height': '1.75rem'
            }"
            mode="currency"
          />
        </div>
        <div v-for="p in getConfig().paymentPresets" :key="p" class="col-span-4 xl:col-span-2">
          <Button
            :label="formatCurrency(p)"
            rounded
            severity="secondary"
            style="width: 100%"
            @click="posStore.givenAmount = p"
          />
        </div>
        <div class="col-span-6">{{ getLabel('payment.change') }}</div>
        <div
          class="col-span-6"
          :class="{ 'text-red-500': returnAmount < 0 }"
          style="text-align: right"
        >
          {{ formatCurrency(returnAmount) }}
        </div>
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { usePosStore } from '@/stores/pos.store'
import { formatCurrency, getLabel } from '@/utils'
import { getConfig } from '@/config'

const posStore = usePosStore()
const { returnAmount } = storeToRefs(posStore)
</script>
