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
    )
    private val _showFilter: MutableState<Boolean> = mutableStateOf(false)

    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")

    private val _customerID: MutableState<String?> = mutableStateOf(null)
    private val _carID: MutableState<String?> = mutableStateOf(null)
    private val _startDate: MutableState<String?> = mutableStateOf(null)
    private val _endDate: MutableState<String?> = mutableStateOf(null)
    private val _totalPrice: MutableState<String?> = mutableStateOf(null)
    private val _sortDirection: MutableState<String> = mutableStateOf("ASC")
    private val _sortBy: MutableState<String> = mutableStateOf("rental_id")
    private val _isLoad: MutableState<Boolean> = mutableStateOf(true)

    val showPopup = _showPopup
    val textPopup = _textPopup

    val customerID = _customerID
    val carID = _carID
    val startDate = _startDate
    val endDate = _endDate
    val totalPrice = _totalPrice

    val sortDirection = _sortDirection
    val sortBy = _sortBy
    val isLoad = _isLoad

    val showFilter = _showFilter
    val sortedRentals = _data

    init {
        request2Get()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        GlobalScope.launch {
            _showFilter.value = false
            _isLoad.value = false
            try {
                _data.value = getRentals(
                    customer_id = _customerID.value,
                    car_id = _carID.value,
                    start_date = _startDate.value,
                    end_date = _endDate.value,
                    total_price = _totalPrice.value,
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