<template>
  <Card>
    <template #title>
      <div class="bg-surface-50 dark:bg-surface-950">{{ getLabel("payment.title") }}</div>
    </template>
    <template #content>
      <div class="grid grid-cols-12 gap-4 font-bold text-xl">
        <div class="col-span-6">{{ getLabel("payment.payed") }}</div>
        <div class="col-span-6 md:col-span-3 col-start-1 md:col-start-4">
          <InputNumber v-model="posStore.givenAmount" :inputStyle="{'width': '100%', 'text-align': 'right'}"
                       :currency="getConfig().currency" inputClass="font-bold text-xl" mode="currency" />
        </div>
        <div v-for="p in getConfig().paymentPresets" :key="p" class="col-span-4 xl:col-span-2">
          <Button :label="formatCurrency(p)" rounded severity="secondary" style="width: 100%"
                  @click="posStore.givenAmount = p" />
        </div>
        <div class="col-span-6">{{ getLabel("payment.change") }}</div>
        <div class="col-span-6" style="text-align: right">{{ formatCurrency(returnAmount) }}</div>
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

