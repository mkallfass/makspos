<template>
  <Card>
    <template #content>
      <div class="flex justify-content-end">
        <Button label="Open Dialog" @click="display = !display" />
      </div>
    </template>
  </Card>

  <Dialog header="Header" v-model:visible="display" >
    <Button class="p-button-info p-button-sm" label="Close" @click="display = !display"/>
  </Dialog>

</template>

<script lang="ts" setup>
import { ref } from "vue";

const display = ref(false)
</script>

