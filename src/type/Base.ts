export interface BasePageReq {
    pageNum: number
    pageSize: number
}

export interface BasePageResp<T> {
    pageNum: number
    pageSize: number
    total:number
    totalPage:number
    list: T[]
}

