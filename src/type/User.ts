import {BasePageReq, BasePageResp} from "@/type/Base";

export class PageUserReq implements BasePageReq {
    pageNum = 1;
    pageSize = 10;
    username?:string
    roleId?: number

}

export class PageUserItem {

}

export class PageUserResp implements BasePageResp<PageUserItem> {
    list: PageUserItem[] = [];
    pageNum = 1;
    pageSize = 10;
    total = 0;
    totalPage = 1;

}

export class RoleItem{
    id = 0
    roleName = ""
}

export class ListRoleResp {
    list:RoleItem[] = []
}