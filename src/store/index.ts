import {createStore} from "vuex";
import {ComponentCache, FixedCustomCardMap, FixedToolCardMap} from "@/type/store";

/**
 * 已固定到导航页的官方工具卡片Map
 */

export default createStore({
  state: {
    user: {
      isLogin: false,
      email: "",
      username: "",
      userId: 0,
      avatar: "",
    },
    // todo 需要从后台接口初始化
    fixedToolCard: {} as FixedToolCardMap,
    fixedCustomCard: {} as FixedCustomCardMap,
    // 组件的共有缓存
    componentCache: {} as ComponentCache<any>,
    curUrl: {
      path: "",
      anchors: "",
      param: new Map<string, string>(),
    },
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {},
});
