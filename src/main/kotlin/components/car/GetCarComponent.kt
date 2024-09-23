package components.car

import androidx.compose.runtime.*
import api.cars.deleteCar
import api.cars.getCar
import api.cars.putCar
import api.cars.CarResponse
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.*

class GetCarComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _car: MutableState<CarResponse?> = mutableStateOf(null);
    private val _carBuf: MutableState<CarResponse?> = mutableStateOf(null);
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _id: MutableState<Long?> = mutableStateOf(null)

    private val _isEdit: MutableState<Boolean> = mutableStateOf(false)
    private val _isDelete: MutableState<Boolean> = mutableStateOf(false)

    val car = _car
    val carBuf = _carBuf
    val id = _id
    val showPopup = _showPopup
    val textPopup = _textPopup
    val isEdit = _isEdit
    val isDelete = _isDelete

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        if (_id.value == null) {
            _textPopup.value = "ID может быть только типа LONG"
            _showPopup.value = true
            return
        }
        GlobalScope.launch {
            try {
                _car.value = getCar(_id.value!!)
                _carBuf.value = _car.value
            }
            catch (e: Exception) {
                _textPopup.value = e.message.toString()
                _showPopup.value = true
                print("Error getting car")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Put(){
        if (_car.value == null) return
        GlobalScope.launch {
            try {
                _car.value = putCar(_carBuf.value!!)
                _textPopup.value = "Автомобиль успешно обновлен"
                _showPopup.value = true
            }
            catch (e: Exception){
                _carBuf.value = _car.value
                println(e.message)
                _textPopup.value = "Не удалось обновить, ошибка: ${e.message}"
                _showPopup.value = true

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Delete(){
        GlobalScope.launch {
            try {
                _car.value = deleteCar(_id.value!!)
                _showPopup.value = true
                _textPopup.value = "Автомобиль успешно удален"
                _isDelete.value = true
                _isEdit.value = false
            }
            catch (e: Exception) {
                _car.value = null
                _showPopup.value = true
                _textPopup.value = e.message.toString()
                print("Error deleting car")
            }
        }
    }


}