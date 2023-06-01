const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8081", // api前缀
        ws: true, // 代理websocket
        changeOrigin: true, // 虚拟的站点需要更管origin
        pathRewrite: {
          "/api": "",
        },
      },
    },
  },
});
