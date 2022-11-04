package org.dahanjsy.autoredpack.service

import org.dahanjsy.autoredpack.service.model.LoginReq
import org.dahanjsy.autoredpack.service.model.LoginResp

object LoginHelper {

    private var token: LoginResp? = null

    fun getTokenResp(phone: String, pwd: String): LoginResp {
        val req = LoginReq()
        req.userName = phone
        req.password = pwd
        return req.invokeService()
    }

}
