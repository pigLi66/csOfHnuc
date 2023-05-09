<template>
    <div class="search">
        <el-autocomplete
                placeholder="请输入内容"
                v-model="word"
                @keyup.enter="search()"
                class="search-input"
                style="width: 60%"
                :fetch-suggestions="autoComplete"
                :popper-append-to-body='false'
                ref="input"
                @select="search()"
        >
            <template #prepend>
                <el-select
                        class="search-engine-select"
                        v-model="searchEngines.select"
                        placeholder="请选择"
                        @change="searchEnginesChanged"
                >
                    <el-option
                            v-for="item in searchEngines.options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                    >
                        <span style="float: left">{{ item.label }}</span>
                    </el-option>
                </el-select>
            </template>
            <template #append>
                <el-button
                        class="search-button"
                        @click="search()"
                >
                    <el-icon>
                        <search/>
                    </el-icon>
                </el-button>
            </template>

        </el-autocomplete>
    </div>
</template>

<script lang="ts">
// import md5 from "js-md5";
import axios from "axios";
import Router from "vue-router";
import {defineComponent} from "vue";
import {ElMessage, FormInstance} from "element-plus";
import * as loginApi from "@/api/loginApi";
import store from "@/store";
import router from "@/router";
import {Search} from "@element-plus/icons-vue";

const default_suggest_url =
    "https://suggestion.baidu.com/su?wd=%word%&cb=window.baidu.sug";
const default_suggest_func =
    "window.baidu={sug:function(json){cb(json.s.map(function(x){return{'value':x}}))}}";
const api = {
    searchEngines: "/search/searchEngines",
    searchLog: "/search/searchLog",
};

type EngineOption = {
    id: any,
    value: any
    main_url: any
    suggest_url: any
    suggest_func: any
    icon: any
    label: any
}

export default defineComponent({
    name: "SearchEngine",
    components: {Search},
    props: {
        user_id: Number,
    },
    data() {
        return {
            word: "",
            searchIcon: "search-icon el-icon-search",
            searchEngines: {
                select: "",
                select_engine_id: 0,
                main_url: "",
                suggest_url: "",
                suggest_func: "",
                options: Array<EngineOption>(),
            },
        };
    },
    methods: {
        searchEnginesChanged() {

            for (let x = 0; x < this.searchEngines.options.length; x++) {
                if (this.searchEngines.select == this.searchEngines.options[x].value) {
                    this.searchEngines.select_engine_id = this.searchEngines.options[
                        x
                        ].id;
                    this.searchEngines.main_url = this.searchEngines.options[x].main_url;
                    this.searchEngines.suggest_url = this.searchEngines.options[
                        x
                        ].suggest_url;
                    this.searchEngines.suggest_func = this.searchEngines.options[
                        x
                        ].suggest_func;
                    this.searchIcon = this.searchEngines.options[x].icon;
                    return;
                }
            }
        },
        async searchEnginesGet() {
            try {
                const {data: res} = await axios.get(api.searchEngines);
                for (let s = 0; s < res.data.length; s++) {
                    let suggest_url = default_suggest_url;
                    let suggest_func = default_suggest_func;
                    // if (md5(res.data[s].suggest_func) == res.data[s].s) {
                    //     suggest_url = res.data[s].suggest_url;
                    //     suggest_func = res.data[s].suggest_func;
                    // }
                    this.searchEngines.options.push({
                        id: res.data[s].id,
                        main_url: res.data[s].main_url,
                        suggest_url: suggest_url,
                        suggest_func: suggest_func,
                        icon: res.data[s].icon,
                        label: res.data[s].name,
                        value: res.data[s].name,
                    });
                }
                this.searchEngines.select = this.searchEngines.options[0].value;
                this.searchEngines.select_engine_id = this.searchEngines.options[0].id;
                this.searchEngines.main_url = this.searchEngines.options[0].main_url;
                this.searchEngines.suggest_url = this.searchEngines.options[0].suggest_url;
                this.searchEngines.suggest_func = this.searchEngines.options[0].suggest_func;
                this.searchIcon = this.searchEngines.options[0].icon;
            } catch (e: any) {
                console.log(e);
                ElMessage.error(e.response.data.msg)
            }
        },
        async search() {
            const searchUrl = this.searchEngines.main_url.replace("%word%", this.word);
            window.open(searchUrl);
            this.autoComplete("");
            try {
                const {data: res} = await axios.post(api.searchLog, {
                    user_id: this.user_id,
                    engine_id: this.searchEngines.select_engine_id,
                    search_text: this.word,
                });
            } catch (e) {
                console.log(e);
            }
            this.word = "";
        },
        async autoComplete(queryString: any, cb: any = null) {
            if (
                queryString === "" ||
                queryString === [] ||
                queryString === undefined
            ) {
                try {
                    cb([]);
                } catch (e) {
                    console.log(e)
                }
            } else {
                let autoCompleteUrl = this.searchEngines.suggest_url.replace(
                    "%word%",
                    this.word
                );
                try {
                    let script = document.createElement("script");
                    script.src = autoCompleteUrl;
                    eval(this.searchEngines.suggest_func);
                    document.querySelector("head")?.appendChild(script);
                    document.querySelector("head")?.removeChild(script);
                } catch (e: any) {
                    console.log(e);
                    ElMessage.error(e.response.data.msg)
                }
            }
        },
    },
    created() {
        this.searchEnginesGet();
    },
    mounted() {
        // (this.$refs['input'] as FormInstance)
        // (this.$refs['input'] as FormInstance).validate((isValid: boolean) => {
        //     if (isValid) {
        //         console.log(isValid)
        //         loginApi.login(this.loginForm).then((res) => {
        //             console.log(res.data)
        //             store.state.token = res.data.token
        //             router.push("/home")
        //         })
        //     }
        // })
        // this.$refs["input"].focus();
    },
});
</script>

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