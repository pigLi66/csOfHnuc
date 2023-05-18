import {service} from "@/api/index";

type GetWidgetReq = {
    userId: number | undefined
    widgetSuiteId?: number
}

export function get(data: GetWidgetReq) {
    return service({
        url: "/widget",
        method: "GET",
        params: data
    })
}

export function getSuite(data: GetWidgetReq) {
    return service({
        url: "/widget/suite",
        method: "GET",
        params: data
    })
}