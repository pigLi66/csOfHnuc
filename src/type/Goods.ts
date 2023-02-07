import {BasePageReq, BasePageResp} from "@/type/Base";

export class ListGoodsReq implements BasePageReq{
    pageNum: number = 1
    pageSize: number = 10
    keyword: string = ""
}

class ListGoodsItem{
    id:number = 0
    title:string = ""
    desc:string = ""
    amount:number = 0
}

export class ListGoodsResp implements BasePageResp<ListGoodsItem>{
    list: ListGoodsItem[] = [];
    pageNum: number = 1;
    pageSize: number = 10;
}