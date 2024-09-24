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
    )
    private val _showFilter: MutableState<Boolean> = mutableStateOf(false)

    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _firstName: MutableState<String?> = mutableStateOf(null)
    private val _lastName: MutableState<String?> = mutableStateOf(null)
    private val _email: MutableState<String?> = mutableStateOf(null)
    private val _phone: MutableState<String?> = mutableStateOf(null)
    private val _license: MutableState<String?> = mutableStateOf(null)
    private val _isBanned: MutableState<Boolean?> = mutableStateOf(null)
    private val _sortDirection: MutableState<String> = mutableStateOf("ASC")
    private val _sortBy: MutableState<String> = mutableStateOf("customer_id")
    private val _isLoad: MutableState<Boolean> = mutableStateOf(true)

    val showPopup = _showPopup
    val textPopup = _textPopup
    val firstName = _firstName
    val lastName = _lastName
    val email = _email
    val phone = _phone
    val license = _license
    val isBanned = _isBanned
    val sortDirection = _sortDirection
    val sortBy = _sortBy
    val isLoad = _isLoad

    val showFilter = _showFilter

    val sortedCustomers = _data

    init {
        request2Get()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        GlobalScope.launch {
            _isLoad.value = false
            try {
                _data.value = getCustomers(
                    firstName = _firstName.value,
                    lastName = _lastName.value,
                    email = _email.value,
                    phoneNumber = _phone.value,
                    driverLicense = _license.value,
                    isBanned = _isBanned.value,
                    sortDirection = _sortDirection.value,
                    sortBy = _sortBy.value,
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