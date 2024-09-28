package components.rental

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import api.cars.CarResponse
import api.cars.getCars
import api.cutomers.CustomerResponse
import api.cutomers.getCustomers
import api.rentals.RentalResponse
import api.rentals.deleteRental
import api.rentals.putRental
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GetRentalComponent (
    componentContext: ComponentContext,
    rental: RentalResponse?
): ComponentContext by componentContext {
    private val _rentalOrigin: MutableState<RentalResponse?> = mutableStateOf(rental)
    private val _rentalBuf: MutableState<RentalResponse?> = _rentalOrigin
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _isEdit: MutableState<Boolean> = mutableStateOf(false)
    private val _isDelete: MutableState<Boolean> = mutableStateOf(false)

    private val _customers: MutableState<List<CustomerResponse>> = mutableStateOf(listOf())
    private val _cars: MutableState<List<CarResponse>> = mutableStateOf(listOf())

    val rental = _rentalOrigin
    val showPopup = _showPopup
    val textPopup = _textPopup
    val isEdit = _isEdit
    val rentalBuf = _rentalBuf

    val customers = _customers
    val cars = _cars

    init {
        request2GetInfo()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2GetInfo(){
        GlobalScope.launch {
            try {
                _customers.value = getCustomers()
                _cars.value = getCars()
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }

        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun request2UpdateRental() {
        if (_rentalOrigin.value == null) return
        GlobalScope.launch {
            try {
                _rentalOrigin.value = putRental(_rentalBuf.value!!)
                _textPopup.value = "Аренда успешно обновлен"
                _showPopup.value = true
            } catch (e: Exception) {
                _rentalBuf.value = _rentalOrigin.value
                _textPopup.value = "Ошибка: " + e.message.toString()
                _showPopup.value = true
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun request2Delete(){
        GlobalScope.launch {
            try {
                _rentalOrigin.value = deleteRental(rental.value!!.rentalId)
                _showPopup.value = true
                _textPopup.value = "Аренда успешно удалена"
                _isDelete.value = true
                _isEdit.value = false
                _rentalOrigin.value = null
            }
            catch (e: Exception) {
                _rentalOrigin.value = null
                _showPopup.value = true
                _textPopup.value = e.message.toString()
                print("Error deleting rental")
            }
        }
    }


}