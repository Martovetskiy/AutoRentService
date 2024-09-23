package ui.rental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.rentals.RentalRequest
import api.rentals.RentalResponse
import components.rental.GetRentalComponent
import widgets.PopupNotification
import java.time.OffsetDateTime

@Composable
fun GetRentalScreen(component: GetRentalComponent) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            SearchWidget({ component.request2Get() }, component.id)
            if (component.rental.value != null) {
                EditRentalScreen(component)
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Введите ID и нажмите Найти", fontSize = 26.sp)
                }
            }
        }
        PopupNotification(component.showPopup, component.textPopup.value)
    }
}

@Composable
private fun SearchWidget(onSearch: () -> Unit, id: MutableState<Long?>) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Поле для ввода поиска
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Введите ID...") },
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp)) // Пробел между полем и кнопкой

        // Кнопка "Найти"
        Button(onClick = {
            id.value = searchQuery.toLongOrNull()
            onSearch()
        }) {
            Text(text = "Найти", fontSize = 16.sp)
        }
    }
}

@Composable
private fun EditRentalScreen(component: GetRentalComponent) {

    // Состояние для режима редактирования (по умолчанию - режим просмотра)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)) // Фон страницы
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Изображение автомобиля (или иконка)
            Image(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Rental Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )
            Row(modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    text = if (component.isEdit.value) "Редактировать аренду" else "Карточка аренды",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (component.isDelete.value) "[Удален]" else "",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            // Кнопка для переключения между режимами
            Button(
                onClick = { component.isEdit.value = !component.isEdit.value },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(text = if (component.isEdit.value) "Перейти в режим просмотра" else "Перейти в режим редактирования")
            }

            if (component.isEdit.value) {
                Button(
                    enabled = !component.isDelete.value,
                    onClick = {
                        component.request2Delete()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), // Красный цвет для кнопки удаления
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Удалить ренту", color = Color.White) // Белый текст для кнопки
                }

            } else {
                // Режим просмотра
                Text(text = "ID клиента: ${component.rental.value!!.customer_id}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "ID машины: ${component.rental.value!!.car_id}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Начало аренды: ${component.rental.value!!.start_date}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Конец аренды: ${component.rental.value!!.end_date}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Итого: ${component.rental.value!!.total_price}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}