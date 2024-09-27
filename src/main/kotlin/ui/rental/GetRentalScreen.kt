package ui.rental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.rentals.RentalResponse
import components.rental.GetRentalComponent
import resources.icons.MoneyBankCheckPaymentChequeFinanceBusinessSvgrepoCom
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
    // Состояния для редактируемых данных проката
    val rentalId = component.rental.value!!.rentalId
    val customerId = remember { mutableStateOf(component.rental.value!!.customerId.toString()) }
    val carId = remember { mutableStateOf(component.rental.value!!.carId.toString()) }
    val startDate = remember { mutableStateOf(component.rental.value!!.startDate.toString()) }
    val endDate = remember { mutableStateOf(component.rental.value!!.endDate.toString()) }

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
            Image(
                imageVector = MoneyBankCheckPaymentChequeFinanceBusinessSvgrepoCom, // Замените на ресурс вашего изображения профиля
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally) // Круглая форма
            )
            Row(modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    text = if (component.isEdit.value) "Редактировать аренду" else "Карточка аренды",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
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
                // Поля ввода для customerId и carId
                TextField(
                    value = customerId.value,
                    onValueChange = { customerId.value = it },
                    label = { Text("ID Клиента") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = carId.value,
                    onValueChange = { carId.value = it },
                    label = { Text("ID Автомобиля") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = startDate.value,
                    onValueChange = { startDate.value = it },
                    label = { Text("Дата начала") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = endDate.value,
                    onValueChange = { endDate.value = it },
                    label = { Text("Дата окончания") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Кнопка для обновления данных о прокате
                Button(
                    onClick = {
                        // Обновление объекта RentalResponse и вызов функции обратного вызова
                        component.rentalBuf.value = RentalResponse(
                            rentalId = rentalId,
                            customerId = customerId.value.toLong(), // Преобразование строки в Long
                            carId = carId.value.toLong(), // Преобразование строки в Long
                            startDate = OffsetDateTime.parse(startDate.value),
                            endDate = OffsetDateTime.parse(endDate.value),
                            totalPrice = 1.0,
                            createAt = component.rentalBuf.value!!.createAt
                        )
                        component.request2UpdateRental()
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text(text = "Обновить аренду")
                }

                // Кнопка для удаления проката
                Button(
                    onClick = {
                        component.request2Delete()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Удалить аренду", color = Color.White)
                }

            } else {
                // Режим просмотра
                Text(text = "ID Клиента: ${component.rental.value!!.customerId}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "ID Автомобиля: ${component.rental.value!!.carId}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата начала: ${component.rental.value!!.startDate}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата окончания: ${component.rental.value!!.endDate}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Общая цена: ${component.rental.value!!.totalPrice}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Создано: ${component.rental.value!!.createAt}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}

