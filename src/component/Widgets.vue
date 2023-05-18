<template>
    <div class="widget-view">
        <el-tabs
                v-model="widgetSuiteLabelActiveName"
                @tab-click="handleClick"
                stretch="true"
                style="
        padding-bottom: 30px;
        width: 70%;
        text-align: center;
        margin: 0 auto;
      "

        >
            <el-tab-pane
                    v-for="singleWidgetSuite in widgetSuiteLabelList"
                    :label="singleWidgetSuite.name"
                    :name="singleWidgetSuite.id"
                    :key="singleWidgetSuite"
            ></el-tab-pane>
        </el-tabs>
        <el-row
                class="margin_bottom-large"
                v-for="(singleWidgetSuite, suiteIndex) in widgetSuite"
                :key="singleWidgetSuite"
        >
            <el-col
                    :span="singleWidget.span"
                    v-for="(singleWidget, index) in singleWidgetSuite"
                    :key="singleWidget"
            >
                <el-card
                        shadow="hover"
                        v-show="singleWidget.show"
                        class="margin_left-medium margin_right-medium"
                        style="width: 330px"
                >
                    <notes
                            style="height: 330px; width: 300px"
                            v-if="singleWidget.name == 'notes'"
                            :user_id="userId"
                            :widget_id="singleWidget.id"
                            :buttons="singleWidget.buttons"
                            :flush="singleWidget.autoUpdate"
                            @done="done(suiteIndex, index)"
                    />
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>
<script lang="ts">
import notes from "./widgets/Notes.vue";
import {defineComponent} from "vue";
import * as widgetApi from "@/api/WidgetApi";


type Widget = {
    id: number
    span: number
    name: string
    buttons: any
    autoUpdate: any
    show: boolean
    flush: boolean
}

export default defineComponent({
    name: "widgets",
    props: {
        userId: Number,
    },
    components: {
        notes,
    },
    watch: {
        userId(newVal, oldVal) {
            this.widgetSuiteLabelGet();
        },
    },
    data() {
        return {
            widgetSuiteLabelActiveName: "",
            widgetSuiteLabelList: Array<{ id: string, name: string }>(),
            widgetSuite: Array<Array<Widget>>(),
            widget: Array<Widget>(),
        };
    },
    methods: {
        async widgetSuiteLabelGet() {
            // let resp = await widgetApi.get({
            //     userId: this.userId,
            // })
            // this.widgetSuiteLabelList = resp.data.list;
            if (!this.widgetSuiteLabelList) {
                this.widgetSuiteLabelList = [
                    {id: "1", name: "yahaha"}, {id: "2", name: "你好"}
                ]
            }

            //这里有一个坑，当以数字作为el-tab-panel的name时，tab下方标识当前被触发tab的横条不能被正确计算并显示，所以要将el-tab-panel的name转化为字符串
            for (let x = 0; x < this.widgetSuiteLabelList.length; x++) {
                this.widgetSuiteLabelList[x].id = String(this.widgetSuiteLabelList[x].id);
            }

            this.widgetSuite = [
                [{
                    id: 1,
                    span: 1,
                    name: "notes",
                    buttons: "add",
                    autoUpdate: false,
                    show: true,
                    flush: true
                }]
            ]

            // if (this.widgetSuiteLabelList.length != 0) {
            //     this.widgetSuiteLabelActiveName = this.widgetSuiteLabelList[0].id;
            //     this.widgetGet(this.widgetSuiteLabelList[0].id);
            // }
        },

        handleClick(activeTab: { name: string }) {
            this.widgetGet(activeTab.name);
        },

        async widgetGet(widgetSuiteLabelActiveId: string) {
            let resp = await widgetApi.get({
                userId: this.userId,
                widgetSuiteId: Number(widgetSuiteLabelActiveId),
            })

            let widgetSuiteLabelList = resp.data.list

            for (let x = 0; x < widgetSuiteLabelList.length; x++) {
                widgetSuiteLabelList[x].show = false;
                widgetSuiteLabelList[x].flush = false;
            }
            this.widget = widgetSuiteLabelList;
            await this.widgetSuiteGenerate();
        },
        widgetSuiteGenerate() {
            let count = 0;
            this.widgetSuite = [];
            this.widgetSuite.push([]);
            for (let x = 0; x < this.widget.length; x++) {
                count += this.widget[x].span;
                if (count > 24) {
                    this.widgetSuite.push([]);
                    count = 0;
                    count += this.widget[x].span
                }
                this.widgetSuite[this.widgetSuite.length - 1].push(this.widget[x]);
            }
        },
        done(suiteIndex: number, index: number) {
            this.widgetSuite[suiteIndex][index].show = true;
            this.widgetSuite[suiteIndex][index].flush = false;
        },
    },
    mounted() {
        this.widgetSuiteLabelGet();
    },
});
</script>
<style>
.widget-view {
    width: 100vw;
    height: 100vh;
    margin: 20px;
    background-color: rgba(255, 255, 255, 0.7);

    border-radius: 30px;
}
</style>
