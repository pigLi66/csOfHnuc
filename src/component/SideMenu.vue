<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "SideMenu",
  data() {
    return {
      mouseHover: false,
      bgSlide: {
        left: 10,
        width: 100,
      },
      broderSlide: {
        left: 10,
        width: 0,
        opacity: 0,
      },
    };
  },
  methods: {
    selectMenu(e: any) {
      console.log(e);
      const parent = e.currentTarget;
      const left = parent.offsetLeft;
      const width = parent.offsetWidth;
      this.bgSlide.left = left;
      this.bgSlide.width = width;
      console.log(this.bgSlide);
    },
    mouseMove(e: any) {
      const parent = e.currentTarget;
      const left = parent.offsetLeft;
      const width = parent.offsetWidth;
      this.broderSlide.left = left;
      this.broderSlide.width = width;
      this.broderSlide.opacity = 1;
      console.log(this.broderSlide);
    },
    mouseOut() {
      this.broderSlide.opacity = 0;
      this.mouseHover = false;
    },
  },
  created() {},
  mounted() {},
});
</script>

<template>
  <div>
    <div id="nav-1">
      <ul class="nav">
        <li
          :class="{ slide1: true }"
          :style="{ left: bgSlide.left + 'px', width: bgSlide.width + 'px' }"
        ></li>
        <li
          :class="{ slide2: true, squeeze: mouseHover }"
          :style="{
            left: broderSlide.left + 'px',
            width: broderSlide.width + 'px',
            opacity: broderSlide.opacity,
          }"
        ></li>
        <li><a class="active" href="#/">Alpha</a></li>
        <li>
          <a
            href="#/"
            @click="selectMenu($event)"
            @mouseover="mouseMove($event)"
            @mouseout="mouseOut()"
            >Beta</a
          >
        </li>
        <li>
          <a
            href="#/"
            @click="selectMenu($event)"
            @mouseover="mouseMove($event)"
            @mouseout="mouseOut()"
            >Gamma</a
          >
        </li>
        <li>
          <a
            href="#/"
            @click="selectMenu($event)"
            @mouseover="mouseMove($event)"
            @mouseout="mouseOut()"
            >Delta</a
          >
        </li>
        <li>
          <a
            href="#/"
            @click="selectMenu($event)"
            @mouseover="mouseMove($event)"
            @mouseout="mouseOut()"
            >Epsilon</a
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped lang="scss">
#nav-1 .nav {
  position: relative;
  border: none;
  border-radius: 10em;
  display: flex;
  list-style: none;
  background: #f5f5f5;
  box-shadow: 20px 40px 50px #00000044;
  padding: 10px;
  vertical-align: middle;
}
#nav-1 .nav li {
  margin: 0px;
}
#nav-1 .nav li a {
  position: relative;
  padding: 0.6em 2em;
  font-size: 18px;
  border: none;
  color: #333;
  display: inline-block;
  text-decoration: none;
  z-index: 3;
}
#nav-1 .slide1,
#nav-1 .slide2 {
  position: absolute;
  display: inline-block;
  height: 3em;
  border-radius: 10em;
  transition: all 0.4s cubic-bezier(0.23, 1, 0.32, 1.05);
}
#nav-1 .nav .slide1 {
  background-color: yellowgreen;
  z-index: 2;
}
#nav-1 .nav .slide2 {
  opacity: 0;
  background: #fff;
  border: 1px solid #8ab9ff;
  z-index: 1;
}

.squeeze {
  transition: all 1.5s;
  -webkit-transform: scale(0.9);
  transform: scale(0.9);
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

html,
body {
  height: 100%;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e0e0e0;
  font-family: Montserrat, sans-serif;
  line-height: 1.5;
  background: linear-gradient(
    160deg,
    #4567b2 20%,
    #8ab9ff 20%,
    #8ab9ff 80%,
    #4567b2 80%
  );
}
</style>
