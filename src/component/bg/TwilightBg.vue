<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  name: "Loading",
  methods: {
    getRandomStarStyle() {
      const s = 0.2 + Math.random() * 1;
      const curR = 800 + Math.random() * 300;
      return {
        transformOrigin: `0 0 ${curR}px`,
        transform: `translate3d(0,0,-${curR}px) rotateY(${
          Math.random() * 360
        }deg) rotateX(${Math.random() * -50}deg) scale(${s}, ${s})`,
      };
    },
  },
});
</script>
<template>
  <div class="stars">
    <div v-for="i in 500" :key="i" class="star" :style="getRandomStarStyle()"></div>
  </div>
</template>
<style lang="scss">
body {
  background: radial-gradient(
    200% 100% at bottom center,
    #f7f7b6,
    #e96f92,
    #75517d,
    #304779
  );
  background: radial-gradient(
    220% 105% at top center,
    #2b3f69 10%,
    #75517d 40%,
    #e96f92 65%,
    #f7f7b6
  );
  background-attachment: fixed;
  overflow: hidden;
}

@keyframes rotate {
  0% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg) rotateY(0);
  }
  100% {
    transform: perspective(400px) rotateZ(20deg) rotateX(-40deg)
      rotateY(-360deg);
  }
}
.stars {
  transform: perspective(500px);
  transform-style: preserve-3d;
  position: absolute;
  bottom: 0;
  perspective-origin: 50% 100%;
  left: 50%;
  animation: rotate 90s infinite linear;
}

.star {
  width: 2px;
  height: 2px;
  background: #f7f7b6;
  position: absolute;
  top: 0;
  left: 0;
  transform-origin: 0 0 -300px;
  transform: translate3d(0, 0, -300px);
  backface-visibility: hidden;
}
</style>
