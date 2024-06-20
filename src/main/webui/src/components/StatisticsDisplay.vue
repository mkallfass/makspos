<template>
  <div class="text-center font-bold text-xl"><h1>Bestellstatistiken</h1></div>
  <Card>
    <template #content>
      <div class="grid font-bold text-xl">
        <div class="col-6">Anzahl Bestellungen</div>
        <div class="col-6" style="text-align: right">{{ orderStatistics.orderCount }}</div>
      </div>
      <div class="grid font-bold text-xl">
        <div class="col-6">Gesamtumsatz</div>
        <div class="col-6" style="text-align: right">
          {{ formatCurrency(orderStatistics.overallRevenue) }}
        </div>
      </div>
    </template>
  </Card>
  <DataTable :value="orderStatistics.productStatistics" showGridlines stripedRows>
    <Column field="name" header="Produkt">
      <template #body="slotProps">
        {{ slotProps.data.name }} <small>{{ slotProps.data.description }}</small>
      </template>
    </Column>
    <Column field="orderedCount" header="Gesamtzahl" sortable>
      <template #body="slotProps">
        <div style="text-align:right;">{{ slotProps.data.orderedCount }}</div>
      </template>
    </Column>
    <Column field="revenue" header="Gesamtumsatz" sortable>
      <template #body="slotProps">
        <div style="text-align:right;">{{ formatCurrency(slotProps.data.revenue) }}</div>
      </template>
    </Column>
  </DataTable>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency } from "@/utils";

const posStore = usePosStore();
const { orderStatistics } = storeToRefs(posStore);

posStore.fetchStatistics();
</script>

