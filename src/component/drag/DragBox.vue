<script setup lang="ts">
import { useDrag, DragSourceMonitor } from "vue3-dnd"
import { ItemTypes } from "./types"
import { getEmptyImage } from "react-dnd-html5-backend"
import { toRefs } from "@vueuse/core"
import { onMounted } from "vue"

const props = defineProps<{
  id: string
  title: string
  left: number
  top: number
}>()

const [collect, drag, preview] = useDrag(() => ({
  type: ItemTypes.BOX,
  item: props,
  collect: (monitor: DragSourceMonitor) => ({
    isDragging: monitor.isDragging(),
  }),
}))

onMounted(() => {
  preview(getEmptyImage(), { captureDraggingState: true })
})

const { isDragging } = toRefs(collect)
</script>

<template>
  <div
    :ref="drag"
    :style="{
      position: 'absolute',
      transform: `translate3d(${left}px, ${top}px, 0)`,
      opacity: isDragging ? 0.5 : 1,
      height: isDragging ? 0 : '',
      float: 'left',
    }"
  >
    <slot />
  </div>
</template>
