import {service} from "@/api/index";
import {LoginForm} from "@/type/Login";

export function login(data: LoginForm) {
    return service({
        url: "/login",
        method: "POST",
        data: data
    })
}