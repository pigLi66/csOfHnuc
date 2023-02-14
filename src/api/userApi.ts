import {service} from "@/api/index";

export function listUser(){
    return service({
        url:"/role/path",
        method:"GET",
    })
}