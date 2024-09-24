package components.rental

import androidx.compose.runtime.*
import api.rentals.deleteRental
import api.rentals.getRental
import api.rentals.RentalResponse
import api.rentals.putRental
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.*

class GetRentalComponent (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _rental: MutableState<RentalResponse?> = mutableStateOf(null)
    private val _rentalBuf: MutableState<RentalResponse?> = _rental
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false)
    private val _textPopup: MutableState<String> = mutableStateOf("")
    private val _id: MutableState<Long?> = mutableStateOf(null)

    private val _isEdit: MutableState<Boolean> = mutableStateOf(false)
    private val _isDelete: MutableState<Boolean> = mutableStateOf(false)

    val rental = _rental
    val id = _id
    val showPopup = _showPopup
    val textPopup = _textPopup
    val isEdit = _isEdit
    val rentalBuf get() = _rentalBuf

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Get(){
        if (_id.value == null) {
            _textPopup.value = "ID может быть только типа LONG"
            _showPopup.value = true
            return
        }
        GlobalScope.launch {
            try {
                _rental.value = getRental(_id.value!!)
            }
            catch (e: Exception) {
                _textPopup.value = e.message.toString()
                _showPopup.value = true
                print("Error getting rental")
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun request2UpdateRental() {
        if (_rental.value == null) return
        GlobalScope.launch {
            try {
                _rental.value = putRental(_rentalBuf.value!!)
                _textPopup.value = "Аренда успешно обновлен"
                _showPopup.value = true
            } catch (e: Exception) {
                _rentalBuf.value = _rental.value
                _textPopup.value = "Ошибка: " + e.message.toString()
                _showPopup.value = true
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun request2Delete(){
        GlobalScope.launch {
            try {
                _rental.value = deleteRental(_id.value!!)
                _showPopup.value = true
                _textPopup.value = "Аренда успешно удалена"
                _isDelete.value = true
                _isEdit.value = false
                _rental.value = null
            }
            catch (e: Exception) {
                _rental.value = null
                _showPopup.value = true
                _textPopup.value = e.message.toString()
                print("Error deleting rental")
            }
        }
    }


}