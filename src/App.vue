<script lang="ts">
import {defineComponent} from "vue";
import store from "@/store";

function initStyleProperty() {
  let style = document.body.style;
  // 设置一些属性值
  style.setProperty("--el-statistic-content-color", "#cc6c6c")
  style.setProperty("--el-fill-color-blank", "rgba(0,0,0,0)")
}

/**
 * 初始化套件
 * 遍历本地缓存中以Widget开头的缓存，并将其赋值到fixedWidgetCache中
 */
function initWidget() {
  for (let i = 0; i < localStorage.length; i++) {
    const key = localStorage.key(i);
    if (key && key.startsWith("Widget:")) {
      store.state.fixedWidgetCache[key.split("Widget:")[1]] = JSON.parse(
          localStorage[key]
      );
    }
  }
}


export default defineComponent({

  mounted() {
    initStyleProperty() // 初始化全局属性值
    initWidget() // 初始化套件
  }
})
</script>

<template>
  <RouterView></RouterView>
</template>

<style lang="scss">
* {
  /*配置内外边距为0，因为不同浏览器的默认边距可能不一样*/
  padding: 0px;
  margin: 0px;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

html,
body {
  width: 100%;
  height: 100%;
}

body {
  background-attachment: fixed;
  overflow: hidden;
}

.el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  padding: 10px 12px;
  color: white;
  font-size: 14px;
  background: linear-gradient(90deg, rgb(163, 203, 236), rgb(142, 216, 245));
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, rgb(163, 203, 236), rgb(142, 216, 245));
  right: 0;
}

</style>
