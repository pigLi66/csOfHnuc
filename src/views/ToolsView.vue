<template>
    <div class="tools-view">
        <div class="tools-context">
            <el-tabs tab-position="left" class="demo-tabs">
                <el-tab-pane v-for="category in categoryList" :key="category" :label="category.name">
                    <div v-masonry style="width: 100%;" item-selector=".tool-card">
                        <button v-masonry-tile
                             class="tool-card"
                             v-for="item in [1,2,3,4,5,6,7,8,9]"
                             :key="item">
                            <moyu-form/>
                        </button>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
import {defineComponent} from "vue";
import MoyuForm from "@/component/widgets/MoyuForm.vue";

export default defineComponent({
    name: "ToolsView",
    components: {MoyuForm},

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
  background-color: rgba(255, 255, 255, 0.8);

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

.tools-context {
  margin: 20px;
}

.tool-card {
    width: calc(100vh / 2);
    margin: 1%;
    border: rgba(255, 255, 255, 0);
    border-radius: 10px;
}

.tool-card:hover {
    transform: scale(1.05, 1.05);
    transition: all 0.3s ease;
}

</style>