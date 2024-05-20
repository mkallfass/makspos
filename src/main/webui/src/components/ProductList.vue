<template>
    <DataTable :value="posStore.products" showGridlines stripedRows>
      <Column field="name" header="Speisen und Getränke">
        <template #body="slotProps">
          {{ slotProps.data.name }} <small>{{ slotProps.data.detail }}</small>
        </template>
      </Column>
      <Column field="price" header="Preis">
        <template #body="slotProps">
          <div style="text-align:right;">{{ formatCurrency(slotProps.data.price, configStore) }}</div>
        </template>
      </Column>
      <Column header="Auswahl">
        <template #body="slotProps">
          <div class="flex justify-content-center flex-wrap">
            <div class="justify-content-center">
              <InputNumber v-model="slotProps.data.quantity" :max="99" :min="0" buttonLayout="horizontal"
                           @input="quantityChanged(slotProps.data)"
                           showButtons :inputStyle="{'width': '3rem', 'text-align': 'center'}">
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
import type { LineItem } from "@/models/pos.model";
import { useConfigStore } from "@/stores/config.store";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency } from "@/utils";

const configStore = useConfigStore();
const posStore = usePosStore()

const quantityChanged = (product: LineItem) => {
  console.log("Quantity of " + JSON.stringify(product) + " changed");
};
</script>

