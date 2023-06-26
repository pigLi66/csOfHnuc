<!-- eslint-disable @typescript-eslint/no-unused-vars -->
<script lang="ts">
import {defineComponent} from "vue";
import store from "../../store";
import {ElMessage, ElMessageBox} from "element-plus";

export default defineComponent({
  name: "ToolCardListItem",

  props: {
    id: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    html: {
      type: String,
      required: true,
    },
    desc: String,
  },

  data() {
    return {
      initial: true,
      opacity: 0,
      state: store.state,
    };
  },
  methods: {
    addFixedCustomCard() {
      const fixedCustomCard = this.state.fixedCustomWidgetCache;
      if (fixedCustomCard[this.id]) {
        ElMessage.error(`已经添加了，请勿重复添加`);
      } else {
        fixedCustomCard[this.id] = {top: 32, left: 32, html: this.html};
      }
    },

    deleteFixedCustomCard() {
      const fixedCustomCard = this.state.fixedCustomWidgetCache;
      if (fixedCustomCard[this.id]) {
        ElMessageBox.confirm("是否移除主页的工具卡片").then(() => {
          delete fixedCustomCard[this.id];
        });
      }
    },

    isFixed(): boolean {
      const fixedCustomCard = this.state.fixedCustomWidgetCache;
      return fixedCustomCard[this.id] !== undefined;
    },
  },
});
</script>

<template>
  <el-row class="scrollbar-item">
    <el-col :span="6" class="scrollbar-item-title">
      <div>{{ title }}</div>
    </el-col>
    <el-col :span="13" class="scrollbar-item-desc"> {{ desc }}</el-col>
    <el-col :span="4">
      <el-button-group>
        <!-- 编辑 -->
        <el-tooltip
            content="编辑卡片"
            effect="customized"
            placement="top"
            show-after="500"
        >
          <el-button circle class="item-opt-btn-star"
          >
            <font-awesome-icon :icon="['fas', 'pen-to-square']"
            />
          </el-button>
        </el-tooltip>
        <!-- 新增 -->
        <el-tooltip
            v-if="!isFixed()"
            content="将卡片添加到主页"
            effect="customized"
            placement="top"
            show-after="500"
        >
          <el-button
              circle
              class="item-opt-btn-add"
              @click="addFixedCustomCard()"
          >
            <font-awesome-icon :icon="['far', 'square-plus']"/>
          </el-button>
        </el-tooltip>
        <!-- 移除卡片 -->
        <el-tooltip
            v-if="isFixed()"
            content="移除主页的卡片"
            effect="customized"
            placement="top"
            show-after="500"
        >
          <el-button
              circle
              class="item-opt-btn-delete"
              @click="deleteFixedCustomCard()"
          >
            <font-awesome-icon :icon="['fas', 'trash']"/>
          </el-button>
        </el-tooltip>
      </el-button-group>
    </el-col>
    <el-col :span="1">
      <el-tooltip
          content="删除卡片"
          effect="customized"
          placement="top"
          show-after="500"
      >
        <el-button circle class="item-opt-btn-remove">
          <font-awesome-icon :icon="['fas', 'xmark']"/>
        </el-button>
      </el-tooltip>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.scrollbar-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 5px;
  // text-align: center;
  border-radius: 4px;
  background: #2c3246;
  // flex-direction: column-reverse;
}

.scrollbar-item:hover {
  background: linear-gradient(45deg, #4a4e5e, rgb(105, 148, 165));
  transform: scaleY(1.1);
  transition: all 0.3s;
}

.scrollbar-item:hover .item-opt-btn-remove {
  display: flex;
}

.scrollbar-item-title,
.scrollbar-item-desc {
  color: #fff;
  text-align: left;
  display: flex;
  align-items: center;
}

.scrollbar-item-title {
  padding-left: 20px;
}

.scrollbar-item-desc {
  font-size: 12px;
  color: #b0b8da;
}

.item-opt-btn-remove,
.item-opt-btn-add,
.item-opt-btn-star,
.item-opt-btn-delete {
  background-color: rgba($color: #000000, $alpha: 0);
  border: rgba($color: #000000, $alpha: 0);
  color: var(--el-text-color-primary);
}

.item-opt-btn-remove {
  display: none;
}

.item-opt-btn-add:hover {
  color: #19eeee;
}

.item-opt-btn-star:hover {
  color: var(--el-color-warning);
}

.item-opt-btn-remove:hover,
.item-opt-btn-delete:hover {
  color: var(--el-color-danger);
}
</style>
