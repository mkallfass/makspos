<template>
  <main>
    <Splitter>
      <SplitterPanel class="flex align-items-center justify-content-center">
        <Card class="my-2">
          <template #title>
            This is an about page
          </template>
          <template #content>
            <div class="text-sky-500 dark:text-sky-400">
              Test Text
            </div>
          </template>
          <template #footer>
            <div class="flex gap-2">
              <Button label="Open Dialog" @click="display = !display"/>
              <Button label="Open Dialog" class="p-button-primary" @click="display = !display"/>
              <Button label="Open Dialog" class="p-button-secondary" @click="display = !display"/>
            </div>
          </template>
        </Card>
      </SplitterPanel>
      <SplitterPanel class="flex align-items-center justify-content-center">
        Panel 2
      </SplitterPanel>
    </Splitter>

    <Dialog header="Header" v-model:visible="display" >
      <Button class="p-button-info p-button-sm" label="Close" @click="display = !display"/>
    </Dialog>

  </main>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const display = ref(false)
</script>
