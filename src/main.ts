import {createApp} from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import VueNativeSocket from 'vue-native-websocket-vue3';
import axios from "axios";

axios.defaults.withCredentials = true; //true允许跨域

const app = createApp(App)

app.use(store)
    .use(router)
    .mount('#app');

app.use(VueNativeSocket,
    {
        // 启用Vuex集成
        store: store,
        // 数据发送格式
        format: "json",
        // 开启手动调用connect()链接服务器,
        connectManually: true,
        // 关闭自动重连
        reconnection: false
    })

export default app