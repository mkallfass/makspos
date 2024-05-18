<template>
  <main>
    <Splitter>
      <SplitterPanel :size="60">
        <ProductList />
      </SplitterPanel>
      <SplitterPanel :minSize="10" :size="40">
        <CartDisplay />
        <PaymentDisplay />
        <OrderDisplay />
      </SplitterPanel>
    </Splitter>
  </main>
</template>

<script setup lang="ts">
import ProductList from "@/components/ProductList.vue";
import CartDisplay from "@/components/CartDisplay.vue";
import PaymentDisplay from "@/components/PaymentDisplay.vue";
import OrderDisplay from "@/components/OrderDisplay.vue";
</script>
