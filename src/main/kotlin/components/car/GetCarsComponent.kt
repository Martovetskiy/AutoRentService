package components.car

import androidx.compose.runtime.*
import api.cars.CarResponse
import com.arkivanov.decompose.ComponentContext
import api.cars.getCars
import kotlinx.coroutines.*

class GetCarsComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    
    
    private var _data: MutableState<List<CarResponse>> = mutableStateOf(
        mutableListOf()
    )
    private val _showFilter: MutableState<Boolean> = mutableStateOf(false)

    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    
    private val _make: MutableState<String?> = mutableStateOf(null)
    private val _model: MutableState<String?> = mutableStateOf(null)
    private val _year: MutableState<String?> = mutableStateOf(null)
    private val _colorHex: MutableState<String?> = mutableStateOf(null)
    private val _pricePerDay: MutableState<String?> = mutableStateOf(null)
    private val _numberPlate: MutableState<String?> = mutableStateOf(null)
    private val _status: MutableState<String?> = mutableStateOf(null)
    private val _sortDirection: MutableState<String> = mutableStateOf("ASC")
    private val _sortBy: MutableState<String> = mutableStateOf("car_id")
    private val _isLoad: MutableState<Boolean> = mutableStateOf(true)

    val make = _make
    val model = _model
    val year = _year
    val colorHex = _colorHex
    val pricePerDay = _pricePerDay
    val numberPlate = _numberPlate
    val sortDirection = _sortDirection
    val sortBy = _sortBy
    val isLoad = _isLoad
    val status = _status
    val showFilter = _showFilter

    val showPopup = _showPopup
    val textPopup = _textPopup

    val sortedCars = _data

    init {
        request2Get()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        GlobalScope.launch {
            _showFilter.value = false
            _isLoad.value = false
            try {
                _data.value = getCars(
                    make = _make.value,
                    model = _model.value,
                    year = _year.value,
                    color_hex = _colorHex.value,
                    price_per_day = _pricePerDay.value,
                    number_plate = _numberPlate.value,
                    status = _status.value,
                    sortBy = _sortBy.value,
                    sortDirection = _sortDirection.value
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