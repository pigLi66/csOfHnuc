<script lang="ts">
import {defineComponent} from "vue";
import ToolCard from "../common/ToolCard.vue";
import {GetQuestionResp} from "@/type/api/Leetcode";
import {getQuestionOfToday} from "@/api/leetcode";
import store from "@/store";

export default defineComponent({
  components: {ToolCard},
  name: "LeetcodeCard",
  props: {
    fixed: Boolean,
  },

  data() {
    return {
      name: this.$options.name,
      question: {} as GetQuestionResp,
    };
  },

  mounted() {
    this.initQuestionOfToday();
  },

  methods: {

    initQuestionOfToday() {

      let cache = store.state.fixedWidgetCache[this.name]
      if (cache && cache.status) {
        this.question = cache.status
      } else {
        getQuestionOfToday().then((resp) => {
          this.question = resp.question;
          store.state.fixedWidgetCache.set(this.name, this.question)
        });
      }
    },

    levelColor(): string {
      if (this.question.level === "Medium") {
        return "rgb(255,161,25,1)";
      } else if (this.question.level === "Easy") {
        return "rgb(90,183,38,1)";
      } else {
        return "rgb(239,71,67,1)";
      }
    },

    openLeetcode() {
      window.open(this.question.url);
    },
  },
});
</script>

<template>
  <tool-card :name="name" :fixed="fixed" style="width: 200px">
    <el-descriptions title="每日一题"
                     size="small"
                     column="1"
                     @click="openLeetcode()">
      <template #extra>
        <div class="level" :style="{ color: levelColor()}">
          {{ question.level }}
        </div>
      </template>
      <el-descriptions-item class="desc-item">
        {{ question.translatedTitle }}
      </el-descriptions-item>
      <el-descriptions-item>
        <el-tag size="small" v-for="tag in question.topicTags" :key="tag"
                class="tag">
          {{ tag }}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </tool-card>
</template>

<style scoped lang="scss">
.level {
  font-size: small;
  font-weight: bolder;
  //background-color: azure;
  border-radius: 21px;
}

.tag {
  margin-left: 2px;
  margin-right: 2px;
}

.desc-item {
  background-color: rgba(0, 0, 0, 0)
}

.green {
  font-size: 5px;
  color: var(--el-color-success);
}
</style>
