package components.rental

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import api.rentals.RentalResponse
import api.rentals.getRentals
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GetRentalsComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _data: MutableState<List<RentalResponse>> = mutableStateOf(listOf())
    private val _firstNameFilter: MutableState<String?> = mutableStateOf(null)
    private val _emailFilter: MutableState<String?> = mutableStateOf(null)
    private val _makeFilter: MutableState<String?> = mutableStateOf(null)
    private val _modelFilter: MutableState<String?> = mutableStateOf(null)
    private val _startDateFilter: MutableState<String?> = mutableStateOf(null)
    private val _endDateFilter: MutableState<String?> = mutableStateOf(null)
    private val _priceFilter: MutableState<String?> = mutableStateOf(null)
    private val _createAtFilter: MutableState<String?> = mutableStateOf(null)
    private val _sortDirection: MutableState<String> = mutableStateOf("ASC")
    private val _sortBy: MutableState<String> = mutableStateOf("firstName")
    private val _isLoad: MutableState<Boolean> = mutableStateOf(true)

    private val _showFilter: MutableState<Boolean> = mutableStateOf(false)

    val data = _data
    val firstNameF = _firstNameFilter
    val emailF = _emailFilter
    val makeF = _makeFilter
    val modelF = _modelFilter
    val startDateF = _startDateFilter
    val endDateF = _endDateFilter
    val priceF = _priceFilter
    val createAtF = _createAtFilter
    val sortDirection = _sortDirection
    val sortBy = _sortBy
    val showFilter = _showFilter
    val isLoad = _isLoad

    init {
        request2Get()
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        GlobalScope.launch {
            _isLoad.value = true
            _data.value = getRentals(
                firstName = _firstNameFilter.value,
                email = _emailFilter.value,
                make = _makeFilter.value,
                model = _modelFilter.value,
                startDate = _startDateFilter.value,
                endDate = _endDateFilter.value,
                totalPrice = _priceFilter.value,
                createdAt = _createAtFilter.value,
                sortDirection = _sortDirection.value,
                sortBy = _sortBy.value,
            )
            _isLoad.value = false
        }
    }
}
