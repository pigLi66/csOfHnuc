import {service} from "@/request/index";
import {PageGoodsReq, PageGoodsResp} from "@/type/Goods";

export function goodsPage(req: PageGoodsReq){
    return service({
        url:"/goods/page",
        method:"GET",
        params: req
    }).then(res=>{
        const resp = new PageGoodsResp();
        resp.pageNum = res.data.pageNum
        resp.pageSize = res.data.pageSize
        resp.total = res.data.total
        resp.totalPage = res.data.totalPage
        resp.list = res.data.list
        return resp
    })
}