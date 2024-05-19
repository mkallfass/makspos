<template>
  <main>
    <div class="grid" >
      <div class="col-7">
        <ProductList />
      </div>
      <div class="col-5">
        <CartDisplay />
        <PaymentDisplay />
        <OrderDisplay />
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import ProductList from "@/components/ProductList.vue";
import CartDisplay from "@/components/CartDisplay.vue";
import PaymentDisplay from "@/components/PaymentDisplay.vue";
import OrderDisplay from "@/components/OrderDisplay.vue";
</script>
