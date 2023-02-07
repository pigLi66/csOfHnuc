import {service} from "@/request/index";

export function goodsList(){
    return service({
        url:"/goods/list",
        method:"GET",
        data:{

        }
    })
}