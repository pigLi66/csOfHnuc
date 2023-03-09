import {createApp} from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import VueNativeSocket from 'vue-native-websocket-vue3';
import axios from "axios";
// import ElementPLus from 'element-plus'
// import 'element-plus/dist/index.css'
// import 'font-awesome/css/font-awesome.min.css'
import * as echarts from 'echarts'
// 通用字体
import 'vfonts/Lato.css'
// 等宽字体
import 'vfonts/FiraCode.css'

axios.defaults.withCredentials = true; //true允许跨域

const app = createApp(App)

const globalProperties = app.config.globalProperties;
globalProperties.$echarts = echarts
globalProperties.$axios = axios

app.use(store)
    .use(router)
    // .use(ElementPLus)
    .mount('#app');

app.use(VueNativeSocket , "", {
        store: store,   // 启用Vuex集成
        format: "json", // 数据发送格式
        connectManually: true,  // 开启手动调用connect()链接服务器,
        reconnection: false // 关闭自动重连
    })

export default app