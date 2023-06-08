<script lang="ts">
import { defineComponent } from "vue";
import MoyuCard from "@/component/widgets/MoyuCard.vue";
import LeetcodeCard from "@/component/widgets/LeetcodeCard.vue";

export default defineComponent({
  name: "Tool",
  components: {
    MoyuCard,
    LeetcodeCard,
  },

  data() {
    return {
      categoryList: [
        { name: "摸鱼组件", key: "moyu", count: 12 },
        { name: "工具组件", key: "tool", count: 3 },
        { name: "其他组件", key: "other", count: 30 },
      ],
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
});
</script>
<template>
  <div class="tools-view bg">
    <div>
      <el-row>
        <el-tabs tab-position="left" class="demo-tabs" v-model="currentTab">
          <el-tab-pane
            v-for="category in categoryList"
            :key="category.key"
            :label="category.name"
          />
        </el-tabs>

        <div class="scroll-content">
          <h3 class="category-title">摸鱼组件</h3>
          <el-row>
            <moyu-card class="tool-card" />
            <leetcode-card class="tool-card" />
          </el-row>
        </div>
      </el-row>
    </div>

    <el-dialog v-model="dialogVisible" title="Warning" width="30%" center>
      <span> TODO 现在还不知要写什么 </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogVisible = false">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">

body{
  padding: 0px;
}

.tools-view {
  width: 100%;
  height: 100%;
  margin-left: 0px;
}

.category-title {
  text-align: left;
  color: var(--el-text-color-primary);
}

.tool-card {
  width: 200px;
  margin: 1%;
  border: rgba(255, 255, 255, 0);
  border-radius: 10px;
}

.tool-card:hover {
  transform: translate3d(1%, 1%, 0) scale(1.05);
  transition: all 0.3s ease;
}

.demo-tabs {
  width: 10%;
}

.scroll-content {
  width: 90%;
  overflow-y: scroll;
  scrollbar-width: thin;
  scrollbar-color: #ccc transparent;
}

.scroll-content::-webkit-scrollbar {
  width: 6px;
}

.scroll-content::-webkit-scrollbar-track {
  background-color: transparent;
}

.scroll-content::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 3px;
}
</style>
