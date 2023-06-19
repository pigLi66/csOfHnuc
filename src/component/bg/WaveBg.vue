<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({});
</script>
<template>
  <div class="bg">
    <svg
      class="editorial"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      viewBox="0 24 150 28"
      preserveAspectRatio="none"
    >
      <defs>
        <path
          id="gentle-wave"
          d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z"
        />
      </defs>
      <g class="parallax">
        <use
          xlink:href="#gentle-wave"
          class="wave"
          x="50"
          y="0"
          fill="#eaf5fc"
        />
        <use
          xlink:href="#gentle-wave"
          class="wave"
          x="50"
          y="6"
          fill="#fff3f3"
        />
        <use
          xlink:href="#gentle-wave"
          class="wave"
          x="50"
          y="3"
          fill="#cfdff0"
        />
      </g>
    </svg>
  </div>
</template>
<style lang="scss">
body {
//     background: radial-gradient(
//     200% 100% at bottom center,
//     #f1dfe4,
//     #e9d1de,
//     #e3d6e7,
//     #d9d7e9,
//   );
}

.bg {
  width: 100%;
  position: absolute;
  bottom: 0;
  right: 0;
}

.wave {
//   backdrop-filter: blur(10px);
  opacity: 0.8;
}

.parallax > use {
  animation: move-forever 12s linear infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
}
.parallax > use:nth-child(2) {
  animation-delay: -2s;
  animation-duration: 5s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 3s;
}

@keyframes move-forever {
  0% {
    transform: translate(-90px, 0%);
  }
  100% {
    transform: translate(85px, 0%);
  }
}
.editorial {
  display: block;
  width: 100%;
  height: 10em;
  max-height: 100vh;
  margin: 0;
}

/*prevent many large-by-comparison ripples by shrinking the height*/
@media (max-width: 50em) {
  .content h1 {
    font-size: 12vmax;
  }

  .editorial {
    height: 17vw;
  }
}
h1 {
  outline: none;
}
</style>
