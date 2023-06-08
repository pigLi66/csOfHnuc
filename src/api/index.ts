import axios, { InternalAxiosRequestConfig } from "axios";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import store from "@/store";
import { ElMessage } from "element-plus";

// 配置请求
export const service = axios.create({
  baseURL: process.env.VUE_APP_BACKEND_URL,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

// 配置请求拦截器，请求头设置token
service.interceptors.request.use(
  (options: InternalAxiosRequestConfig) => {
    options.withCredentials = true;
    return options;
  },
  (error) => Promise.reject(error)
);

// 配置响应拦截
service.interceptors.response.use(
  (res) => {
    const errMsg: string = res.data.errMsg;
    // eslint-disable-next-line eqeqeq
    if (errMsg) {
      // 发送请求失败,将信息返回出去
      ElMessage.error(errMsg);
      return Promise.reject(res.data);
    }
    return res;
  },
  (error) => {
    // 错误信息处理，例如请求超时等
    ElMessage.error(error.message);
    return Promise.reject(error);
  }
);
