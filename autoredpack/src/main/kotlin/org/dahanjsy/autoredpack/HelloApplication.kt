package org.dahanjsy.autoredpack

import org.dahanjsy.autoredpack.common.utils.PackageScanner
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.dahanjsy.autoredpack.service.Config
import org.dahanjsy.autoredpack.views.LoginController
import org.kordamp.bootstrapfx.BootstrapFX

class HelloApplication : Application() {

    init {
        app = this
    }

    override fun start(stage: Stage) {
        Config.init()  // 初始化bean
        val fxmlLoader = FXMLLoader(LoginController::class.java.getResource("login.fxml"))
        val root:Parent = fxmlLoader.load()
        root.setOnMousePressed {
            DragPosition.x = it.sceneX
            DragPosition.y = it.sceneY
        }
        root.setOnMouseDragged {
            stage.x = it.screenX - DragPosition.x
            stage.y = it.screenY - DragPosition.y
        }
        val scene = Scene(root)
        scene.stylesheets.add(BootstrapFX.bootstrapFXStylesheet())
        scene.stylesheets.add(LoginController::class.java.getResource("login.css")!!.toExternalForm())

        stage.title = "自动红书包"
        stage.scene = scene
        stage.initStyle(StageStyle.UNDECORATED)
        stage.isResizable = false
        stage.show()
    }

    companion object{
        lateinit var app:HelloApplication
    }
}

object DragPosition{
    var x:Double = 0.0
    var y:Double = 0.0
}

fun main() {
    println("启动")
//    PackageScanner.doScan(HelloApplication::class.java) // 像spring一样先扫描一遍
    Application.launch(HelloApplication::class.java)
}