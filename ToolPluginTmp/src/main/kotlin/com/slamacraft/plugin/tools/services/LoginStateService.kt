package com.slamacraft.plugin.redpack.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.Converter
import com.intellij.util.xmlb.annotations.OptionTag
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Storage("loginState.xml")
class LoginStateService : PersistentStateComponent<State> {
    private var state: State = State()

    override fun getState(): State? {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

}

class State {
    lateinit var userName: String
    lateinit var password: String
    lateinit var childToken: String

    @OptionTag(converter = LocalDateTimeConverter::class)
    var expirationTime: LocalDateTime? = null
}

class LocalDateTimeConverter : Converter<LocalDateTime>() {
    override fun toString(value: LocalDateTime): String? {
        return value.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    override fun fromString(value: String): LocalDateTime? {
        return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(value))
    }

}