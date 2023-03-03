import {service} from "@/api/index";
import {PageUserReq} from "@/type/User";

export function pageUser(req:PageUserReq){
    return service({
        url:"/user/page",
        method:"GET",
        params: req
    })
}

export function listRole(){
    return service({
        url:"/role/list",
        method:"GET"
    })
}