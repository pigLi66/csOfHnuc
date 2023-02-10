import {BasePageReq, BasePageResp} from "@/type/Base";

export class PageGoodsReq implements BasePageReq{
    pageNum = 1
    pageSize = 5
    keyword = ""
}

class PageGoodsItem{
    id = 0
    title = ""
    desc = ""
    amount = 0
}

export class PageGoodsResp implements BasePageResp<PageGoodsItem>{
    list: PageGoodsItem[] = [];
    pageNum = 1;
    pageSize = 10;
    total = 0;
    totalPage = 0;
}