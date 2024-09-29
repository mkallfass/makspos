<template>
  <Card>
    <template #title>
      <div class="bg-surface-50 dark:bg-surface-950">{{ getLabel('cart.title') }}</div>
    </template>
    <template #content>
      <DataTable :value="cartLineItems" showGridlines stripedRows>
        <Column :header="getLabel('cart.quantity')" field="quantity" style="width: 10%" />
        <Column :header="getLabel('cart.name')" field="name" style="width: 70%">
          <template #body="slotProps">
            {{ slotProps.data.name }}
          </template>
        </Column>
        <Column :header="getLabel('cart.price')" field="price" style="width: 10%">
          <template #body="slotProps">
            <div style="text-align: right">{{ formatCurrency(slotProps.data.price) }}</div>
          </template>
        </Column>
        <Column :header="getLabel('cart.total')" field="total" style="width: 10%">
          <template #body="slotProps">
            <div style="text-align: right">{{ formatCurrency(slotProps.data.total) }}</div>
          </template>
        </Column>
        <template #footer>
          <div class="grid grid-cols-12 gap-4 font-bold text-xl">
            <div class="col-span-6">{{ getLabel('cart.total') }}</div>
            <div class="col-span-6" style="text-align: right">
              {{ formatCurrency(posStore.total) }}
            </div>
          </div>
        </template>
      </DataTable>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { usePosStore } from '@/stores/pos.store'
import { formatCurrency, getLabel } from '@/utils'

const posStore = usePosStore()
const { cartLineItems } = storeToRefs(posStore)
</script>
