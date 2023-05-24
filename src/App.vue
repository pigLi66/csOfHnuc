<script lang="ts">
import githubConner from "@/component/common/GithubConner.vue"
import SearchEngine from "@/component/SearchEngine.vue"
import Login from "@/component/Login.vue"
import ToolsView from "@/views/ToolsView.vue"
import { defineComponent } from "vue"
import { DndProvider } from "vue3-dnd"
import { HTML5Backend } from "react-dnd-html5-backend"
import DragBox from "./component/drag/DragBox.vue"
import DragContainer from "./component/drag/DragContainer.vue"
import { snapToGrid } from "./component/drag/types"
import MoyuCard from "./component/widgets/MoyuCard.vue"

interface BoxMap {
  [key: string]: { top: number; left: number; title: string };
}

export default defineComponent({
  components: {
    ToolsView,
    Login,
    SearchEngine,
    githubConner,
    DndProvider,
    DragBox,
    DragContainer,
    MoyuCard,
  },

  data() {
    return {
      initial: true,
      opacity: 1,
      HTML5Backend: HTML5Backend,
      boxes: this.initToolCards(),
    }
  },

  mounted() {
    document.body.style.setProperty("--el-text-color-primary", "#FFFFFF")
    window.addEventListener("scroll", this.handleScroll)
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll)
  },

  methods: {
    initToolCards(): BoxMap {
      return {
        a: { top: 20, left: 80, title: "Drag me around" },
        b: { top: 20, left: 20, title: "Drag me too" },
      }
    },

    handleScroll() {
      const scrollTop = window.scrollY
      const windowHeight = window.innerHeight
      const documentHeight = document.documentElement.scrollHeight
      const maxScrollTop = documentHeight - windowHeight
      const scrollFraction = scrollTop / maxScrollTop
      const opacity = Math.max(1 - scrollFraction, 0)
      this.opacity = opacity
      const textColor = `rgb(${256 * opacity}, ${256 * opacity}, ${
        256 * opacity
      })`
      document.body.style.setProperty('--el-text-color-primary', textColor)
    },

    dragMove(id: string, left: number, top: number) {
      [left, top] = snapToGrid(left, top)

      Object.assign(this.boxes[id], { left, top })
    },
  },
})
</script>

<template>
  <DndProvider :backend="HTML5Backend">
    <div class="app" v-if="initial">
      <!--        固定背景-->
      <div class="bg-div" :style="{ opacity: opacity }">
        <div class="bg-filter">
          <img src="../src/assets/bg.jpg" style="width: 100vw; height: 100vh" />
        </div>
      </div>

      <github-conner style="z-index: 9999;"/>
      <!-- 组件拖动窗口 -->
      <drag-container
        class="drag-container"
        :snap-to-grid="true"
        :drag-move="dragMove"
      >
        <drag-box
          v-for="(value, key) in boxes"
          :id="key"
          :key="key"
          v-bind="value"
        >
          <moyu-card></moyu-card>
        </drag-box>
      </drag-container>

      <!--        回到顶部-->
      <el-backtop :right="40" :bottom="100" />
      <login />
      <el-row class="search-row center">
        <h1 class="title">工作搜索</h1>
        <search-engine />
      </el-row>
      <el-row>
        <!--            <tools-view/>-->
        <tools-view />
      </el-row>
    </div>
  </DndProvider>
</template>

<style lang="scss">
* {
  /*配置内外边距为0，因为不同浏览器的默认边距可能不一样*/
  padding: 0px;
  margin: 0px;
}

html,
body,
#app {
  width: 100%;
  height: 100%;
}

#app {
  font-family: Helvetica, sans-serif;
  text-align: center;
}

.center {
  position: absolute;
  top: 50%;
  left: 50%;
  align-items: center;
  transform: translate(-50%, -50%);
}

.search-row {
  margin-top: 30vh;
  margin-bottom: 30vh;

  .title {
    margin-top: 30vh;
    margin-bottom: 5vh;
    text-align: center;
    horiz-align: center;
    width: 100%;
    color: white;
    text-shadow: 2px 2px 10px #ececec;
  }
}

.drag-container {
  margin-left: 5vw;
  width: 90vw;
  height: 100vh;
  overflow: hidden;
  z-index: 10;
  position: absolute;
}

.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: -1;

  .bg-filter {
    position: fixed;
    filter: blur(6px); // 磨砂质感
    transform: scale(1.2);
  }
}
</style>
