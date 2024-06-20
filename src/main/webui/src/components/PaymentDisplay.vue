<template>
  <Card>
    <template #title>
      <div class="surface-ground">{{ getLabel("payment.title") }}</div>
    </template>
    <template #content>
      <div class="grid font-bold text-xl">
        <div class="col-6">{{ getLabel("payment.payed") }}</div>
        <div class="col-6 md:col-3 col-offset-0 md:col-offset-3">
          <InputNumber v-model="posStore.givenAmount" :inputStyle="{'width': '100%', 'text-align': 'right'}"
                       :currency="getConfig().currency" inputClass="font-bold text-xl" mode="currency" />
        </div>
        <div v-for="p in getConfig().paymentPresets" :key="p" class="col-4 xl:col-2">
          <Button :label="formatCurrency(p)" rounded severity="secondary" style="width: 100%"
                  @click="posStore.givenAmount = p" />
        </div>
        <div class="col-6">{{ getLabel("payment.change") }}</div>
        <div class="col-6" style="text-align: right">{{ formatCurrency(returnAmount) }}</div>
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency, getLabel } from "@/utils";
import { getConfig } from "@/config";

const posStore = usePosStore();
const { returnAmount } = storeToRefs(posStore);
</script>

