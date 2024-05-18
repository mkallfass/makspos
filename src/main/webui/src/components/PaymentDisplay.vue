<template>
  <Card>
    <template #title>
      Bezahlung
    </template>
    <template #content>
    </template>
    <template #footer>
      <div>
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

