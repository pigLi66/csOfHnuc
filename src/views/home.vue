<template>
  <div class="home">
    <div class="wallpaper">
        <div class="bg">

        </div>
    </div>
  </div>
</template>

<script>
export default {
    name: "home"
}
</script>

<style scoped lang="scss">

.home{
  display: flex;
  flex-direction: column;
  min-height: 100vh;

  .wallpaper{
    position: absolute; // 相对定位
    height: 300px;
    display: flex;

    .bg{

    }
  }
}

</style>