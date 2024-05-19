<template>
    <DataTable :value="posStore.products" showGridlines stripedRows>
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
                       showButtons >
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
// @input="quantityChanged(slotProps.data)"
import type { LineItem } from "@/models/pos.model";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency } from "@/utils";

const posStore = usePosStore()

const quantityChanged = (product: LineItem) => {
  console.log("Quantity of " + JSON.stringify(product) + " changed");
};
</script>

