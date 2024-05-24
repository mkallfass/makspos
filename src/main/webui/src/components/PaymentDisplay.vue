<template>
  <Card>
    <template #title>
      <div class="surface-ground">Bezahlung</div>
    </template>
    <template #content>
      <div class="grid font-bold text-xl">
        <div class="col-6">Bezahlt</div>
        <div class="col-6 md:col-3 col-offset-0 md:col-offset-3">
          <InputNumber v-model="posStore.givenAmount" mode="currency" currency="EUR" locale="de-DE" inputClass="font-bold text-xl" :inputStyle="{'width': '100%', 'text-align': 'right'}" />
        </div>
        <div v-for="p in configStore.paymentPresets" :key="p" class="col-4 xl:col-2">
          <Button :label="formatCurrency(p, configStore)" @click="posStore.givenAmount = p" severity="secondary" rounded style="width: 100%" />
        </div>
        <div class="col-6">Rückgeld</div>
        <div class="col-6" style="text-align: right">{{ returnAmount }}</div>
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { storeToRefs } from "pinia";
import { useConfigStore} from "@/stores/config.store";
import { usePosStore } from "@/stores/pos.store";
import { formatCurrency} from "@/utils";

const configStore = useConfigStore()
const posStore = usePosStore()
const { returnAmount } = storeToRefs(posStore)
</script>

