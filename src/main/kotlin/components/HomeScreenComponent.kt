package components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import java.awt.Component

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onGoChat: (String, Int) -> Unit
): ComponentContext by componentContext {

    private val _ip: MutableState<String> = mutableStateOf("")
    private val _port: MutableState<String> = mutableStateOf("")

    val ip = _ip
    val port = _port

    fun goChat(){
        onGoChat(_ip.value, _port.value.toInt())
    }

}