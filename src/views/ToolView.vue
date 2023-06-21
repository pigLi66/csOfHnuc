<!-- eslint-disable vue/no-unused-components -->
<script lang="ts">
import {defineComponent, markRaw} from "vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";
import ToolCardItem from "@/component/scrollbar/ToolCardItem.vue";
import SearchInput from "@/component/common/SearchInput.vue";
import ParallaxMountainBg from "@/component/bg/ParallaxMountainBg.vue";

type CardItem = {
  component: any,
  title: string,
  desc: string
}

export default defineComponent({
  name: "ToolView",
  components: {
    ParallaxMountainBg,
    SearchInput,
    MoyuCard,
    LeetcodeCard,
    ToolCardItem,
  },

  data() {
    return {
      // 不可变对象，设置为非响应式提高性能
      items: markRaw([
        {
          component: MoyuCard,
          title: '摸鱼ERP',
          desc: '摸鱼不积极，思想有问题'
        }, {
          component: LeetcodeCard,
          title: '力扣每日一题',
          desc: '获取力扣的每日一题，点击可以跳转到力扣答题界面'
        }
      ] as CardItem[]),
      searchInput: {
        keyword: ''
      }
    };
  },

  methods: {

    filterCardItem(): CardItem[] {
      let inputKeywords = this.searchInput.keyword;
      if (!inputKeywords || inputKeywords === '') {
        return this.items
      }
      let keywords = inputKeywords.split(/\s/)
      let result = this.items.filter(it => keywords.filter(keyword =>
              it.title.includes(keyword) || it.desc.includes(keyword)
          ).length > 0
      )
      return result
    },

    inputSearch(str: string) {
      this.searchInput.keyword = str
    }
  },
  mounted() {
    window.scrollTo(0, 0);
  },
});
</script>
<template>
  <div class="view">
    <div class="tool-card-view">
      <el-container style="align-content: center;flex-wrap: wrap">
        <el-header class="header-container">
          <ParallaxMountainBg style="position: absolute; top:0; left: 0; width: 100%;height: 12vh;z-index: 1"/>
          <el-row style="padding-top: 20px;z-index: 2;font-family: 'tonsil';color: #4f4f4f">
            <el-col :span="7">
              这里或许有你想要的
            </el-col>
            <el-col :span="10">
              <SearchInput v-model:value="searchInput.keyword"
                           @input="inputSearch($event.target.value)"
              />
            </el-col>
            <el-col :span="7">

            </el-col>
          </el-row>
        </el-header>
        <el-container style="width: 93vw">
          <!-- 左侧的用户个人界面 -->
          <el-aside class="aside-container">
            <el-card class="side-card">
              <ul>
                <li><a href="#摸鱼">摸鱼</a></li>
                <li><a href="#工具">工具</a></li>
                <li><a href="#生产力">生产力</a></li>
              </ul>
            </el-card>

          </el-aside>
          <!-- 右侧主要内容 -->
          <el-main>
            <el-scrollbar height="80vh">
              <ToolCardItem v-for="it in filterCardItem()" :key="it.title"
                            :title="it.title"
                            :desc="it.desc"
              >
                <template #prefix>
                  <component :is="it.component"/>
                </template>
              </ToolCardItem>
            </el-scrollbar>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<style scoped lang="scss">
body {
  padding: 0px;
}

.view {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.tool-card-view {
  width: 100vw;
  height: 100vh;
  //background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
}

.header-container {
  height: 12vh;
  padding: 0;
  background: linear-gradient(to right,
      #cfdff0,
      #fff3f3,
      #eaf5fc
  );
}

.aside-container {
  width: 20vw;
}

.side-card {
  height: 100%;
}
</style>
