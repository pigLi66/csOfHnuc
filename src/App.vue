<script lang="ts"></script>

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
  // background: radial-gradient(
  //   200% 100% at bottom center,
  //   #f7f7b6,
  //   #e96f92,
  //   #75517d,
  //   #1b2947
  // );
  // background: radial-gradient(
  //   220% 105% at top center,
  //   #1b2947 10%,
  //   #75517d 40%,
  //   #e96f92 65%,
  //   #f7f7b6
  // );
  background-attachment: fixed;
  overflow: hidden;
}

.el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  padding: 3px 6px;
  color: white;
  background: linear-gradient(90deg, rgb(133, 157, 202), rgb(62, 146, 179));
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, rgb(133, 157, 202), rgb(62, 146, 179));
  right: 0;
}

</style>
