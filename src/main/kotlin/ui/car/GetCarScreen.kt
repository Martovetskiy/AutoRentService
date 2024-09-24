package ui.car

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.cars.CarResponse
import carMakes
import carModels
import components.car.GetCarComponent
import resources.icons.CarSvgrepoCom
import widgets.PopupNotification

@Composable
fun GetCarScreen(component: GetCarComponent) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            SearchWidget({ component.request2Get() }, component.id)
            if (component.car.value != null) {
                EditCarScreen(component)
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
private fun EditCarScreen(component: GetCarComponent) {
    var expandedMake by remember { mutableStateOf(false) }
    var expandedModel by remember { mutableStateOf(false) }
    // Состояния для редактируемых данных
    val make = remember { mutableStateOf(component.car.value!!.make) }
    val model = remember { mutableStateOf(component.car.value!!.model) }
    val year = remember { mutableStateOf(component.car.value!!.year.toString()) }
    val colorHex = remember { mutableStateOf(component.car.value!!.color_hex) }
    val pricePerDay = remember { mutableStateOf(component.car.value!!.price_per_day.toString()) }
    val numberPlate = remember { mutableStateOf(component.car.value!!.number_plate) }
    val status = remember { mutableStateOf(component.car.value!!.status) }

    val patternYear = Regex("""^\d{4}$""")
    val patternColorHex = Regex("""^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""")
    val patternPricePerDay = Regex("""^\d+(\.\d{1,2})?$""")
    val patternNumberPlate = Regex("""^[А-Я]{1}[0-9]{1,3}[А-Я]{1,2}[0-9]{2}$""")
    val patternStatus = Regex("""^(Available|Rented|UnderMaintenance)$""")


    // Состояние для режима редактирования (по умолчанию - режим просмотра)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)) // Фон страницы
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            // Изображение автомобиля (или иконка)
            Image(
                imageVector = CarSvgrepoCom,
                contentDescription = "Car Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )
            Row(modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    text = if (component.isEdit.value) "Редактировать авто" else "Карточка автомобиля",
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
                // Поля ввода с предустановленными значениями
                Box(modifier = Modifier.padding(bottom = 8.dp)) { // Добавляем отступ для Divider
                    Box (modifier = Modifier.fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .clickable { expandedMake = true }.border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                        contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "Марка: ${make.value.ifEmpty { "Не выбрана" }}",
                            modifier = Modifier.padding(16.dp)
                        )
                        DropdownMenu(

                            expanded = expandedMake,
                            onDismissRequest = { expandedMake = false }
                        ) {
                            carMakes.sorted().forEach { carMake ->
                                DropdownMenuItem(onClick = {
                                    make.value = carMake
                                    model.value = "" // Reset model when make changes
                                    expandedMake = false
                                }) {
                                    Text(text = carMake)
                                }
                            }
                        }
                    }

                    Divider(
                        color = if (make.value.isNotEmpty()) Color.Green else Color.Red,
                        thickness = 1.dp, // Толщина границы
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter) // Выровнять по центру снизу
                    )
                }

                Box(modifier = Modifier.padding(bottom = 8.dp)) { // Добавляем отступ для Divider
                    Box (modifier = Modifier.fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .clickable { expandedModel = true }.border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                        contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "Модель: ${model.value.ifEmpty { "Не выбрана" }}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expandedModel,
                        onDismissRequest = { expandedModel = false }
                    ) {
                        // Show models based on selected make
                        carModels[make.value]?.sorted()?.forEach { carModel ->
                            DropdownMenuItem(onClick = {
                                model.value = carModel
                                expandedModel = false
                            }) {
                                Text(text = carModel)
                            }
                        }
                    }

                    Divider(
                        color = if (model.value.isNotEmpty()) Color.Green else Color.Red,
                        thickness = 1.dp, // Толщина границы
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter) // Выровнять по центру снизу
                    )
                }

                RegexInput(year, patternYear, "Год (YYYY)")
                RegexInput(colorHex, patternColorHex, "Цвет (HEX, #RRGGBB)")
                RegexInput(pricePerDay, patternPricePerDay, "Цена за день")
                RegexInput(numberPlate, patternNumberPlate, "Номерной знак")
                RegexInput(status, patternStatus, "Статус (Available/Rented/UnderMaintenance)")

                // Кнопка для обновления автомобиля
                Button(
                    enabled = make.value.isNotEmpty()
                            && model.value.isNotEmpty()
                            && patternYear.matches(year.value)
                            && patternColorHex.matches(colorHex.value)
                            && patternPricePerDay.matches(pricePerDay.value)
                            && patternNumberPlate.matches(numberPlate.value)
                            && patternStatus.matches(status.value),
                    onClick = {
                        // Обновление объекта CarResponse и вызов функции обратного вызова
                        component.carBuf.value = CarResponse(
                            car_id = component.carBuf.value!!.car_id,
                            make = make.value,
                            model = model.value,
                            year = year.value.toInt(), // Convert year to Integer
                            color_hex = colorHex.value,
                            price_per_day = pricePerDay.value.toDouble(), // Convert price to Float
                            number_plate = numberPlate.value,
                            status = status.value,
                            create_at = component.carBuf.value!!.create_at
                        )
                        component.request2Put()
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text(text = "Обновить автомобиль")
                }
                Button(
                    enabled = !component.isDelete.value,
                    onClick = {
                        component.request2Delete()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), // Красный цвет для кнопки удаления
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Удалить автомобиль", color = Color.White) // Белый текст для кнопки
                }
            } else {
                // Режим просмотра
                Text(text = "Марка: ${component.car.value!!.make}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Модель: ${component.car.value!!.model}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Год: ${component.car.value!!.year}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Цвет: ${component.car.value!!.color_hex}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Цена за день: ${component.car.value!!.price_per_day}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Номерной знак: ${component.car.value!!.number_plate}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Статус: ${component.car.value!!.status}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}