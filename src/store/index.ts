import { createStore } from "vuex";

/**
 * 已固定到导航页的工具卡片Map
 */
export interface FixedToolCardMap {
  [key: string]: { top: number; left: number };
}

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
