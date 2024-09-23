package components.payment

import androidx.compose.runtime.*
import api.payments.PaymentResponse
import com.arkivanov.decompose.ComponentContext
import api.payments.getPayments
import kotlinx.coroutines.*

class GetPaymentsComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    
    
    private var _data: MutableState<List<PaymentResponse>> = mutableStateOf(
        mutableListOf()
    );
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")

    val showPopup = _showPopup
    val textPopup = _textPopup

    var ascending = mutableStateOf(true)
    val sortedPayments = _data
    var selectedAttribute = mutableStateOf("payment_id") 

    init {
        request2Data()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Data(){
        GlobalScope.launch {
            try {
                _data.value = getPayments()
                sort()
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }

    fun sort(){
        sortedPayments.value = when (selectedAttribute.value) {
            "payment_id" -> if (ascending.value) _data.value.sortedBy { it.payment_id } else _data.value.sortedByDescending { it.payment_id }
            "rental_id" -> if (ascending.value) _data.value.sortedBy { it.rental_id } else _data.value.sortedByDescending { it.rental_id }
            "amount" -> if (ascending.value) _data.value.sortedBy { it.amount } else _data.value.sortedByDescending { it.amount }
            "payment_date" -> if (ascending.value) _data.value.sortedBy { it.payment_date } else _data.value.sortedByDescending { it.payment_date }
            "payment_method" -> if (ascending.value) _data.value.sortedBy { it.payment_method } else _data.value.sortedByDescending { it.payment_method }
            "create_at" -> if (ascending.value) _data.value.sortedBy { it.create_at } else _data.value.sortedByDescending { it.create_at }
            else -> _data.value
        }
    }
}