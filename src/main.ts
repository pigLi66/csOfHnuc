import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import axios from "axios";
import {createApp} from 'vue'
import VueCookies from "vue-cookies";
import VueClipboard from 'vue-clipboard2'
import VueNativeSocket from 'vue-native-websocket-vue3';

import ElementPLus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'

import * as echarts from 'echarts'

import 'vfonts/Lato.css' // 通用字体
import 'vfonts/FiraCode.css' // 等宽字体
import 'font-awesome/css/font-awesome.min.css'

import {VueMasonryPlugin} from 'vue-masonry'

axios.defaults.withCredentials = true; //true允许跨域

const app = createApp(App)

const globalProperties = app.config.globalProperties;
globalProperties.$echarts = echarts
globalProperties.$axios = axios

app.use(VueCookies)
    .use(VueMasonryPlugin)
    // .use(Vuex)
    // .use(VCharts)
    .use(VueClipboard)
    .use(ElementPLus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(store)
    .use(router)
    // .use(ElementPLus)
    .mount('#app');

app.use(VueNativeSocket, "", {
    store: store,   // 启用Vuex集成
    format: "json", // 数据发送格式
    connectManually: true,  // 开启手动调用connect()链接服务器,
    reconnection: false // 关闭自动重连
})

export default app