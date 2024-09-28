package ui.rental

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import ui.subtractDates
import widgets.PopupNotification
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun GetRentalScreen(component: GetRentalComponent) {
    Box(contentAlignment = Alignment.Center) {
        Column {
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
private fun EditRentalScreen(component: GetRentalComponent) {
    // Состояния для редактируемых данных проката
    val rentalId = component.rental.value!!.rentalId
    val customer = remember { mutableStateOf(component.rental.value!!.customer)}
    val car = remember { mutableStateOf(component.rental.value!!.car)}
    val startDate = remember { mutableStateOf(component.rental.value!!.startDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale("ru")))) }
    val endDate = remember { mutableStateOf(component.rental.value!!.endDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd", Locale("ru")))) }
    var expandedMake by remember { mutableStateOf(false) }
    var expandedModel by remember { mutableStateOf(false) }

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
                Box(modifier = Modifier.padding(bottom = 8.dp)) { // Добавляем отступ для Divider
                    Box (modifier = Modifier.fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .clickable { expandedModel = true }.border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                        contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "${customer.value!!.firstName} ${customer.value!!.email}",
                            modifier = Modifier.padding(16.dp)
                        )
                        DropdownMenu(

                            expanded = expandedModel,
                            onDismissRequest = { expandedModel = false }
                        ) {
                            component.customers.value.map { Triple(it.customerId, it.firstName, it.email) }.sortedBy { it.first }.forEach { cust ->
                                DropdownMenuItem(onClick = {
                                    customer.value = component.customers.value.first { it.customerId == cust.first }
                                    expandedModel = false
                                }) {
                                    Text(text = cust.second + ' ' + cust.third)
                                }
                            }
                        }
                    }

                    Divider(
                        color = Color.Green,
                        thickness = 1.dp, // Толщина границы
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter) // Выровнять по центру снизу
                    )
                }

                Box(modifier = Modifier.padding(bottom = 8.dp)) { // Добавляем отступ для Divider
                    Box (modifier = Modifier.fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .clickable { expandedMake = true }.border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                        contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "#${car.value!!.carId} ${car.value!!.make} ${car.value!!.model}",
                            modifier = Modifier.padding(16.dp)
                        )
                        DropdownMenu(

                            expanded = expandedMake,
                            onDismissRequest = { expandedMake = false }
                        ) {
                            component.cars.value.map { Triple(it.carId, it.make, it.model) }.sortedBy { it.first }.forEach { carMake ->
                                DropdownMenuItem(onClick = {
                                    car.value = component.cars.value.first { it.carId == carMake.first }
                                    expandedMake = false
                                }) {
                                    Text(text = '#' + carMake.first.toString() + ' ' + carMake.second + carMake.third)
                                }
                            }
                        }
                    }

                    Divider(
                        color = Color.Green,
                        thickness = 1.dp, // Толщина границы
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter) // Выровнять по центру снизу
                    )
                }


                TextField(
                    isError = !subtractDates(endDate.value,startDate.value,),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Green,
                        unfocusedIndicatorColor = Color.Green
                    ),
                    value = startDate.value,
                    onValueChange = { startDate.value = it },
                    label = { Text("Дата начала (ГГГГ-ММ-ДД)") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    isError = !subtractDates(endDate.value,startDate.value,),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Green,
                        unfocusedIndicatorColor = Color.Green
                    ),
                    value = endDate.value,
                    onValueChange = { endDate.value = it },
                    label = { Text("Дата окончания (ГГГГ-ММ-ДД)") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Кнопка для обновления данных о прокате
                Button(
                    enabled = subtractDates(endDate.value,startDate.value,),
                    onClick = {
                        // Обновление объекта RentalResponse и вызов функции обратного вызова
                        component.rentalBuf.value = RentalResponse(
                            rentalId = rentalId,
                            customerId = customer.value!!.customerId, // Преобразование строки в Long
                            carId = car.value!!.carId, // Преобразование строки в Long
                            startDate = OffsetDateTime.parse(startDate.value + "T15:51:18+00:00"),
                            endDate = OffsetDateTime.parse(endDate.value + "T15:51:18+00:00"),
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
                Text(text = "Клиент: ${component.rental.value!!.customer!!.firstName} ${component.rental.value!!.customer!!.email}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Автомобиль: #${component.rental.value!!.carId} ${component.rental.value!!.car!!.make} ${component.rental.value!!.car!!.model} ${component.rental.value!!.car!!.year}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата начала: ${component.rental.value!!.startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru")))}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата окончания: ${component.rental.value!!.endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru")))}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Общая цена: ${component.rental.value!!.totalPrice}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Создано: ${component.rental.value!!.createAt.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale("ru")))}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}

