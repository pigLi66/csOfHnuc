import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {ElMessage} from "element-plus";
import store from "@/store";

// 配置请求
export const service = axios.create({
    baseURL: "http://127.0.0.1:4523/m1/2240180-0-default",
    timeout: 5000,
    headers:{
        "Content-Type":"application/json;charset=utf-8"
    }
})

// 配置请求拦截器，请求头设置token
axios.interceptors.request.use((options: AxiosRequestConfig) => {
    const token = store.state.token;
    if (token && options.headers) {
        options.headers['Authorization'] = token
    }
    return options
})

// 配置响应拦截
axios.interceptors.response.use((res)=>{
    const code:number = res.data.code;
    if(code != 200){
        // 发送请求失败,将信息返回出去
        return Promise.reject(res.data)
    }
    return res.data
}, (error)=>{   // 错误信息处理，例如请求超时等
    ElMessage.error(error.message)
})


export interface IResp<T = any> {
    code: string
    message: string
    data: T
}

// export function service({method, path, data}: IReq): Promise<IResp> {
//     return new Promise((resolve) => {
//         const request: AxiosRequestConfig = {
//             url: path,
//             method: method,
//             data: JSON.stringify(data)
//         }
//         axios.create().request(request)
//             .then((response: AxiosResponse<IResp>) => {
//                 resolve(response.data);
//             }).catch((reason) => {ElMessage.error(reason.message)})
//     })
// }

