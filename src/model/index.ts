import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import store from "@/store";

const postData = JSON.stringify({
    'msg': 'Hello World!'
});

export enum StateCode {
    error = "400",
    success = "200"
}

export interface IReq {
    method:string
    path:string
    data:string
}

export interface IResp<T = any> {
    code: string
    message: string
    data: T
}

export function service({method, path, data}:IReq):Promise<IResp> {
    return new Promise((resolve) => {
        const request:AxiosRequestConfig = {
            url: path,
            method: method,
            data: data,
            headers:{
                "Content-Type": "application/json",
                "Authorization": store.state.token
            }
        }
        axios.create().request(request)
            .then((response: AxiosResponse<IResp>) => {
                resolve(response.data);
            })
    })
}

