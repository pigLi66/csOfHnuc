<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  name: "Loading",
});
</script>
<template>
  <div class="loader">
    <div class="loading-square"></div>
    <div class="loading-square"></div>
    <div class="loading-square"></div>
    <div class="loading-square"></div>
  </div>
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

html {
  height: 100%;
  background: radial-gradient(ellipse at bottom, #1b2735 0%, #090a0f 100%);
  overflow: hidden;
}
.loader {
  position: relative;
  top: 50%;
  left: 50%;
  align-items: center;
  height: 20px;
  width: 86px;
  top: 45vh;
}

.loading-square {
  position: absolute;
  height: 20px;
  width: 20px;
  top: 0;
}

.loading-square:nth-child(1) {
  left: 0;
  animation: square1 1.5s linear forwards infinite;
}

.loading-square:nth-child(2) {
  left: 22px;
  animation: square2 1.5s linear forwards infinite;
}

.loading-square:nth-child(3) {
  left: 44px;
  animation: square3 1.5s linear forwards infinite;
}

.loading-square:nth-child(4) {
  left: 66px;
  animation: square4 1.5s linear forwards infinite;
}

@keyframes square1 {
  0% {
    background-color: #97c900;
    transform: translate(0, 0);
  }
  9.09091% {
    transform: translate(0, calc(-100% - 2px));
    background-color: #97c900;
  }
  18.18182% {
    transform: translate(calc(100% + 2px), calc(-100% - 2px));
    background-color: #15668a;
  }
  27.27273% {
    transform: translate(calc(100% + 2px), 0);
  }
  100% {
    background-color: #15668a;
    transform: translate(calc(100% + 2px), 0);
  }
}
@keyframes square2 {
  0% {
    background-color: #15668a;
    transform: translate(0, 0);
  }
  18.18182% {
    transform: translate(0, 0);
  }
  27.27273% {
    transform: translate(0, calc(100% + 2px));
    background-color: #15668a;
  }
  36.36364% {
    transform: translate(calc(100% + 2px), calc(100% + 2px));
    background-color: #d53a33;
  }
  45.45455% {
    transform: translate(calc(100% + 2px), 0);
  }
  100% {
    background-color: #d53a33;
    transform: translate(calc(100% + 2px), 0);
  }
}
@keyframes square3 {
  0% {
    background-color: #d53a33;
    transform: translate(0, 0);
  }
  36.36364% {
    transform: translate(0, 0);
  }
  45.45455% {
    transform: translate(0, calc(-100% - 2px));
    background-color: #d53a33;
  }
  54.54545% {
    transform: translate(calc(100% + 2px), calc(-100% - 2px));
    background-color: #e79c10;
  }
  63.63636% {
    transform: translate(calc(100% + 2px), 0);
  }
  100% {
    background-color: #e79c10;
    transform: translate(calc(100% + 2px), 0);
  }
}
@keyframes square4 {
  0% {
    transform: translate(0, 0);
    background-color: #e79c10;
  }
  54.54545% {
    transform: translate(0, 0);
  }
  63.63636% {
    transform: translate(0, calc(100% + 2px));
    background-color: #e79c10;
  }
  72.72727% {
    background-color: #d53a33;
  }
  81.81818% {
    background-color: #15668a;
  }
  90.90909% {
    transform: translate(calc(-300% - 6px), calc(100% + 2px));
    background-color: #97c900;
  }
  100% {
    transform: translate(calc(-300% - 6px), 0);
    background-color: #97c900;
  }
}
</style>
