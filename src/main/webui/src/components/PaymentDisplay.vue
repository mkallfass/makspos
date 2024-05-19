<template>
  <Card>
    <template #title>
      Bezahlung
    </template>
    <template #content>
      <div class="p-card-title">
        Bezahlt
        <InputNumber mode="currency" currency="EUR" locale="de-DE">
        </InputNumber>
      </div>
      <div class="p-card-title">
        Rückgeld
      </div>
    </template>
  </Card>
</template>

<script lang="ts" setup>
import { useCartStore } from "@/stores/cart";

const cartStore = useCartStore()

const formatCurrency = (value : any) => {
  return value.toLocaleString('de-DE', { style: 'currency', currency: 'EUR' })
}
</script>

