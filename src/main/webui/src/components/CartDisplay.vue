<template>
  <Card>
    <template #title>
      <div class="surface-ground">Bestellung</div>
    </template>
    <template #content>
      <DataTable :value="cartLineItems" showGridlines stripedRows>
        <Column field="quantity" header="Anzahl" style="width: 10%" />
        <Column field="name" header="Position" style="width: 70%">
          <template #body="slotProps">
            {{ slotProps.data.name }}
          </template>
        </Column>
        <Column field="price" header="Einzelpreis" style="width: 10%">
          <template #body="slotProps">
            <div style="text-align:right;">{{ formatCurrency(slotProps.data.price) }}</div>
          </template>
        </Column>
        <Column field="total" header="Summe" style="width: 10%">
          <template #body="slotProps">
            <div style="text-align:right;">{{ formatCurrency(slotProps.data.total) }}</div>
          </template>
        </Column>
        <template #footer>
          <div class="grid font-bold text-xl">
            <div class="col"> Summe</div>
            <div class="col" style="text-align: right">{{ formatCurrency(posStore.total) }}</div>
          </div>
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

