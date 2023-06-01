import axios, { InternalAxiosRequestConfig } from "axios";
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import store from "@/store";
import { ElMessage } from "element-plus";

// 配置请求
export const service = axios.create({
  baseURL: process.env.VUE_APP_BACKEND_URL,
  timeout: 5000,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

// 配置请求拦截器，请求头设置token
service.interceptors.request.use(
  (options: InternalAxiosRequestConfig) => {
    // const token = store.state.user.token;
    // if (token && options.headers) {
    //     options.headers['Authorization'] = token
    // }
    options.withCredentials = true;
    return options;
  },
  (error) => Promise.reject(error)
);

// 配置响应拦截
service.interceptors.response.use(
  (res) => {
    const code: number = res.data.code;
    // eslint-disable-next-line eqeqeq
    if (code && code != 200) {
      // 发送请求失败,将信息返回出去
      ElMessage.error(res.data.message);
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
