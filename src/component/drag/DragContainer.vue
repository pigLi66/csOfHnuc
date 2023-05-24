<script setup lang="ts">
import { useDrop } from "vue3-dnd"
import { ItemTypes, DragItem } from "./types"

const props = defineProps<{
  snapToGrid: boolean;
  dragMove:(id: string, left: number, top: number) => undefined;
}>()

const [, drop] = useDrop(() => ({
  accept: ItemTypes.BOX,
  drop(item: DragItem, monitor) {
    const delta = monitor.getDifferenceFromInitialOffset() as {
      x: number;
      y: number;
    }

    const left = Math.round(item.left + delta.x)
    const top = Math.round(item.top + delta.y)
    props.dragMove(item.id, left, top)

    return undefined
  },
}))
</script>

<template>
  <div :ref="drop" :style="{ overflow: 'hidden', clear: 'both' }">
    <slot />
  </div>
</template>

<style scoped lang="scss">
</style>
