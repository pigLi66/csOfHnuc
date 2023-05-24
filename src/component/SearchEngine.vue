<script lang="ts">
// import md5 from "js-md5";
// import axios from "axios";
import { defineComponent } from "vue";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ElMessage } from "element-plus";
// import * as loginApi from "@/api/LoginApi";
// import store from "@/store";
// import router from "@/router";
import { Search } from "@element-plus/icons-vue";

interface EngineOption {
  [key: string]: {
    name: string;
    mainUrl: string;
    suggestUrl: string;
    suggestFunc: any;
    icon: string;
  };
}

export default defineComponent({
  name: "SearchEngine",
  components: { Search },
  props: {
    userId: Number,
  },
  data() {
    return {
      word: "",
      searchIcon: "search-icon el-icon-search",
      searchEngines: {
        currentEngines: "baidu",
        options: {
          baidu: {
            name: "百度",
            mainUrl:
              "http://www.baidu.com/s?wd=%word%&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baidulocal&inputT=2969",
            suggestUrl:
              "https://suggestion.baidu.com/su?wd=%word%&cb=window.baidu.sug",
            suggestFunc:
              "window.baidu = { sug: function (json) { callback(json.s.map(function (x) { return { 'value': x } })) } }",
            icon: "",
          },
        } as EngineOption,
      },
    };
  },
  methods: {
    async search() {
      const currentEnginesOption =
        this.searchEngines.options[this.searchEngines.currentEngines];
      const searchUrl = currentEnginesOption.mainUrl.replace(
        "%word%",
        this.word
      );
      window.open(searchUrl);
      this.autoComplete("");
    },

    async autoComplete(
      queryString: string,
      callback?: (query: string[]) => void
    ) {
      if (!queryString && callback) {
        // eslint-disable-next-line standard/no-callback-literal
        callback([]);
      } else {
        const currentEnginesOption =
          this.searchEngines.options[this.searchEngines.currentEngines];
        const autoCompleteUrl = currentEnginesOption.suggestUrl.replace(
          "%word%",
          this.word
        );
        try {
          const script = document.createElement("script");
          script.src = autoCompleteUrl;
          // eslint-disable-next-line no-eval
          eval(currentEnginesOption.suggestFunc);
          document.querySelector("head")?.appendChild(script);
          document.querySelector("head")?.removeChild(script);
        } catch (e: any) {
          console.log(e);
          ElMessage.error(e.response.data.msg);
        }
      }
    },
  },
});
</script>

<!-- eslint-disable no-eval -->
<template>
  <div class="search">
    <el-autocomplete
      clearable
      placeholder="请输入内容"
      v-model="word"
      @keyup.enter="search()"
      class="search-input"
      style="width: 60%"
      :fetch-suggestions="autoComplete"
      :popper-append-to-body="false"
      ref="input"
      debounce="0"
      @select="search()"
    >
      <template #prepend>
        <el-select
          class="search-engine-select"
          v-model="searchEngines.currentEngines"
          placeholder="请选择"
        >
          <el-option
            v-for="(value, key) in searchEngines.options"
            :key="key"
            :label="value.name"
            :value="key"
          >
            <span style="float: left">{{ value.name }}</span>
          </el-option>
        </el-select>
      </template>
      <template #append>
        <el-button class="search-button" @click="search()">
          <el-icon>
            <search />
          </el-icon>
        </el-button>
      </template>
    </el-autocomplete>
  </div>
</template>

<style scoped lang="scss">
.search {
  width: 100%;
  //text-align: center;
  horiz-align: center;

  .search-input {
    margin-left: 50px;
    margin-right: 50px;
    width: 70%;
  }
}

.search-icon-div {
  padding-top: 80px;
  padding-bottom: 60px;
}

.search-engine-select {
  width: 100px;
}

.search-button {
  width: 70px;
}
</style>
