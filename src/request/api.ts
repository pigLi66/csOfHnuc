import {service} from "@/request/index";
import {LoginData} from "@/type/Login";

export function login(data:LoginData){
    return service({
        url:"/login",
        method:"POST",
        data:{

        }
    })
}