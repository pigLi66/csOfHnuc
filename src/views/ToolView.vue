<!-- eslint-disable vue/no-unused-components -->
<script lang="ts">
import { defineComponent } from "vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";
import ToolCardListItem from "@/component/common/ToolCardListItem.vue";

export default defineComponent({
  name: "ToolView",
  components: {
    MoyuCard,
    LeetcodeCard,
    ToolCardListItem,
  },

  data() {
    return {
      items: [{ id: 1 }, { id: 2 }, { id: 3 }],
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
    <el-card class="tool-card-view">
      <el-contianer>
        <el-header>卡片菜单</el-header>
        <el-container>
          <!-- 左侧的用户个人界面 -->
          <el-aside width="100px">
            <ul>
              <li><a href="#摸鱼">摸鱼</a></li>
              <li><a href="#工具">工具</a></li>
              <li><a href="#生产力">生产力</a></li>
            </ul>
          </el-aside>
          <!-- 右侧主要内容 -->
          <el-main>
            <el-scrollbar height="80vh">
              <ToolCardListItem
                name="MoyuCard"
                title="摸鱼卡片"
                desc="摸鱼的卡片"
              >
                <template #prefix>
                  <font-awesome-icon
                    :icon="['fas', 'fish']"
                    style="color: var(--el-color-primary)"
                  />
                </template>
              </ToolCardListItem>
              <ToolCardListItem
                name="LeetcodeCard"
                title="力扣每日一题"
                desc="力扣每日一题"
              >
                <template #prefix>
                  <el-image
                    src="https://leetcode.cn/favicon.ico"
                    style="width: 30px"
                  ></el-image>
                </template>
              </ToolCardListItem>
            </el-scrollbar>
          </el-main>
        </el-container>
      </el-contianer>
    </el-card>
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
  background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
}
</style>
