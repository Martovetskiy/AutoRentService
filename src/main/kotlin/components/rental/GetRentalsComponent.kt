package components.rental

import androidx.compose.runtime.*
import api.rentals.RentalResponse
import com.arkivanov.decompose.ComponentContext
import api.rentals.getRentals
import kotlinx.coroutines.*

class GetRentalsComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    
    
    private var _data: MutableState<List<RentalResponse>> = mutableStateOf(
        mutableListOf()
    );
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")

    val showPopup = _showPopup
    val textPopup = _textPopup

    var ascending = mutableStateOf(true)
    val sortedRentals = _data
    var selectedAttribute = mutableStateOf("rental_id") 

    init {
        request2Data()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Data(){
        GlobalScope.launch {
            try {
                _data.value = getRentals()
                sort()
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }

    fun sort(){
        sortedRentals.value = when (selectedAttribute.value) {
            "rental_id" -> if (ascending.value) _data.value.sortedBy { it.rental_id } else _data.value.sortedByDescending { it.rental_id }
            "customer_id" -> if (ascending.value) _data.value.sortedBy { it.customer_id } else _data.value.sortedByDescending { it.customer_id }
            "car_id" -> if (ascending.value) _data.value.sortedBy { it.car_id } else _data.value.sortedByDescending { it.car_id }
            "start_date" -> if (ascending.value) _data.value.sortedBy { it.start_date } else _data.value.sortedByDescending { it.start_date }
            "end_date" -> if (ascending.value) _data.value.sortedBy { it.end_date } else _data.value.sortedByDescending { it.end_date }
            "total_price" -> if (ascending.value) _data.value.sortedBy { it.total_price } else _data.value.sortedByDescending { it.total_price }
            "create_at" -> if (ascending.value) _data.value.sortedBy { it.create_at } else _data.value.sortedByDescending { it.create_at }
            else -> _data.value
        }
    }
}