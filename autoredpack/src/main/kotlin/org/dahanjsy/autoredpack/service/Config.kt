package org.dahanjsy.autoredpack.service

import org.dahanjsy.autoredpack.common.JsonMapper
import java.io.File

object Config {
    // config path
    private const val configBasePath = "C:\\Users\\Administrator\\AppData\\Roaming\\dahanjsy"
    private const val redPackPath = "\\autoRedPack"
    private const val configName = "\\config.json"

    private lateinit var configFile: File

    // config properties
    lateinit var property: Property

    fun init() {
        File(configBasePath).mkdir()
        File("$configBasePath$redPackPath").mkdir()
        configFile = File("$configBasePath$redPackPath$configName")
        configFile.createNewFile()
        if(configFile.readLines().isEmpty()){
            configFile.writeText("{}")
        }
        property = JsonMapper.objectMapper.readValue(configFile, Property::class.java)
    }


    fun writeConfig() {
        val jsonStr = JsonMapper.objectMapper.writeValueAsString(property)
        configFile.writeText(jsonStr)
    }

}

class Property {
    var remember = false
    var phone: String = ""
    var pwd: String = ""
    var articleNum:Int = 30
    var summaryNumBar:Int = 50
}

fun main() {

}