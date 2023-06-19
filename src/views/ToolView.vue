<!-- eslint-disable vue/no-unused-components -->
<script lang="ts">
import {defineComponent} from "vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";
import ToolCardListItem from "@/component/scrollbar/ToolCardListItem.vue";

export default defineComponent({
  name: "ToolView",
  components: {
    MoyuCard,
    LeetcodeCard,
    ToolCardListItem,
  },

  data() {
    return {
      items: [{id: 1}, {id: 2}, {id: 3}],
      currentTab: "moyu",
      dialogVisible: false,
    };
  },

  methods: {
    displayDialog() {
      this.dialogVisible = true;
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
});
</script>
<template>
  <div class="view">
    <div class="tool-card-view">
      <el-contianer>
        <el-header class="header-container">
          <div>

          </div>
        </el-header>
        <el-container>
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
              <ToolCardListItem
                  name="MoyuCard"
                  title="摸鱼ERP"
                  desc="摸鱼不积极，思想有问题"
              >
                <template #prefix>
                  <moyu-card/>
                </template>
              </ToolCardListItem>
              <ToolCardListItem
                  name="LeetcodeCard"
                  title="力扣每日一题"
                  desc="获取力扣的每日一题，点击可以跳转到力扣"
              >
                <template #prefix>
                  <leetcode-card/>
                </template>
              </ToolCardListItem>
            </el-scrollbar>
          </el-main>
        </el-container>
      </el-contianer>
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
  width: 93vw;
  height: 100vh;
  //background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
}

.header-container {
  height: 15vh;
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
