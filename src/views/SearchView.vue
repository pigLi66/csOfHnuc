<script lang="ts">
import SearchEngine from "@/component/SearchEngine.vue";
import CustomCard from "@/component/common/CustomWidget.vue";
import githubConner from "@/component/common/GithubConner.vue";
import StarBg from "@/component/bg/StarBg.vue";
import DragBox from "@/component/drag/DragBox.vue";
import DragContainer from "@/component/drag/DragContainer.vue";
import {snapToGrid} from "@/component/drag/types";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import store from "@/store";
import {ElMessage} from "element-plus";
import {defineComponent} from "vue";
import WaveBg from "@/component/bg/WaveBg.vue";
import SearchBall from "@/component/SearchBall.vue";
import Live2d from "@/component/widgets/Live2d.vue";
import {WidgetCacheMap} from "@/type/store";

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
    CustomCard,
    WaveBg,
    SearchBall,
    Live2d,
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
      store.state.fixedWidgetCache.set(id, {left, top})
    },

    getComponentByKey(key: string) {
      const components: any = this.$options.components;
      const result = components[key];
      if (result) {
        return result;
      } else {
        ElMessage.error(`卡片${key}加载失败`);
      }
    },

    getFixedWidgetCfg(): WidgetCacheMap {
      let result = {} as WidgetCacheMap
      for (let fixedWidgetCacheKey in this.state.fixedWidgetCache) {
        if (fixedWidgetCacheKey !== 'set' && fixedWidgetCacheKey !== 'del') {
          result[fixedWidgetCacheKey] = this.state.fixedWidgetCache[fixedWidgetCacheKey]
        }
      }
      return result
    }
  },
});
</script>

<template>
  <div class="app" v-if="initial">
    <github-conner/>
    <!-- <StarBg v-if="true"></StarBg> -->
    <!-- <TwilightBg v-if="false"></TwilightBg> -->
    <search-engine class="search-engine"/>
    <SearchBall class="fixed-ball"></SearchBall>
    <WaveBg></WaveBg>

    <!-- <el-row style="z-index: 90">
      <github-conner />
    </el-row> -->

    <!-- 组件拖动窗口 -->
    <drag-container
        class="drag-container"
        :snap-to-grid="true"
        :drag-move="dragMove"
    >
      <!-- 加载官方的卡片 -->
      <drag-box
          v-for="(value, key) in getFixedWidgetCfg()"
          :id="key"
          :key="key"
          v-bind="value"
      >
        <!--        动态加载组件，注意排除掉set这个属性，这个是个方法-->
        <component :is="getComponentByKey(key as string)" fixed></component>
      </drag-box>
      <!-- 遍历加载用户自定义卡片 -->
      <drag-box
          v-for="(value, key) in state.fixedCustomWidgetCache"
          :id="key"
          :key="key"
          v-bind="value"
      >
        <CustomCard :name="key" :html="value.html"/>
      </drag-box>
    </drag-container>

    <!--        回到顶部-->
    <el-backtop :right="40" :bottom="100"/>
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
  align-items: center;
  justify-content: center;
  display: flex;
}

.fixed-ball {
  position: fixed;
  top: calc(50% - 100px);
  left: calc(50% - 100px);
  // transform: translate(-50%, -50%);
}

.center {
  position: relative;
  top: 50%;
  left: 50%;
  align-items: center;
  transform: translate(-50%, -50%);
}

.search-engine {
  width: 60%;
  z-index: 20;
  position: fixed;
  top: 65%;
  left: 0%;
}

.drag-container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  z-index: 10;
  position: absolute;
  left: 0;
  top: 0%;
}
</style>
