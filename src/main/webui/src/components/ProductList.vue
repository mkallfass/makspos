<template>
    <DataTable :value="posStore.products" showGridlines stripedRows>
      <Column field="name" header="Speisen und Getränke">
        <template #body="slotProps">
          {{ slotProps.data.name }} <small>{{ slotProps.data.detail }}</small>
        </template>
      </Column>
      <Column field="price" header="Preis">
        <template #body="slotProps">
          <div style="text-align:right;">{{ formatCurrency(slotProps.data.price) }}</div>
        </template>
      </Column>
      <Column header="Auswahl">
        <template #body="slotProps">
          <InputNumber v-model="slotProps.data.quantity" :max="99" :min="0" buttonLayout="horizontal"
                       @input="quantityChanged(slotProps.data)"
                       showButtons inputStyle="width: 3rem;text-align:center;">
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
import type { LineItem } from "@/models/pos.model";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency } from "@/utils";

const posStore = usePosStore()

const quantityChanged = (product: LineItem) => {
  console.log("Quantity of " + JSON.stringify(product) + " changed");
};
</script>

