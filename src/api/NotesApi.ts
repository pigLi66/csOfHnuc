import {service} from "@/api/index";
import {NotifyReq, SaveReq} from "@/type/Notes";

export function get(data: { userId: number}) {
    return service({
        url: "/note",
        method: "GET",
        params: data
    })
}

export function save(data: SaveReq) {
    return service({
        url: "/note",
        method: "POST",
        data: JSON.stringify(data)
    })
}

export function notify(data: NotifyReq) {
    return service({
        url: "/note/notify",
        method: "UPDATE",
        data: JSON.stringify(data)
    })
}

export function revert(data: { userId: number}) {
    return service({
        url: "/note/revert",
        method: "UPDATE",
        data: JSON.stringify(data)
    })
}