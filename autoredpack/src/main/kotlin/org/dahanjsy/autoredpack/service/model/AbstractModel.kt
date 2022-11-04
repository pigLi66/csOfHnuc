package org.dahanjsy.autoredpack.service.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.dahanjsy.autoredpack.common.JsonMapper
import org.dahanjsy.autoredpack.service.UserContext
import org.dahanjsy.autoredpack.service.handler.ApiModelTransfer


abstract class Req<R> {
    protected open val userId: Int
        get() = UserContext.userId

    @JsonIgnore
    private val queryMap: MutableMap<String, String> = HashMap()

    // http请求相关
    fun invokeService(): R = ApiModelTransfer.invokeService(this)

    protected abstract fun path(): String

    open fun method(): String {
        return "POST"
    }

    open fun authorize(): Boolean {
        return true
    }

    protected open fun formatInner(rootNode: ObjectNode) {}

    protected open fun host(): String {
        return "http://m.dhgjcloud.com"
    }

    protected fun addQuery(key: String, value: String) {
        if (key.isNotBlank() && value.isNotBlank()) {
            queryMap[key] = value
        }
    }

    fun format(): String {
        val objectMapper = JsonMapper.objectMapper
        val rootNode = objectMapper.createObjectNode()
        formatInner(rootNode)
        return objectMapper.writeValueAsString(rootNode)
    }

    fun buildRequestUrl(): String {
        val queryStr: String = this.queryMap.map { "${it.key}=${it.value}" }
            .joinToString("&", "?", "")

        return "${host()}${path()}${queryStr}"
    }
}

abstract class Resp {
    open fun parse(rootNode: JsonNode) {}
}
