<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({

  mounted() {
    let bd = document.body;
    let sunny:any = document.querySelectorAll(".card__sun");
    let m1:any = document.querySelectorAll(".card__m--1");
    let m2:any = document.querySelectorAll(".card__m--2");
    let m3:any = document.querySelectorAll(".card__m--3");

    bd.addEventListener("mousemove", (event: MouseEvent) => {
      let w = window.innerWidth / 2;
      let x = event.clientX;
      let y = event.clientY;

      sunny[0].style.transform = `translateX(${x / 20}px) translateY(${y / 20}px)`;
      m1[0].style.transform = `rotateZ(135deg) skew(20deg, 20deg) translateX(${x / 50}px) translateY(${y / 55}px)`;
      m2[0].style.transform = `rotateZ(135deg) skew(20deg, 20deg) translateX(${x / 35}px) translateY(${y / 45}px)`;
      m3[0].style.transform = `rotateZ(135deg) skew(20deg, 20deg) translateX(${x / 35}px) translateY(${y / 35}px)`;
    });
  }
})

</script>

<template>
  <div class="main">
    <div class="card">
      <div class="card__m card__m--1"></div>
      <div class="card__m card__m--2"></div>
      <div class="card__m card__m--3"></div>
      <div class="card__m card__m--4"></div>
      <div class="card__m card__m--5"></div>
      <div class="card__sun"></div>
      <div class="card__star card__star--1"></div>
      <div class="card__star card__star--2"></div>
      <div class="card__star card__star--3"></div>
      <div class="card__star card__star--4"></div>
      <div class="card__star card__star--5"></div>
      <div class="card__fugaz"></div>
    </div>
  </div>
</template>

<style scoped lang="scss">
/* Variables */
/* Reset */
*, *::after, *::before {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.main {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.card {
  position: relative;
  width: 100%;
  height: 100%;
  //margin: 0 20px;
  overflow: hidden;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.35);
  transition: .15s ease;
  background-image: linear-gradient(to bottom, #93A2AF, #DDD2D5 40%);
}

.card--b {
  background-image: linear-gradient(to bottom, #93A2AF, #DDD2D5 40%);
}

.card__m {
  z-index: 1;
  position: absolute;
  transform: rotateZ(135deg) skew(20deg, 20deg);
}

.card__m--1 {
  top: 20px;
  left: -185px;
  width: 500px;
  height: 500px;
  background-color: #AEB7BE;
  transition: .2s ease-in;
}

.card__m--2 {
  top: 20px;
  left: 25px;
  width: 500px;
  height: 500px;
  background-color: #6F8A9C;
  transition: .15s ease-in;
}

.card__m--3 {
  top: 40px;
  left: 100px;
  width: 500px;
  height: 500px;
  background-color: #205A6E;
  transition: .1s ease-in;
}

.card__m--4 {
  top: 50px;
  left: -75px;
  width: 500px;
  height: 500px;
  background-color: #3A405A;
}

.card__m--5 {
  top: 20px;
  left: -285px;
  width: 500px;
  height: 500px;
  background-color: #453750;
}

.card__sun {
  position: absolute;
  left: 70%;
  top: -10%;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #ffffff;
  transition: .15s ease;
}

.card__star {
  position: absolute;
  border-radius: 50%;
  background-color: #e1e9ef;
}

.card__star--1 {
  height: 5px;
  width: 5px;
  top: 30%;
  left: 80%;
}

.card__star--2 {
  height: 4px;
  width: 4px;
  top: 20%;
  left: 90%;
}

.card__star--3 {
  height: 4px;
  width: 4px;
  top: 10%;
  left: 60%;
}

.card__star--4 {
  height: 3px;
  width: 3px;
  top: 25%;
  left: 50%;
}

.card__star--5 {
  height: 3px;
  width: 3px;
  top: 30%;
  left: 10%;
}

.card__fugaz::before {
  content: '';
  position: absolute;
  top: -4px;
  left: -60px;
  width: 60px;
  height: 16px;
  background-image: linear-gradient(to left, rgba(225, 233, 239, 0.7), rgba(225, 233, 239, 0.3));
  border-top-left-radius: 50%;
  border-bottom-left-radius: 50%;
  animation: fugaz-2 .1s infinite;
  filter: blur(2px);
}

.card__fugaz {
  position: absolute;
  top: -100px;
  left: 200px;
  height: 10px;
  width: 10px;
  background-color: rgba(225, 233, 239, 0.7);
  border-radius: 50%;
  transform: rotateZ(10deg);
  animation: fugaz 8s infinite;
}

/**/
@keyframes fugaz {
  0% {
    left: -100px;
    top: -200px;
  }
  60%, 100% {
    top: 150px;
    left: 100%;
  }
}

@keyframes fugaz-2 {
  0% {
    transform: skewX(15deg) scaleY(1);
  }
  100% {
    transform: skewX(-15deg) scaleY(0.5);
  }
}

</style>
