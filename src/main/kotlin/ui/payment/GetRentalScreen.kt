package ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.payment.GetPaymentComponent
import widgets.PopupNotification

@Composable
fun GetPaymentScreen(component: GetPaymentComponent) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            SearchWidget({ component.request2Get() }, component.id)
            if (component.payment.value != null) {
                EditPaymentScreen(component)
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
private fun EditPaymentScreen(component: GetPaymentComponent) {

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
                contentDescription = "Payment Image",
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
                Text(text = "ID аренды: ${component.payment.value!!.rental_id}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Сумма: ${component.payment.value!!.amount}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Шаг: ${component.payment.value!!.step}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата платежа: ${component.payment.value!!.payment_date}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Метод платежа: ${component.payment.value!!.payment_method}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}