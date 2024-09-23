package ui.rental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.rentals.RentalRequest
import components.rental.PostRentalComponent
import widgets.PopupNotification
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PostRentalScreen(component: PostRentalComponent) {
    Box(contentAlignment = Alignment.Center) {
        SurveyForm({component.request2Post()}, component.rental)
        PopupNotification(component.showPopup, component.textPopup.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyForm(onRentalAdded: () -> Unit, rental: MutableState<RentalRequest?>) {
    var customerId by remember { mutableStateOf("") }
    var carId by remember { mutableStateOf("") }
    var startDateInput by remember { mutableStateOf("") }
    var endDateInput by remember { mutableStateOf("") }

// Регулярное выражение для проверки формата даты "ГГГГ-ММ-ДД"
    val dateRegex = Regex("""^\d{4}-\d{2}-\d{2}$""")

    // Функция для проверки правильности даты
    fun isValidDate(date: String): Boolean {
        return dateRegex.matches(date)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(16.dp)) // Фон страницы
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Изображение профиля
            Image(
                imageVector = Icons.Filled.Person, // Замените на ресурс вашего изображения профиля
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )

            Text(
                text = "Добавить аренду",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )

            // Поля ввода
            TextField(
                value = customerId,
                onValueChange = { customerId = it },
                label = { Text("ID клиента") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (customerId.isNotEmpty()) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (customerId.isNotEmpty()) Color.Green else Color.Red
                )
            )

            TextField(
                value = carId,
                onValueChange = { carId = it },
                label = { Text("ID авто") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (carId.isNotEmpty()) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (carId.isNotEmpty()) Color.Green else Color.Red
                )
            )

            TextField(
                value = startDateInput,
                onValueChange = { startDateInput = it },
                label = { Text("Начальная дата (ГГГГ-ММ-ДД)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (isValidDate(startDateInput)) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (isValidDate(startDateInput)) Color.Green else Color.Red
                )
            )

            TextField(
                value = endDateInput,
                onValueChange = { endDateInput = it },
                label = { Text("Конечная дата (ГГГГ-ММ-ДД)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (isValidDate(endDateInput)) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (isValidDate(endDateInput)) Color.Green else Color.Red
                )
            )

            // Кнопка добавления пользователя
            Button(
                enabled = customerId.isNotEmpty()
                        && carId.isNotEmpty()
                        && isValidDate(startDateInput)
                        && isValidDate(endDateInput),
                onClick = {
                    // Создание объекта CustomerResponse и вызов функции обратного вызова
                    val newUser = RentalRequest(
                        customer_id = customerId.toLong(),
                        car_id = carId.toLong(),
                        start_date = OffsetDateTime.parse(startDateInput + "T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        end_date = OffsetDateTime.parse(endDateInput + "T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    )
                    rental.value = newUser
                    onRentalAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Добавить ренту")
            }
        }
    }
}