package org.dahanjsy.autoredpack.views

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.dahanjsy.autoredpack.HelloApplication
import org.dahanjsy.autoredpack.service.Config
import org.dahanjsy.autoredpack.service.UserContext
import org.dahanjsy.autoredpack.service.LoginHelper
import java.net.URL
import java.util.*

class LoginController :Initializable{

    @FXML
    lateinit var loginButton: Button

    @FXML
    lateinit var errorMsg: Label

    @FXML
    lateinit var remember: CheckBox
    @FXML
    lateinit var pwd: PasswordField
    @FXML
    lateinit var phone: TextField

    @FXML
    lateinit var root: AnchorPane

    @FXML
    lateinit var topPane: Pane

    @FXML
    fun close(actionEvent: ActionEvent){
        val stage = root.scene.window as Stage
        stage.close()
    }

    @FXML
    fun login(actionEvent: ActionEvent){
        saveLoginConfig()
        loginButton.text = "登录中..."
        try {
            val tokenResp = LoginHelper.getTokenResp(phone.text, pwd.text)
            if(tokenResp.childToken == null){
                // 提示错误
                errorMsg.text = "用户名或密码错误"
            }

            UserContext.phone = phone.text
            UserContext.pw = pwd.text
            UserContext.userId = tokenResp.userId
            UserContext.childToken = tokenResp.childToken
            UserContext.expirationTime = tokenResp.expirationTime

            // 关闭此窗口，打开另外一个窗口
            val stage = root.scene.window as Stage
            stage.close()
            val toolsController = ToolsController()
            toolsController.start()
        }catch (e:Exception){
            errorMsg.text = e.message
        } finally {
            loginButton.text = "登录"
            pwd.text = ""
        }
    }

    private fun saveLoginConfig(){
        Config.property.phone = phone.text
        Config.property.remember = remember.isSelected
        if(remember.isSelected){
            Config.property.pwd = pwd.text
        }
        Config.writeConfig()
    }

    @FXML
    fun forget(actionEvent: ActionEvent){
        HelloApplication.app.hostServices.showDocument("http://m.dhgjcloud.com/hsb/forgetPassword.html")
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        phone.text = Config.property.phone
        pwd.text = Config.property.pwd
        remember.isSelected = Config.property.remember
    }

}