<script lang="ts">
import { defineComponent } from "vue";
import ToolCard from "../common/ToolCard.vue";

export default defineComponent({
  components: { ToolCard },
  name: "MoyuCard",
  props: {
    userId: Number,
    widgetId: Number,
    buttons: Array,
    flush: Number,
    shrink: Boolean, // 是否缩小卡片
  },

  data() {
    return {
      form: {
        hourlyWage: 0,
        moyuTime: 0,
      },
      dialogVisible: false,
    };
  },

  methods: {},
});
</script>

<template>
  <tool-card>
    <el-statistic :value="200" @click="dialogVisible = true">
      <template #title>
        <div
          style="
            display: inline-flex;
            align-items: center;
            color: var(--el-text-color-primary);
          "
        >
          您今天已经赚了
        </div>
      </template>
      <template #suffix>
        <span class="green">
          &nbsp;24%
          <el-icon>
            <CaretTop />
          </el-icon>
        </span>
      </template>
    </el-statistic>

    <el-dialog v-model="dialogVisible" title="Warning" width="30%" center append-to-body>
      <template #header>
        <span>摸鱼EPR</span>
        <el-divider />
      </template>

      <span>
        todo
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogVisible = false">
            保存
          </el-button>
        </span>
      </template>
    </el-dialog>
  </tool-card>
</template>

<style scoped lang="scss">
.statistic-card {
  height: 100%;
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
  text-align: center;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.green {
  font-size: 5px;
  color: var(--el-color-success);
}
</style>