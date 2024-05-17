<template>
    <DataTable :value="productStore.products" showGridlines stripedRows>
      <Column field="name" header="Speisen und Getränke" style="width: 90%">
        <template #body="slotProps">
          {{ slotProps.data.name }} <small>{{ slotProps.data.detail }}</small>
        </template>
      </Column>
      <Column field="price" header="Preis" style="width: 10%">
        <template #body="slotProps">
          {{ formatCurrency(slotProps.data.price) }}
        </template>
      </Column>
    </DataTable>
</template>

<script lang="ts" setup>
import { useProductStore } from "@/stores/products";

const productStore = useProductStore()

const formatCurrency = (value : any) => {
  return value.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })
}
</script>

