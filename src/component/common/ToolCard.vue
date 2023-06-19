<script lang="ts">
import {defineComponent, PropType} from "vue";
import {MenuOperate} from "@/type/component/MenuOperate";
import Widget from "@/component/common/Widget.vue";

export default defineComponent({

  components: {
    Widget
  },

  props: {
    name: {
      type: String,
      required: true,
    },
    fixed: Boolean,
    menuOperate: Object as PropType<MenuOperate>,
  },
});
</script>

<template>
  <widget :name="name" :fixed="fixed" :menu-operate="menuOperate">
    <!--suppress TypeScriptValidateTypes -->
    <el-card
        class="bg-div"
        :style="{ borderColor: `rgba(0,0,0,0.1)` }"
        shadow="hover"
        :body-style="{
          padding: '10px'
        }"
    >
      <slot/>
    </el-card>
  </widget>
</template>

<style scoped lang="scss">
//卡片背景
.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 100;
  background-color: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

</style>
