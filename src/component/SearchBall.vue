<script setup lang="ts">
</script>

<template>
  <div id="view">
    <div class="ball">
      <font-awesome-icon
          class="search-icon"
          :icon="['fas', 'magnifying-glass']"
      />
    </div>
  </div>
</template>
<style scoped lang="scss">

#view {
  animation: float 3.5s ease-in-out infinite;
}

.ball {
  height: 200px;
  width: 200px;
  border-radius: 50%;
  border: none;
  // position: relative;
  background: radial-gradient(
          circle at 77% 30%,
          white 5px,
          aqua 8%,
          darkblue 60%,
          aqua 100%
  );
  box-shadow: inset 0 0 20px #ffffff, inset 10px 0 46px #eaf5fc,
  inset 88px 0 60px #c2d8fe, inset -20px -60px 100px #fde9ea,
  inset 0 50px 140px #fde9ea, 0 0 90px #ffffff;

}

//
//.ball:hover {
//  //transition: all 1s;
//  animation: gradient 3.5s ease-in-out infinite;
//}

@keyframes gradient {
  100% {
    filter: hue-rotate(360deg);
  }
}

@keyframes float {
  0% {
    transform: translateY(0px) rotate(-10deg);
  }
  50% {
    transform: translateY(-20px) rotate(10deg);
  }
  100% {
    transform: translateY(0px) rotate(-10deg);
  }
}

.search-icon {
  position: fixed;
  width: 60px;
  height: 60px;
  color: #ffffff;
  top: calc(50% - 30px);
  left: calc(50% - 30px);
  animation: transform-colors 3.5s ease-in-out infinite;
}

@keyframes transform-colors {
  0% {
    color: #c5e9ff;
    filter: blur(1px) hue-rotate(360deg);;
    transform: rotate(10deg);
  }
  50% {
    color: rgb(157, 252, 252);
    filter: blur(2px) hue-rotate(360deg);;
    transform: rotate(-10deg);
  }
  100% {
    color: #c5e9ff;
    filter: blur(1px) hue-rotate(360deg);;
    transform: rotate(10deg);
  }
}

</style>
