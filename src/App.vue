<template>
  <router-view></router-view>
</template>

<script lang="ts">

export default {
  mounted() {
    // document.body.style.setProperty("--el-color-primary", "#ffeba7")
    // document.body.style.setProperty("--el-color-primary-dark-2", "#c3ab80")
    // document.body.style.setProperty("--el-color-primary-light-3", "#dcc593")
  }
}
</script>

<style lang="scss">
*{
  /*配置内外边距为0，因为不同浏览器的默认边距可能不一样*/
  padding: 0px;
  margin: 0px;
}
html,body,#app{
  width: 100%;
  height: 100%;
}
</style>
