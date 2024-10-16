<template>
  <div class="text-center font-bold text-xl">
    <h1>{{ getLabel('statistics.title') }}</h1>
  </div>
  <Card>
    <template #content>
      <div class="grid grid-cols-12 gap-4 font-bold text-xl">
        <div class="col-span-6">{{ getLabel('statistics.ordercount') }}</div>
        <div class="col-span-6" style="text-align: right">{{ orderStatistics.orderCount }}</div>
      </div>
      <div class="grid grid-cols-12 gap-4 font-bold text-xl">
        <div class="col-span-6">{{ getLabel('statistics.overallrevenue') }}</div>
        <div class="col-span-6" style="text-align: right">
          {{ formatCurrency(orderStatistics.overallRevenue) }}
        </div>
      </div>
    </template>
  </Card>
  <DataTable :value="orderStatistics.productStatistics" showGridlines stripedRows>
    <Column :header="getLabel('statistics.productname')" field="name">
      <template #body="slotProps">
        {{ slotProps.data.name }} <small>{{ slotProps.data.description }}</small>
      </template>
    </Column>
    <Column :header="getLabel('statistics.orderedcount')" field="orderedCount" sortable>
      <template #body="slotProps">
        <div style="text-align: right">{{ slotProps.data.orderedCount }}</div>
      </template>
    </Column>
    <Column :header="getLabel('statistics.revenue')" field="revenue" sortable>
      <template #body="slotProps">
        <div style="text-align: right">{{ formatCurrency(slotProps.data.revenue) }}</div>
      </template>
    </Column>
  </DataTable>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { usePosStore } from '@/stores/pos.store'
import { formatCurrency, getLabel } from '@/utils'

const posStore = usePosStore()
const { orderStatistics } = storeToRefs(posStore)

posStore.fetchStatistics()
</script>
