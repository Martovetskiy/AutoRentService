package components.rental

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import api.rentals.RentalRequest
import api.rentals.postRental
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostRentalComponent  (
    componentContext: ComponentContext,
): ComponentContext by componentContext {
    private val _rental: MutableState<RentalRequest?> = mutableStateOf(null)
    private val _showPopup: MutableState<Boolean> = mutableStateOf(false);
    private val _textPopup: MutableState<String> = mutableStateOf("")


    val showPopup = _showPopup
    val textPopup = _textPopup
    val rental = _rental

    @OptIn(DelicateCoroutinesApi::class)
    fun request2Post(){
        GlobalScope.launch {
            try {
                postRental(_rental.value!!)
                _textPopup.value = "Рента успешно добавлена"
                _showPopup.value = true
            }
            catch (e: Exception){
                _textPopup.value = e.message.toString()
                _showPopup.value = true
            }
        }
    }
}