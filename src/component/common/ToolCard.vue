<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ToolCard",

  data() {
    return {
      initial: true,
      opacity: 0,
    };
  },

  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  },

  methods: {
    handleScroll() {
      const scrollTop = window.scrollY;
      const windowHeight = window.innerHeight;
      const documentHeight = document.documentElement.scrollHeight;
      const maxScrollTop = documentHeight - windowHeight;
      const scrollFraction = scrollTop / maxScrollTop;
      const opacity = Math.min(scrollFraction, 1);
      this.opacity = opacity;
    },
  },
});
</script>

<template>
  <div>
    <el-card
      class="bg-div"
      :style="{ borderColor: `rgb(200,200,200, ${opacity}` }"
      shadow="hover"
    >
      <slot />
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: -1;
  background-color: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
}
</style>