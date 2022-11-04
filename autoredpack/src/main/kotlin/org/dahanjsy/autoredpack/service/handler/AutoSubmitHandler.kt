package org.dahanjsy.autoredpack.service.handler

import com.hankcs.hanlp.HanLP
import org.dahanjsy.autoredpack.common.SummaryExtractor
import org.dahanjsy.autoredpack.service.model.GetUserInfoReq
import org.dahanjsy.autoredpack.service.model.ListWorkReq
import org.dahanjsy.autoredpack.service.model.PageTaskReq
import org.dahanjsy.autoredpack.service.model.SubmitTaskReq

object AutoSubmitHandler {

    fun invoke() {
        // 获取用户信息
        val userInfoResp = GetUserInfoReq().invokeService()

        // 获取红书包列表
        val listWorkReq = ListWorkReq()
        listWorkReq.orgId = userInfoResp.orgId
        val listWorkResp = listWorkReq.invokeService()

        val pageTaskReq = PageTaskReq()
        pageTaskReq.pageNum = 1
        pageTaskReq.pageSize = 50
        pageTaskReq.activeUserId = userInfoResp.userId
        pageTaskReq.hsbContentId = listWorkResp[0].contentId
        val pageTaskResp = pageTaskReq.invokeService()

        val text = pageTaskResp.joinToString("。") { it.content }

        val summary = HanLP.extractSummary(text, 30)
        val result = summary.distinct().joinToString(",") { it }

        val title = SummaryExtractor.filterTitle(result, pageTaskResp.map { it.title })

        val submitTaskReq = SubmitTaskReq()
        submitTaskReq.title = title
        submitTaskReq.content = result
        submitTaskReq.hsbGroupId = userInfoResp.hsbGroupId
        submitTaskReq.hsbContentId = pageTaskReq.hsbContentId

        val resp = submitTaskReq.invokeService()
        println("成功")
    }

}
