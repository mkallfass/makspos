<template>
  <Card>
    <template #title>
      Bezahlung
    </template>
    <template #content>
      <div class="p-column-title">
        <strong>Bezahlt</strong>
        <InputNumber v-model="posStore.givenAmount" mode="currency" currency="EUR" locale="de-DE">
        </InputNumber>
      </div>
      <div class="p-column-title">
        <p><strong>Rückgeld</strong> {{ returnAmount }}</p>
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { usePosStore } from "@/stores/pos.store";

const posStore = usePosStore()
const { returnAmount } = storeToRefs(posStore)
</script>

