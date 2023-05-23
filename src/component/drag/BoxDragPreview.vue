<script setup lang="ts">
import Box from './Box.vue'
import { onMounted, onUnmounted, ref } from 'vue'

const props = defineProps<{
  title: string
}>()

</script>

<template>
  <div class="box-drag-preview">
    <Box :title="title" />
  </div>
</template>

<style lang="scss" scoped>
.box-drag-preview {
  display: inline-block;
}
</style>
