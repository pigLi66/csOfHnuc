<script lang="ts">
import { defineComponent } from "vue";
import ToolCard from "../common/ToolCard.vue";
import { GetQuestionResp, GetQuestionStateResp } from "@/type/Leetcode";
import {
  getQuestionOfToday,
  getQuestion,
  getQuestionState,
} from "@/api/Leetcode";

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

  created() {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "https://leetcode.cn/graphql/", true);
    xhr.withCredentials = true;
    // xhr.setRequestHeader("Content-Type", "application/json")
    xhr.send(JSON.stringify({
      operationName: "questionOfToday",
      variables: {},
      query:
        "query questionOfToday { todayRecord {   question {     questionFrontendId     questionTitleSlug     __typename   }   lastSubmission {     id     __typename   }   date   userStatus   __typename }}",
    }));

    this.initQuestionOfToday();
  },

  methods: {
    getRandomString(n: number) {
      let str = "";
      while (str.length < n) {
        str += Math.random().toString(36).substr(2);
      }
      return str.substr(str.length - n);
    },
    initQuestionOfToday() {
      getQuestionOfToday().then((resp) => {
        getQuestion(resp.questionTitle).then((it) => (this.question = it));
        getQuestionState(resp.questionTitle).then((it) => (this.state = it));
      });
    },
  },
});
</script>

<template>
  <tool-card :name="name" :fixed="fixed" style="width: 200px">
    <div>{{ question.translatedTitle }}</div>
    <div>{{ question.level }}</div>
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
