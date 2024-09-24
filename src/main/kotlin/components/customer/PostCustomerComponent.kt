package components.customer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import api.cutomers.CustomerRequest
import api.cutomers.postCustomer
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostCustomerComponent  (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _customer: MutableState<CustomerRequest?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")


    val showPopup = _showPopup
    val textPopup = _textPopup
    val customer = _customer

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Post(){
        GlobalScope.launch {
            try {
                postCustomer(_customer.value!!)
                _textPopup.value = "Пользователь успешно добавлен"
                _showPopup.value = true
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }
}