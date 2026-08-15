<template>
  <DataTable :value="lineitems" showGridlines stripedRows>
    <Column :header="getLabel('productlist.name')" field="name">
      <template #body="slotProps">
        {{ slotProps.data.name }} <small>{{ slotProps.data.description }}</small>
      </template>
    </Column>
    <Column :header="getLabel('productlist.price')" field="price">
      <template #body="slotProps">
        <div style="text-align: right">{{ formatCurrency(slotProps.data.price) }}</div>
      </template>
    </Column>
    <Column :header="getLabel('productlist.selection')">
      <template #body="slotProps">
        <div class="flex justify-center flex-wrap">
          <div class="justify-center">
            <InputNumber
              v-model="slotProps.data.quantity"
              :inputStyle="{ width: '3rem', 'text-align': 'center' }"
              :max="99"
              :min="0"
              buttonLayout="horizontal"
              showButtons
              @input="quantityChanged(slotProps.data)"
            >
              <template #incrementbuttonicon>
                <span class="pi pi-plus" />
              </template>
              <template #decrementbuttonicon>
                <span class="pi pi-minus" />
              </template>
            </InputNumber>
          </div>
        </div>
      </template>
    </Column>
  </DataTable>
</template>

<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import type { LineItem } from '@/models/pos.model'
import { usePosStore } from '@/stores/pos.store'
import { formatCurrency, getLabel } from '@/utils'

const posStore = usePosStore()
const { lineitems } = storeToRefs(posStore)

posStore.fetchProducts()

const quantityChanged = (product: LineItem) => {
  console.log('Quantity of ' + JSON.stringify(product) + ' changed')
}
</script>
