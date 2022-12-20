package com.slamacraft.plugin.tools.window

import com.hankcs.hanlp.HanLP
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.slamacraft.plugin.tools.common.SummaryExtractor
import com.slamacraft.plugin.tools.context.UserContext
import com.slamacraft.plugin.tools.model.*
import java.time.LocalDateTime
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.swing.RepaintManager

class RedPackToolWindow constructor(private val project: Project) {
    private lateinit var loginPanel: Panel
    private lateinit var userNameField: Cell<JBTextField>
    private lateinit var passwordField: Cell<JBPasswordField>

    private lateinit var toolPanel: Panel
    private lateinit var titleField: Cell<JBTextField>
    private lateinit var contentArea: Cell<JBTextArea>

    private var isLogin = false

    companion object {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
    }

    init {
        isLogin = try {
            UserContext.login()
            true
        } catch (e: Exception) {
            false
        }
    }

    val toolWindow = panel {

        loginPanel = panel {
            row("用户") {
                userNameField = textField()
                    .text(UserContext.phone)
                    .horizontalAlign(HorizontalAlign.FILL)
            }.layout(RowLayout.LABEL_ALIGNED)

            row("密码") {
                passwordField = cell(JBPasswordField())
                    .text(UserContext.pw)
                    .resizableColumn()
                    .horizontalAlign(HorizontalAlign.FILL)
                button("登录") {
                    UserContext.storageInfo(userNameField.component.text, passwordField.component.text).login()
                    loginPanel.visible(false)
                    toolPanel.visible(true)
                }
            }.layout(RowLayout.LABEL_ALIGNED)
        }.visible(!isLogin)

        toolPanel = panel {
            row {
                titleField = textField().text("标题")
                    .resizableColumn()
                    .horizontalAlign(HorizontalAlign.FILL)

                button("生成") {
                    info("正在生成，请稍等")
                    executor.submit { generateContent() }
                }
                button("提交") { submitContent() }
            }.layout(RowLayout.LABEL_ALIGNED)

            row {
                contentArea = textArea()
                    .resizableColumn()
                    .text("内容")
                    .horizontalAlign(HorizontalAlign.FILL)
                    .verticalAlign(VerticalAlign.FILL)
                    .apply {
                        component.lineWrap = true
                    }
            }.layout(RowLayout.INDEPENDENT)
                .resizableRow()
        }.visible(isLogin)
            .horizontalAlign(HorizontalAlign.FILL)
            .verticalAlign(VerticalAlign.FILL)
    }

    private fun tryLogin() {
        if (UserContext.tokenResp == null || !UserContext.tokenResp?.expirationTime!!.isAfter(LocalDateTime.now())) {
            UserContext.login()
        }
    }

    private fun <R> Req<R>.trySendRequest(): R? {
        return invokeService { message: String ->
            NotificationGroupManager.getInstance()
                .getNotificationGroup("RedPack Group")
                .createNotification(message, NotificationType.ERROR)
                .notify(project)
            null
        }
    }

    private fun generateContent() {
        tryLogin()
        // 获取用户信息
        GetUserInfoReq().trySendRequest()?.also { userInfo ->
            // 获取红书包列表
            val listWorkReq = ListWorkReq()
            listWorkReq.orgId = userInfo.orgId
            listWorkReq.trySendRequest()?.also { workList ->
                val pageTaskReq = PageTaskReq()
                pageTaskReq.pageNum = 1
                pageTaskReq.pageSize = 50
                pageTaskReq.activeUserId = userInfo.userId
                pageTaskReq.hsbContentId = workList[0].contentId

                UserContext.hsbGroupId = userInfo.hsbGroupId
                UserContext.hsbContentId = workList[0].contentId

                pageTaskReq.trySendRequest()?.also { taskList ->
                    val text = taskList.joinToString("。") { it.content }

                    val summary = HanLP.extractSummary(text, 30)
                    val result = summary.distinct().joinToString(",") { it }
                    val title = SummaryExtractor.filterTitle(result, taskList.map { it.title })

                    synchronized(RepaintManager.currentManager(titleField.component)) {
                        titleField.text(title)
                    }
                    synchronized(RepaintManager.currentManager(contentArea.component)) {
                        contentArea.text(result)
                    }
                }
            }
        }
    }


    private fun info(message: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("RedPack Group")
            .createNotification(message, NotificationType.INFORMATION)
            .notify(project)
    }

    private fun UserContext.login() {
        val req = LoginReq()
        req.userName = phone
        req.password = pw
        this.tokenResp = req.trySendRequest()
        this.userId = tokenResp!!.userId
    }

    private fun submitContent() {
        tryLogin()
        val submitTaskReq = SubmitTaskReq()
        submitTaskReq.title = titleField.component.text
        submitTaskReq.content = contentArea.component.text
        submitTaskReq.hsbGroupId = UserContext.hsbGroupId
        submitTaskReq.hsbContentId = UserContext.hsbContentId
        submitTaskReq.trySendRequest()?.let {
            info("红书包提交成功")
        }
    }
}