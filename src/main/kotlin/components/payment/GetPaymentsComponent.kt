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
    )
    private val _showFilter: MutableState<Boolean> = mutableStateOf(false)

    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")

    private val _rentalId: MutableState<String?> = mutableStateOf(null)
    private val _amount: MutableState<String?> = mutableStateOf(null)
    private val _step: MutableState<String?> = mutableStateOf(null)
    private val _paymentDate: MutableState<String?> = mutableStateOf(null)
    private val _paymentMethod: MutableState<String?> = mutableStateOf(null)
    private val _sortDirection: MutableState<String> = mutableStateOf("ASC")
    private val _sortBy: MutableState<String> = mutableStateOf("rentalId")
    private val _isLoad: MutableState<Boolean> = mutableStateOf(true)

    val showPopup = _showPopup
    val textPopup = _textPopup

    val rentalId =_rentalId
    val amount = _amount
    val step = _step
    val paymentDate = _paymentDate
    val paymentMethod = _paymentMethod

    val sortDirection = _sortDirection
    val sortBy = _sortBy
    val isLoad = _isLoad

    val showFilter = _showFilter
    val sortedPayments = _data


    init {
        request2Data()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Data(){
        GlobalScope.launch {
            _isLoad.value = false
            try {
                _data.value = getPayments(
                    rentalId = _rentalId.value,
                    amount = _amount.value,
                    step = _step.value,
                    paymentDate = _paymentDate.value,
                    paymentMethod = _paymentMethod.value,
                    sortDirection = _sortDirection.value,
                    sortBy = _sortBy.value
                )
                _isLoad.value = true
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }

}