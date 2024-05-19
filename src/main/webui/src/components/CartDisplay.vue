<template>
  <Card>
    <template #title>
      Bestellung
    </template>
    <template #content>
      <DataTable :value="cartStore.lineItems.values()" showGridlines stripedRows>
        <Column field="quantity" header="Anzahl" style="width: 10%" />
        <Column field="name" header="Position" style="width: 70%">
          <template #body="slotProps">
            {{ slotProps.data.name }} <small>{{ slotProps.data.detail }}</small>
          </template>
        </Column>
        <Column field="price" header="Einzelpreis" style="width: 10%">
          <template #body="slotProps">
            {{ formatCurrency(slotProps.data.price) }}
          </template>
        </Column>
        <Column field="total" header="Summe" style="width: 10%">
          <template #body="slotProps">
            {{ formatCurrency(slotProps.data.total) }}
          </template>
        </Column>
        <template #footer>
          <div class="p-card-title">Summe {{ formatCurrency(cartStore.total) }}</div>
        </template>
      </DataTable>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { useCartStore } from "@/stores/cart";
import { storeToRefs } from "pinia";

const cartStore = useCartStore()

const formatCurrency = (value : any) => {
  return value.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })
}
</script>

