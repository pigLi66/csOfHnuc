<script setup lang="ts">
import DragContainer from "./DragContainer.vue";
import CustomDragLayer from "./CustomDragLayer.vue";
import DragBox from "./DragBox.vue";
import MoyuCard from "../widgets/MoyuCard.vue";

import { snapToGrid } from "./types";
import { reactive } from "vue";

interface BoxMap {
  [key: string]: { top: number; left: number; title: string };
}

const boxes = reactive<BoxMap>({
  a: { top: 20, left: 80, title: "Drag me around" },
  b: { top: 180, left: 20, title: "Drag me too" },
});

const dragMove = (id: string, left: number, top: number) => {
  [left, top] = snapToGrid(left, top);

  Object.assign(boxes[id], { left, top });
};
</script>

<template>
  <div>
    <DragContainer :snap-to-grid="true" :drag-move="dragMove">
      <DragBox
        v-for="(value, key) in boxes"
        :id="key"
        :key="key"
        v-bind="value"
      >
        <moyu-card ></moyu-card>
      </DragBox>
    </DragContainer>
    <!-- <CustomDragLayer :snap-to-grid="true" /> -->
  </div>
</template>