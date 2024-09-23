package components.customer

import androidx.compose.runtime.*
import api.cutomers.CustomerResponse
import api.cutomers.getCustomers
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.*

class GetCustomersComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    
    
    private var _data: MutableState<List<CustomerResponse>> = mutableStateOf(
        mutableListOf()
    );
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")

    val showPopup = _showPopup
    val textPopup = _textPopup

    var ascending = mutableStateOf(true)
    val sortedCustomers = _data
    var selectedAttribute = mutableStateOf("customer_id") 

    init {
        request2Data()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Data(){
        GlobalScope.launch {
            try {
                _data.value = getCustomers()
                sort()
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }

    fun sort(){
        sortedCustomers.value = when (selectedAttribute.value) {
            "customer_id" -> if (ascending.value) _data.value.sortedBy { it.customer_id } else _data.value.sortedByDescending { it.customer_id }
            "first_name" -> if (ascending.value) _data.value.sortedBy { it.first_name } else _data.value.sortedByDescending { it.first_name }
            "last_name" -> if (ascending.value) _data.value.sortedBy { it.last_name } else _data.value.sortedByDescending { it.last_name }
            "email" -> if (ascending.value) _data.value.sortedBy { it.email } else _data.value.sortedByDescending { it.email }
            "phone_number" -> if (ascending.value) _data.value.sortedBy { it.phone_number } else _data.value.sortedByDescending { it.phone_number }
            "driver_license" -> if (ascending.value) _data.value.sortedBy { it.driver_license } else _data.value.sortedByDescending { it.driver_license }
            "is_banned" -> if (ascending.value) _data.value.sortedBy { it.is_banned } else _data.value.sortedByDescending { it.is_banned }
            "create_at" -> if (ascending.value) _data.value.sortedBy { it.create_at } else _data.value.sortedByDescending { it.create_at }
            else -> _data.value
        }
    }
}