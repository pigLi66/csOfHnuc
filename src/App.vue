<template>
    <div class="app" v-if="initial">
        <github-conner/>
<!--        <el-row class="loginRow">-->
<!--        </el-row>-->
        <el-row>
            <search-engine/>
        </el-row>
    </div>
</template>

<script lang="ts">

import githubConner from "@/component/common/GithubConner.vue";
import SearchEngine from "@/component/SearchEngine.vue";

export default {
    components: {
        SearchEngine,
        githubConner,
    },

    data() {
        return {
            initial: true
        }
    },

    // mounted() {
    //     // document.body.style.setProperty("--el-color-primary", "#ffeba7")
    //     // document.body.style.setProperty("--el-color-primary-dark-2", "#c3ab80")
    //     // document.body.style.setProperty("--el-color-primary-light-3", "#dcc593")
    // }
}
</script>

<style lang="scss">
* {
  /*配置内外边距为0，因为不同浏览器的默认边距可能不一样*/
  padding: 0px;
  margin: 0px;
}

html, body, #app {
  width: 100%;
  height: 100%;
}

#app {
  font-family: Helvetica, sans-serif;
  text-align: center;
}

</style>
