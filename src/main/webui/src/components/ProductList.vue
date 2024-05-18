<template>
    <DataTable :value="productStore.products" showGridlines stripedRows>
      <Column field="name" header="Speisen und Getränke" style="width: 50%">
        <template #body="slotProps">
          {{ slotProps.data.name }} <small>{{ slotProps.data.detail }}</small>
        </template>
      </Column>
      <Column field="price" header="Preis" style="width: 10%">
        <template #body="slotProps">
          {{ formatCurrency(slotProps.data.price) }}
        </template>
      </Column>
      <Column header="Auswahl" style="width: 40%">
        <template #body="slotProps">
          <InputNumber v-model="slotProps.data.quantity" :max="99" :min="0" buttonLayout="horizontal"
                       showButtons @input="quantityChanged(slotProps.data)">
            <template #incrementbuttonicon>
              <span class="pi pi-plus" />
            </template>
            <template #decrementbuttonicon>
              <span class="pi pi-minus" />
            </template>
          </InputNumber>
        </template>
      </Column>
    </DataTable>
</template>

<script lang="ts" setup>
import type { ProductSelection } from "@/models/product.model";

import { useProductStore } from "@/stores/product";
import { useCartStore } from "@/stores/cart";

const productStore = useProductStore()
const cartStore = useCartStore();

const formatCurrency = (value : any) => {
  return value.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })
}
const quantityChanged = (product: ProductSelection) => {
  console.log("Quantity of " + JSON.stringify(product) + " changed");
  cartStore.addLineItem(product);
};
</script>

