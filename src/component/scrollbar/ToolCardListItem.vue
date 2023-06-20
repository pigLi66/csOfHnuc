<!-- eslint-disable @typescript-eslint/no-unused-vars -->
<script lang="ts">
import {defineComponent} from "vue";
import store from "../../store";
import {ElMessage, ElMessageBox} from "element-plus";

export default defineComponent({
  name: "ToolCardListItem",

  props: {
    name: {
      type: String,
      required: true,
    },
    title: {
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
    addFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard;
      if (fixedToolCard[this.name]) {
        ElMessage.error(`已经添加了，请勿重复添加`);
      } else {
        const position = {top: 32, left: 32};
        fixedToolCard[this.name] = position;
        localStorage[`ToolCard:${this.name}`] = JSON.stringify(position);
        ElMessage.success("添加成功，前往主页就能看到")
      }
    },

    deleteFixedToolCard() {
      const fixedToolCard = this.state.fixedToolCard;
      if (fixedToolCard[this.name]) {
        ElMessageBox.confirm("是否移除主页的工具卡片").then(() => {
          delete fixedToolCard[this.name];
          delete localStorage[`ToolCard:${this.name}`];
        });
      }
    },

    isFixed(): boolean {
      const fixedToolCard = this.state.fixedToolCard;
      return fixedToolCard[this.name] !== undefined;
    },
  },
});
</script>

<template>
  <el-card class="scrollbar-item">
    <el-row>
      <!-- 卡片前缀 -->
      <div style="display: inline">
        <slot name="prefix"></slot>
      </div>
      <!--      <el-col :span="6" v-if="$slots.prefix">-->
      <!--        -->
      <!--      </el-col>-->
      <!--  卡片介绍 -->
      <div style="display: inline;height: auto;float: left;flex-grow: 1">
        <el-row>
          <el-col :span="20" class="scrollbar-item-title">
            <el-descriptions :title="title">
              <el-descriptions-item>{{ desc }}</el-descriptions-item>
            </el-descriptions>
          </el-col>
          <!-- 卡片操作按钮 -->
          <el-col :span="4">
            <el-button-group>
              <!-- 收藏 -->
              <el-tooltip
                  content="收藏卡片"
                  show-after="500"
                  placement="top"
                  effect="customized"
              >
                <el-button circle class="item-opt-btn-star"
                >
                  <font-awesome-icon :icon="['far', 'star']" size="lg"
                  />
                </el-button>
              </el-tooltip>
              <!-- 新增 -->
              <el-tooltip
                  v-if="!isFixed()"
                  content="将卡片添加到主页"
                  show-after="500"
                  placement="top"
                  effect="customized"
              >
                <el-button
                    circle
                    class="item-opt-btn-add"
                    @click="addFixedToolCard()"
                >
                  <font-awesome-icon :icon="['far', 'square-plus']" size="lg"/>
                </el-button>
              </el-tooltip>
              <!-- 移除 -->
              <el-tooltip
                  v-if="isFixed()"
                  content="移除主页的卡片"
                  show-after="500"
                  placement="top"
                  effect="customized"
              >
                <el-button
                    circle
                    class="item-opt-btn-delete"
                    @click="deleteFixedToolCard()"
                >
                  <font-awesome-icon :icon="['fas', 'trash']" size="lg"/>
                </el-button>
              </el-tooltip>
            </el-button-group>
          </el-col>
        </el-row>
      </div>
    </el-row>
  </el-card>

</template>

<style scoped lang="scss">
@import "src/style/scrollbar";

.scrollbar-item {
  height: auto;
  margin: 10px;
  padding: 10px;
  background-color: white;
  border-radius: 4px;
}

.scrollbar-item:hover {
  //background: linear-gradient(45deg, #4a4e5e, rgb(105, 148, 165));
  transform: translateX(10px);
  transition: all 0.1s;
}

.scrollbar-item-title,
.scrollbar-item-desc {
  color: #fff;
  text-align: left;
  display: flex;
}

.scrollbar-item-title {
  padding-left: 20px;
}

.scrollbar-item-desc {
  font-size: 12px;
  color: #b0b8da;
}

.item-opt-btn-add,
.item-opt-btn-star,
.item-opt-btn-delete {
  background-color: rgba($color: #000000, $alpha: 0);
  border: rgba($color: #000000, $alpha: 0);
  color: var(--el-text-color-primary);
}

.item-opt-btn-add {
  color: var(--el-color-primary);
}

.item-opt-btn-star {
  color: var(--el-color-warning);
}

.item-opt-btn-delete {
  color: var(--el-color-danger);
}
</style>
