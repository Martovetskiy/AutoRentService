package components.payment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import api.payments.PaymentRequest
import api.payments.postPayment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostPaymentComponent  (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _payment: MutableState<PaymentRequest?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")


    val showPopup = _showPopup
    val textPopup = _textPopup
    val payment = _payment

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Post(){
        GlobalScope.launch {
            try {
                postPayment(_payment.value!!)
                _textPopup.value = "Платеж успешно добавлен"
                _showPopup.value = true
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }
}