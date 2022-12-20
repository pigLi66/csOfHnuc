package com.slamacraft.plugin.redpack.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.slamacraft.plugin.redpack.common.JsonMapper
import com.slamacraft.plugin.redpack.context.UserContext
import com.slamacraft.plugin.redpack.model.PageTaskReq
import com.slamacraft.plugin.redpack.model.Req
import com.slamacraft.plugin.redpack.model.Resp
import org.apache.commons.io.IOUtils
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object ApiModelTransfer {
    private const val ERR_CODE_NODE_KEY = "code"
    private const val ERR_MSG_NODE_KEY = "message"
    private const val ACCESS_TOKEN_HEADER_KEY = "childToken"
    private var objectMapper: ObjectMapper = JsonMapper.objectMapper

    @Suppress("UNCHECKED_CAST")
    fun <R> invokeService(
        req: Req<R>,
        errorHandle: (String) -> R?
    ): R? {
        // 1、组织接口数据
        val outBodyStr = req.format()
        val requestUrl = req.buildRequestUrl()

        // 2、执行接口调用
        val inBodyStr = execute(requestUrl, req.method(), outBodyStr) {
            if (req.authorize()) {  // 如果需要token
                it.setRequestProperty(ACCESS_TOKEN_HEADER_KEY, UserContext.tokenResp?.childToken!!)
            }
        }

        // 3、解析响应数据
        val rootNode = JsonMapper.objectMapper.readTree(inBodyStr)
        if (rootNode.path(ERR_CODE_NODE_KEY).asInt(0) != 0) {
            return errorHandle(rootNode.path(ERR_MSG_NODE_KEY).asText())
        }

//        (types[0] as ParameterizedType).rawType
//        var result = (type as Class<*>)

        return when (val type = (req.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]) {
            is Class<*> -> {
                val result = type.getDeclaredConstructor().newInstance() as R
                if (result is Resp) {
                    result.parse(rootNode.path("data"))
                }
                return result
            }

            is ParameterizedType -> {
                val rawType = type.rawType as Class<*>
                if (List::class.java.isAssignableFrom(rawType)) {
                    val respClass = (type.actualTypeArguments[0] as WildcardType)
                        .upperBounds[0] as Class<*>

                    return rootNode.path("data").map {
                        val resp = respClass.getDeclaredConstructor().newInstance() as Resp
                        resp.parse(it)
                        resp
                    } as R
                } else {
                    val respClass = (type.actualTypeArguments[0] as WildcardType)
                        .upperBounds[0] as Class<*>

                    return rootNode.path("data").map {
                        val resp = respClass.getDeclaredConstructor().newInstance() as Resp
                        resp.parse(it)
                        resp
                    }.toSet() as R
                }
            }

            else -> errorHandle("解析请求参数泛型失败")
        }
    }


    private fun execute(
        requestUrl: String,
        requestMethod: String,
        outputStr: String,
        modifyFun: (HttpURLConnection) -> Unit = {}
    ): String {
        val connection = openConnection(requestUrl, requestMethod)
        modifyFun.invoke(connection)

        try {
            // 1、写出请求数据
            // 当请求为GET时禁止写入输出流，因为HttpURLConnection会把GET请求转换为POST请求
            if (requestMethod != "GET" && outputStr.isNotBlank() && outputStr != "{}") {
                IOUtils.write(outputStr, connection.outputStream, "UTF-8")
            }

            // 2、读入响应数据
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                return IOUtils.toString(connection.inputStream, "UTF-8")
            }
            val errorJsonStr = IOUtils.toString(connection.errorStream, "UTF-8")
            val errorJson = objectMapper.readTree(errorJsonStr)
            val errorMsg = errorJson.path("message").asText()

            throw RuntimeException(errorMsg)
        } finally {
            closeConnection(connection)
        }
    }

    private fun openConnection(requestUrl: String, requestMethod: String): HttpURLConnection {
        val url = URL(requestUrl)
        val connection = url.openConnection() as HttpURLConnection
        if (url.protocol == "https") {
            (connection as HttpsURLConnection).sslSocketFactory = sSLSocketFactory
        }
        connection.connectTimeout = 3000
        connection.readTimeout = 10000
        connection.doOutput = true // 使用 URL 连接进行输出
        connection.doInput = true // 使用 URL 连接进行输入
        connection.useCaches = false // 不使用缓存
        connection.requestMethod = requestMethod // 设置请求方式（GET/POST）
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8")

        return connection
    }

    private fun closeConnection(connection: HttpURLConnection?) {
        connection?.disconnect()
    }

    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
    private val sSLSocketFactory: SSLSocketFactory
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        get() {
            val tms = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(cxs: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(xcs: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate>? = null
            })
            val sr = SecureRandom()
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            val sslContext = SSLContext.getInstance("SSL", "SunJSSE")
            sslContext.init(null, tms, sr)
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            return sslContext.socketFactory
        }
}

fun main() {
    val javaClass = PageTaskReq::class.java

    val req = PageTaskReq()
    req.activeUserId = 29082
    req.hsbContentId = 71
    val invokeService = req.invokeService()

    println(invokeService)
}
