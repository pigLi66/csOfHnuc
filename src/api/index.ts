import axios, { InternalAxiosRequestConfig } from "axios";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import store from "@/store";
import { ElMessage } from "element-plus";

// 配置请求
export const service = axios.create({
  baseURL: process.env.VUE_APP_BACKEND_URL,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json", // 请求默认用json发送
  },
});

// 配置请求拦截器，请求头设置token
service.interceptors.request.use(
  (options: InternalAxiosRequestConfig) => {
    options.withCredentials = true;
    options.headers.Authorization = localStorage.getItem("token");
    return options;
  },
  (error) => Promise.reject(error)
);

// 配置响应拦截
service.interceptors.response.use(
  (res) => {
    const errMsg: string = res.data.errMsg;
    if (errMsg) {
      ElMessage.error(errMsg); // 发送请求失败,将信息返回出去
      return Promise.reject(res.data);
    }
    return res;
  },
  (error) => {
    // 错误信息处理，例如请求超时等
    const errMsg: string = error.response.data.errMsg;
    if (errMsg) {
      ElMessage.error(errMsg); // 返回服务端的错误信息
    } else {
      ElMessage.error(error.message); // 返回HTTP错误信息
    }
    if (error.response.status === 401) {
      localStorage.removeItem("userId");
      localStorage.removeItem("username");
      localStorage.removeItem("email");
      localStorage.removeItem("avatar");
      localStorage.removeItem("token");
      location.reload();
    }
    return Promise.reject(error);
  }
);
