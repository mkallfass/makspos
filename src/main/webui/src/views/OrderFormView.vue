<template>
  <main>
    <div class="grid grid-cols-12 gap-4">
      <div class="col-span-12 lg:col-span-6">
        <ProductList />
      </div>
      <div class="col-span-12 lg:col-span-6">
        <CartDisplay />
        <PaymentDisplay />
        <OrderDisplay />
      </div>
    </div>
  </main>
</template>

<script lang="ts" setup>
import ProductList from '@/components/ProductList.vue'
import CartDisplay from '@/components/CartDisplay.vue'
import PaymentDisplay from '@/components/PaymentDisplay.vue'
import OrderDisplay from '@/components/OrderDisplay.vue'
</script>
