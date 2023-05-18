<template>

    <div class="app" v-if="initial">
        <!--        固定背景-->
        <div class="bg-div">
            <div class="bg-filter">
                <img src="../src/assets/bg.jpg" style="width: 100vw; height: 100vh"/>
            </div>
        </div>
        <!--        回到顶部-->
        <el-backtop :right="40" :bottom="100"/>
        <github-conner/>
        <login/>
        <el-row class="search-row">
            <h1 class="title">工作搜索</h1>
            <search-engine/>
        </el-row>
        <el-row>
            <!--            <tools-view/>-->
            <tools-view/>
        </el-row>
    </div>
</template>

<script lang="ts">

import githubConner from "@/component/common/GithubConner.vue";
import SearchEngine from "@/component/SearchEngine.vue";
import Login from "@/component/Login.vue";
import ToolsView from "@/views/ToolsView.vue";

export default {
    components: {
        ToolsView,
        // Widgets,
        Login,
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

.search-row {
  //margin-top: 40vh;
  margin-bottom: 55vh;

  .title {
    margin-top: 30vh;
    margin-bottom: 5vh;
    text-align: center;
    horiz-align: center;
    width: 100%;
    color: white;
    text-shadow: 2px 2px 10px #ececec;
  }
}

.bg-div {
  width: 100%;
  height: 100%;
  overflow: hidden;

  .bg-filter {
    position: fixed;
    filter: blur(6px); // 磨砂质感
    transform: scale(1.2)
  }
}
</style>
