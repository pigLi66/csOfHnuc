package com.slamacraft.plugin.redpack.context

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe
import com.slamacraft.plugin.redpack.model.LoginReq
import com.slamacraft.plugin.redpack.model.LoginResp
import java.time.LocalDateTime

object UserContext {

    var tokenResp: LoginResp? = null
    var userId: Int = 0

    private val credentialAttributes = createCredentialAttributes()
    var phone: String = PasswordSafe.instance[credentialAttributes]?.userName ?: ""
    var pw: String = PasswordSafe.instance[credentialAttributes]?.getPasswordAsString() ?: ""
    var hsbGroupId = 0
    var hsbContentId = 0

    fun storageInfo(phone: String, pw: String): UserContext {
        this.phone = phone
        this.pw = pw
        PasswordSafe.instance[credentialAttributes] = Credentials(phone, pw)
        return this
    }


    private fun createCredentialAttributes(key: String = "UserPw"): CredentialAttributes {
        return CredentialAttributes(generateServiceName("RedPackService", key))
    }
}
