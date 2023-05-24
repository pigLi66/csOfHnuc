import { createStore } from "vuex"

export default createStore({
  state: {
    user: {
      isLogin: false,
      email: "",
      username: "",
      userId: 0,
    },

    socket: {
      // 连接状态
      isConnected: false,
      // 消息内容
      message: "",
      // 重新连接错误
      reconnectError: false,
      // 心跳消息发送时间
      heartBeatInterval: 50000,
      // 心跳定时器
      heartBeatTimer: 0,
    },
  },
  getters: {},
  mutations: {},
  actions: {},
  modules: {},
})
