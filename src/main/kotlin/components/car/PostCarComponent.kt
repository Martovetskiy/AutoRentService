package components.car

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import api.cars.CarRequest
import api.cars.postCar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostCarComponent  (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _car: MutableState<CarRequest?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")


    val showPopup = _showPopup
    val textPopup = _textPopup
    val car = _car

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Post(){
        GlobalScope.launch {
            try {
                postCar(_car.value!!)
                _textPopup.value = "Автомобиль успешно добавлен"
                _showPopup.value = true
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }
}