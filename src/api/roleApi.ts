import {service} from "@/api/index";

export function listRole() {
    return service({
        url: "/role/list",
        method: "GET"
    })
}