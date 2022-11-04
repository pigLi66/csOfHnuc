package org.dahanjsy.autoredpack.views

import com.hankcs.hanlp.HanLP
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import org.dahanjsy.autoredpack.common.SummaryExtractor
import org.dahanjsy.autoredpack.service.*
import org.dahanjsy.autoredpack.service.model.GetUserInfoReq
import org.dahanjsy.autoredpack.service.model.ListWorkReq
import org.dahanjsy.autoredpack.service.model.PageTaskReq
import org.dahanjsy.autoredpack.service.model.SubmitTaskReq
import org.kordamp.bootstrapfx.BootstrapFX
import java.net.URL
import java.util.*

class ToolsController : Initializable {

    @FXML
    lateinit var errorMsg: Label

    @FXML
    lateinit var status: Label

    @FXML
    lateinit var phone: Label

    @FXML
    lateinit var submit: Button

    @FXML
    lateinit var clear: Button

    @FXML
    lateinit var generate: Button

    @FXML
    lateinit var articleContext: TextArea

    @FXML
    lateinit var articleTitle: TextField

    @FXML
    lateinit var articleNumBar: Slider

    @FXML
    lateinit var summaryNumBar: Slider

    @FXML
    lateinit var articleNum: Label

    @FXML
    lateinit var summaryNum: Label

    fun start() {
        val stage = Stage()
        val fxmlLoader = FXMLLoader(LoginController::class.java.getResource("tools.fxml"))
        val root: Parent = fxmlLoader.load()
        val scene = Scene(root)
        scene.stylesheets.add(BootstrapFX.bootstrapFXStylesheet())
        scene.stylesheets.add(LoginController::class.java.getResource("login.css")!!.toExternalForm())

        stage.title = "自动红书包"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // 获取用户信息
        val userInfoResp = GetUserInfoReq().invokeService()
        UserContext.orgId = userInfoResp.orgId
        UserContext.flag = userInfoResp.flag
        UserContext.hsbGroupId = userInfoResp.hsbGroupId

        phone.text = UserContext.phone
        status.text = if (UserContext.flag == 0) {
            "本周红书包未提交"
        } else {
            "本周红书包已提交"
        }
    }

    @FXML
    fun changeArticleNum(mouseDragEvent: MouseEvent) {
        articleNum.text = articleNumBar.value.toInt().toString()
    }

    @FXML
    fun changeSummaryNum(mouseDragEvent: MouseEvent) {
        summaryNum.text = summaryNumBar.value.toInt().toString()
    }

    @FXML
    fun clearArticle(actionEvent: ActionEvent) {
        articleTitle.clear()
        articleContext.clear()
    }

    @FXML
    fun generateArticle(actionEvent: ActionEvent) {
        // 获取红书包列表
        val listWorkReq = ListWorkReq()
        listWorkReq.orgId = UserContext.orgId
        val listWorkResp = listWorkReq.invokeService()
        UserContext.hsbContentId = listWorkResp[0].contentId

        val pageTaskReq = PageTaskReq()
        pageTaskReq.pageNum = 1
        pageTaskReq.pageSize = articleNumBar.value.toInt()
        pageTaskReq.activeUserId = UserContext.userId
        pageTaskReq.hsbContentId = listWorkResp[0].contentId
        val pageTaskResp = pageTaskReq.invokeService()

        val text = pageTaskResp.joinToString("。") { it.content }

        val summary = HanLP.extractSummary(text, summaryNumBar.value.toInt())
        val result = summary.distinct().joinToString(",") { it }

        val title = SummaryExtractor.filterTitle(result, pageTaskResp.map { it.title })

        articleTitle.text = title
        articleContext.text = result
    }

    @FXML
    fun submitArticle(actionEvent: ActionEvent) {
        val submitTaskReq = SubmitTaskReq()
        submitTaskReq.title = articleTitle.text
        submitTaskReq.content = articleContext.text
        submitTaskReq.hsbGroupId = UserContext.hsbGroupId
        submitTaskReq.hsbContentId = UserContext.hsbContentId
        try {
            submitTaskReq.invokeService()
            status.text = "本周红书包已提交"
        } catch (e: Exception) {
            errorMsg.text = e.message
        }
    }

}