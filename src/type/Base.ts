export interface BasePageReq {
    pageNum: number
    pageSize: number
}

export interface BasePageResp<T> {
    pageNum: number
    pageSize: number
    list: T[]
}

