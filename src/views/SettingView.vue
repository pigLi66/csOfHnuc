<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "SettingView",

  data() {
    return {
      titleName: localStorage.getItem("titleName")
        ? localStorage.getItem("titleName")
        : "工作搜索",
    };
  },

  methods: {
    saveCfg() {
      if (this.titleName) {
        localStorage.setItem("titleName", this.titleName);
        location.reload();
      }
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  watch: {
    titleName(newVar: string) {
      localStorage.setItem("titleName", newVar);
    },
  },
});
</script>
<template>
  <div class="view">
    <el-card class="setting-card-view">
      <el-container>
        <!-- 左侧的用户个人界面 -->
        <el-aside width="200px">
          设置
          <el-divider />
          <div class="setting-button">
            <el-button style="width: 100%" type="primary" @click="saveCfg()">
              <font-awesome-icon :icon="['fas', 'floppy-disk']" size="lg" />
              &nbsp;保存设置
            </el-button>
          </div>
          <div class="setting-button">
            <el-button style="width: 100%" type="warning">
              <font-awesome-icon :icon="['fas', 'download']" size="lg" />
              &nbsp;导入设置文件</el-button
            >
          </div>
          <div class="setting-button">
            <el-button style="width: 100%" type="warning">
              <font-awesome-icon :icon="['fas', 'upload']" size="lg" />
              &nbsp;导出设置文件</el-button
            >
          </div>
          <div class="setting-button">
            <el-button style="width: 100%" type="success">
              <font-awesome-icon
                :icon="['fas', 'cloud-arrow-down']"
                size="lg"
              />
              &nbsp;载入云端设置</el-button
            >
          </div>
          <div class="setting-button">
            <el-button style="width: 100%" type="success">
              <font-awesome-icon :icon="['fas', 'cloud-arrow-up']" size="lg" />
              &nbsp;同步至云端</el-button
            >
          </div>
          <div class="setting-button">
            <el-button style="width: 100%" type="danger">
              <font-awesome-icon :icon="['fas', 'eraser']" size="lg" />
              &nbsp;还原默认设置</el-button
            >
          </div>
        </el-aside>
        <!-- 右侧主要内容 -->
        <el-main>
          <el-form>
            <el-form-item label="主页标题">
              <el-input v-model="titleName"></el-input>
            </el-form-item> </el-form
        ></el-main>
      </el-container>
    </el-card>
  </div>
</template>

<style lang="scss">
.view {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.setting-card-view {
  width: 93vw;
  height: 100vh;
  background: radial-gradient(ellipse at bottom, #253241 0%, #171a27 100%);
}

.el-form-item__label {
  color: var(--el-text-color-primary);
}

.button-row {
  width: 100%;
  margin-top: 20px;
  position: fixed;
  align-items: center;
}

.setting-button {
  width: 100%;
  margin-top: 20px;
  margin-left: 0px;
}
</style>
