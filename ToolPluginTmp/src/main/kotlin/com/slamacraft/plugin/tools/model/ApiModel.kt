 package com.slamacraft.plugin.redpack.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.slamacraft.plugin.redpack.common.JsonMapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LoginReq : Req<LoginResp>() {
    lateinit var userName: String
    lateinit var password: String

    override fun path(): String = "/app/api/h5Login"
    override fun authorize() = false

    override fun formatInner(rootNode: ObjectNode) {
        rootNode.put("userName", userName)
            .put("password", password)
    }
}

class LoginResp : Resp() {
    var childToken: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var expirationTime: LocalDateTime? = null
    var userId: Int = 0

    override fun parse(rootNode: JsonNode) {
        childToken = rootNode.path("childToken").asText()
        expirationTime = LocalDateTime.parse(
            rootNode.path("expirationTime").asText(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        )
        userId = rootNode.path("userId").asInt()
    }
}

class GetUserInfoReq : Req<GetUserInfoResp>() {
    override fun path() = "/h5/api/hsb/getUserInfo"
    override fun method() = "GET"

    override fun formatInner(rootNode: ObjectNode) {
        addQuery("appUserId", userId.toString())
    }
}

class GetUserInfoResp : Resp() {
    var userId: Int = 0
    var orgId: Int = 0
    var flag: Int = 1
    var hsbGroupId: Int = 0

    override fun parse(rootNode: JsonNode) {
        userId = rootNode.path("appUserId").asInt()
        orgId = rootNode.path("orgId").asInt()
        flag = rootNode.path("flag").asInt()
        hsbGroupId = rootNode.path("hsbGroupId").asInt()
    }
}

class ListWorkReq : Req<List<ListWorkResp>>() {
    var orgId: Int = 0
    override fun path() = "/h5/api/hsb/getSemesterContentList"
    override fun method() = "GET"

    override fun formatInner(rootNode: ObjectNode) {
        addQuery("orgId", orgId.toString())
    }
}

class ListWorkResp : Resp() {
    var contentId: Int = 0

    override fun parse(rootNode: JsonNode) {
        contentId = rootNode.path("contentId").asInt()
    }
}

class PageTaskReq : Req<List<PageTaskResp>>() {
    var activeUserId: Int = 0
    var isRank: Int = 0
    var pageNum: Int = 1
    var pageSize: Int = 5
    var hsbContentId: Int = 0
    override fun path() = "/h5/api/hsb/getTaskPageList"
    override fun method() = "GET"

    override fun formatInner(rootNode: ObjectNode) {
        addQuery("activeUserId", activeUserId.toString())
        addQuery("isRank", isRank.toString())
        addQuery("pageNum", pageNum.toString())
        addQuery("pageSize", pageSize.toString())
        addQuery("hsbContentId", hsbContentId.toString())
    }
}

class PageTaskResp : Resp() {
    var id: Int = 0
    lateinit var title: String
    lateinit var content: String

    override fun parse(rootNode: JsonNode) {
        id = rootNode.path("id").asInt()

        JsonMapper.objectMapper.readTree(rootNode.path("content").asText())
            .forEach {
                val nodeTitle = it.path("title").asText()
                val nodeContent = it.path("content").asText()
                if (nodeTitle == "学习内容") {
                    title = nodeContent
                } else if (nodeTitle == "学习感悟") {
                    content = nodeContent
                }
            }
    }
}


class SubmitTaskReq : Req<SubmitTaskResp>() {
    var hsbGroupId: Int = 0
    var hsbContentId: Int = 0

    lateinit var title: String
    lateinit var content: String
    override fun path() = "/h5/api/hsb/submitTask"

    private class SubmitContent(var title: String, var content: String)

    override fun formatInner(rootNode: ObjectNode) {
        val content = listOf(
            SubmitContent("学习内容", title),
            SubmitContent("学习要点", title),
            SubmitContent("学习感悟", content)
        )

        val contentStr = JsonMapper.objectMapper.writeValueAsString(content)

        rootNode.put("appUserId", userId)
            .put("hsbGroupId", hsbGroupId)
            .put("hsbContentId", hsbContentId)
            .put("content", contentStr)
    }
}

class SubmitTaskResp : Resp() {
}
