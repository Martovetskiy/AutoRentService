package components.customer

import androidx.compose.runtime.*
import api.cutomers.CustomerResponse
import api.cutomers.deleteCustomer
import api.cutomers.getCustomer
import api.cutomers.putCustomer
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.*

class GetCustomerComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _customer: MutableState<CustomerResponse?> = mutableStateOf(null)
    private val _customerBuf: MutableState<CustomerResponse?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _id: MutableState<Long?> = mutableStateOf(null)

    private val _isEdit: MutableState<Boolean> = mutableStateOf(false)
    private val _isDelete: MutableState<Boolean> = mutableStateOf(false)

    val customer = _customer
    val customerBuf = _customerBuf
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
                _customer.value = getCustomer(_id.value!!)
                _customerBuf.value = _customer.value
            }
            catch (e: Exception) {
                _textPopup.value = e.message.toString()
                _showPopup.value = true
                print("Error getting customer")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Put(){
        if (_customer.value == null) return
        GlobalScope.launch {
            _customer.value = putCustomer(_customerBuf.value!!)
            try {
                _customer.value = putCustomer(_customerBuf.value!!)
                _textPopup.value = "Пользователь успешно обновлен"
                _showPopup.value = true
            }
            catch (e: Exception){
                _customerBuf.value = _customer.value
                _textPopup.value = "Ошибка: " + e.message.toString()
                _showPopup.value = true

            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Delete(){
        GlobalScope.launch {
            try {
                _customer.value = deleteCustomer(_id.value!!)
                _showPopup.value = true
                _textPopup.value = "Пользователь успешно удален"
                _isDelete.value = true
                _isEdit.value = false
                _customer.value = null
            }
            catch (e: Exception) {
                _customer.value = null
                _showPopup.value = true
                _textPopup.value = "Ошибка: " + e.message.toString()
                print("Error deleting customer")
            }
        }
    }


}