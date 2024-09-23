package components.car

import androidx.compose.runtime.*
import api.cars.CarResponse
import com.arkivanov.decompose.ComponentContext
import api.cars.getCars
import kotlinx.coroutines.*

class GetCarsComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    
    
    private var _data: MutableState<List<CarResponse>> = mutableStateOf(
        mutableListOf()
    );
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")

    val showPopup = _showPopup
    val textPopup = _textPopup

    var ascending = mutableStateOf(true)
    val sortedCars = _data
    var selectedAttribute = mutableStateOf("car_id") 

    init {
        request2Data()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Data(){
        GlobalScope.launch {
            try {
                _data.value = getCars()
                sort()
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }

    fun sort(){
        sortedCars.value = when (selectedAttribute.value) {
            "car_id" -> if (ascending.value) _data.value.sortedBy { it.car_id } else _data.value.sortedByDescending { it.car_id }
            "make" -> if (ascending.value) _data.value.sortedBy { it.make } else _data.value.sortedByDescending { it.make }
            "model" -> if (ascending.value) _data.value.sortedBy { it.model } else _data.value.sortedByDescending { it.model }
            "year" -> if (ascending.value) _data.value.sortedBy { it.year } else _data.value.sortedByDescending { it.year }
            "color_hex" -> if (ascending.value) _data.value.sortedBy { it.color_hex } else _data.value.sortedByDescending { it.color_hex }
            "price_per_day" -> if (ascending.value) _data.value.sortedBy { it.price_per_day } else _data.value.sortedByDescending { it.price_per_day }
            "status" -> if (ascending.value) _data.value.sortedBy { it.status } else _data.value.sortedByDescending { it.status }
            "create_at" -> if (ascending.value) _data.value.sortedBy { it.create_at } else _data.value.sortedByDescending { it.create_at }
            else -> _data.value
        }
    }
}