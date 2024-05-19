<template>
  <Card>
    <template #title>
      <div class="surface-ground">Bezahlung</div>
    </template>
    <template #content>
      <div class="grid font-bold text-xl">
        <div class="col-6">Bezahlt</div>
        <div class="col-3 col-offset-3">
          <InputNumber v-model="posStore.givenAmount" mode="currency" currency="EUR" locale="de-DE" inputClass="font-bold text-xl" :inputStyle="{'width': '100%', 'text-align': 'right'}" />
        </div>
        <div class="col-6">Rückgeld</div>
        <div class="col-6" style="text-align: right">{{ returnAmount }}</div>
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

