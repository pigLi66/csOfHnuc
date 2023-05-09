<template>
    <div class="tools-view" >
        <el-container style="height: 100%; width: 100%">
            <el-aside style="width: 20%">
                <el-scrollbar>
                    <el-menu>
                        <div>

                        </div>
                        <el-menu-item v-for="(category, index) in categoryList" :key="category.key" index="{{index.toString()}}">
                            {{ category.name }}&nbsp;
                            <el-text style="background-color: #409EFF; border-radius: 8px; color: white">
                                &nbsp;{{ category.count }}&nbsp;
                            </el-text>
                        </el-menu-item>
                    </el-menu>
                </el-scrollbar>
            </el-aside>
        </el-container>
        <div class="tool-list"></div>
    </div>
</template>

<script>
import {defineComponent} from "vue";

export default defineComponent({
    name: "ToolsView",

    data() {
        return {
            categoryList: [
                {name: "摸鱼组件", key: "moyu", count: 12},
                {name: '工具组件', key: 'tool', count: 3},
                {name: '其他组件', key: 'other', count: 30}
            ]
        }
    }
})
</script>

<style scoped lang="scss">

.tools-view {
  width: 100vw;
  height: 100vh;
  margin: 20px;
    background-color: rgba(255, 255, 255, 0.7);

  border-radius: 30px;
}

.tool-category-col {
  width: 20vw;
  height: 100%;
  horiz-align: center;
  vertical-align: center;
}

.tool-category {
  margin-top: 50px;
}

.tool-list {
  width: 80vw;
}

.category-button {
  margin-left: 20px;
  margin-right: 20px;
  width: 100%;
  border-radius: 0px;
}
</style>