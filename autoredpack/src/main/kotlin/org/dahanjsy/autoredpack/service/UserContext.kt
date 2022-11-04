package org.dahanjsy.autoredpack.service

import java.time.LocalDateTime

object UserContext {
    // 登录表单信息
    lateinit var phone: String
    lateinit var pw: String

    // 登录成功后会有的信息
    var userId: Int = 0
    var childToken: String? = null
    var expirationTime: LocalDateTime? = null

    // 获取用户信息获得
    var orgId: Int = 0
    var flag: Int = 1
    var hsbGroupId: Int = 0

    // 查询当期红书包获得
    var hsbContentId: Int = 0

}
