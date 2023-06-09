<script lang="ts">
import SearchEngine from "@/component/SearchEngine.vue";
import githubConner from "@/component/common/GithubConner.vue";
import DragBox from "@/component/drag/DragBox.vue";
import DragContainer from "@/component/drag/DragContainer.vue";
import { snapToGrid } from "@/component/drag/types";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import store from "@/store";
import { defineComponent } from "vue";
import StarBg from "@/component/common/StarBg.vue";
// import TwilightBg from "@/component/common/TwilightBg.vue";

export default defineComponent({
  name: "SearchView",
  components: {
    SearchEngine,
    githubConner,
    DragBox,
    DragContainer,
    MoyuCard,
    LeetcodeCard,
    StarBg,
    // TwilightBg,
  },

  data() {
    return {
      initial: true,
      opacity: 1,
      state: store.state,
      localStorage,
    };
  },

  methods: {
    dragMove(id: string, left: number, top: number) {
      [left, top] = snapToGrid(left, top);
      Object.assign(store.state.fixedToolCard[id], { left, top });
      this.saveDragToLocal(id, left, top);
    },

    async saveDragToLocal(id: string, left: number, top: number) {
      localStorage[`ToolCard:${id}`] = JSON.stringify({ left: left, top: top });
    },
  },
  mounted() {
    this.$nextTick(() => {
      window.scrollTo(0, 0);
    });
    for (let i = 0; i < localStorage.length; i++) {
      const key = this.localStorage.key(i);
      if (key && key.startsWith("ToolCard:")) {
        store.state.fixedToolCard[key.split("ToolCard:")[1]] = JSON.parse(
          localStorage[key]
        );
      }
    }
  },
});
</script>

<template>
  <div class="app" v-if="initial">
    <StarBg v-if="true"></StarBg>
    <TwilightBg v-if="false"></TwilightBg>

    <el-row style="z-index: 90">
      <github-conner />
    </el-row>

    <!-- 组件拖动窗口 -->
    <drag-container
      class="drag-container"
      :snap-to-grid="true"
      :drag-move="dragMove"
    >
      <drag-box
        v-for="(value, key) in state.fixedToolCard"
        :id="key"
        :key="key"
        v-bind="value"
      >
        <moyu-card v-if="key === 'MoyuCard'" fixed></moyu-card>
        <leetcode-card v-if="key === 'LeetcodeCard'" fixed></leetcode-card>
      </drag-box>
    </drag-container>

    <!--        回到顶部-->
    <el-backtop :right="40" :bottom="100" />
    <el-row class="search-row center">
      <h1 class="title">
        {{
          localStorage.getItem("titleName")
            ? localStorage.getItem("titleName")
            : "工作搜索"
        }}
      </h1>
      <search-engine />
    </el-row>
  </div>
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
  position: relative;
  top: 50%;
  left: 50%;
  align-items: center;
  transform: translate(-50%, -50%);
}

.search-row {
  margin-top: 45vh;
  margin-bottom: 45vh;
  z-index: 20;

  .title {
    margin-bottom: 5vh;
    text-align: center;
    width: 100%;
    color: white;
    text-shadow: 2px 2px 10px #ececec;
  }
}

.drag-container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  z-index: 10;
  position: absolute;
}
</style>
