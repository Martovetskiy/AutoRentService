package components.payment

import androidx.compose.runtime.*
import api.payments.PaymentResponse
import api.payments.deletePayment
import api.payments.getPayment
import api.payments.putPayment
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.*

class GetPaymentComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _payment: MutableState<PaymentResponse?> = mutableStateOf(null)
    private val _paymentBuf: MutableState<PaymentResponse?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _id: MutableState<Long?> = mutableStateOf(null)

    private val _isEdit: MutableState<Boolean> = mutableStateOf(false)
    private val _isDelete: MutableState<Boolean> = mutableStateOf(false)

    val payment = _payment
    val paymentBuf = _paymentBuf
    val id = _id
    val showPopup = _showPopup
    val textPopup = _textPopup
    val isEdit = _isEdit

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        if (_id.value == null) {
            _textPopup.value = "ID может быть только типа LONG"
            _showPopup.value = true
            return
        }
        GlobalScope.launch {
            try {
                _payment.value = getPayment(_id.value!!)
                _paymentBuf.value = _payment.value
            }
            catch (e: Exception) {
                _payment.value = null
                _textPopup.value = e.message.toString()
                _showPopup.value = true
                print("Error getting payment")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Put(){
        GlobalScope.launch {
            try {
                _payment.value = putPayment(_paymentBuf.value!!)
                _paymentBuf.value = _payment.value
                _showPopup.value = true
                _textPopup.value = "Платеж успешно обновлен"
            }
            catch (e: Exception) {
                _payment.value = null
                _textPopup.value = e.message.toString()
                _showPopup.value = true
                print("Error getting payment")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Delete(){
        GlobalScope.launch {
            try {
                _payment.value = deletePayment(_id.value!!)
                _showPopup.value = true
                _textPopup.value = "Платеж успешно удален"
                _isDelete.value = true
                _isEdit.value = false
                _payment.value = null
            }
            catch (e: Exception) {
                _payment.value = null
                _showPopup.value = true
                _textPopup.value = e.message.toString()
                print("Error deleting payment")
            }
        }
    }


}