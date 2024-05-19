<template>
  <Card>
    <template #title>
      Bestellung
    </template>
    <template #content>
      <DataTable :value="cartLineItems" showGridlines stripedRows>
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
          <div class="p-card-title">Summe {{ formatCurrency(posStore.total) }}</div>
        </template>
      </DataTable>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency } from "@/utils";

const posStore = usePosStore()
const { cartLineItems } = storeToRefs(posStore)
</script>

