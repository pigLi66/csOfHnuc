<script lang="ts">
import { defineComponent } from "vue";
import ToolCard from "../common/ToolCard.vue";
import { GetQuestionResp, GetQuestionStateResp } from "@/type/api/Leetcode";
import { getQuestionOfToday } from "@/api/leetcode";

export default defineComponent({
  components: { ToolCard },
  name: "LeetcodeCard",
  props: {
    fixed: Boolean,
  },

  data() {
    return {
      name: this.$options.name,
      question: {} as GetQuestionResp,
      state: {} as GetQuestionStateResp,
    };
  },

  mounted() {
    if (localStorage.getItem("token")) {
      this.initQuestionOfToday();
    }
  },

  methods: {
    getRandomString(n: number) {
      let str = "";
      while (str.length < n) {
        str += Math.random().toString(36).substring(2);
      }
      return str.substring(str.length - n);
    },
    initQuestionOfToday() {
      getQuestionOfToday().then((resp) => {
        this.question = resp.question;
        this.state = resp.state;
      });
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
    <div @click="openLeetcode()">
      <el-row>
        <div style="font-size: small">每日一题&nbsp;</div>
        <div class="level" :style="{ color: levelColor() }">
          {{ question.level }}
        </div>
      </el-row>
      <div>{{ question.translatedTitle }}</div>
    </div>
  </tool-card>
</template>

<style scoped lang="scss">
.level {
  font-size: small;
  font-weight: bolder;
  background-color: azure;
  border-radius: 21px;
}

.green {
  font-size: 5px;
  color: var(--el-color-success);
}
</style>
