import {createStore} from "vuex";
import {CustomWidgetCacheMap, widgetCache} from "@/type/store";


export default createStore({
  state: {
    user: {
      isLogin: false,
      email: "",
      username: "",
      userId: 0,
      avatar: "",
    },
    fixedWidgetCache: widgetCache, // 记录套件的位置，以及状态信息，并可以缓存到local
    fixedCustomWidgetCache: {} as CustomWidgetCacheMap, // 记录自定义套件的位置，以及html
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {},
});
