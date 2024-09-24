package ui.car

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.cars.CarRequest
import carMakes
import carModels
import components.car.PostCarComponent
import resources.icons.CarSvgrepoCom
import widgets.PopupNotification

@Composable
fun PostCarScreen(component: PostCarComponent) {
    Box(contentAlignment = Alignment.Center) {
        AddCarScreen(onCarAdded = { component.request2Post() }, component.car)
        PopupNotification(component.showPopup, component.textPopup.value)
    }
}

@Composable
fun AddCarScreen(onCarAdded: () -> Unit, car: MutableState<CarRequest?>) {
    // States for input data
    val make = remember { mutableStateOf("") }
    val model = remember { mutableStateOf("") }
    val year = remember { mutableStateOf("") }
    val colorHex = remember { mutableStateOf("") }
    val pricePerDay = remember { mutableStateOf("") }
    val numberPlate = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("") }

    var expandedMake by remember { mutableStateOf(false) }
    var expandedModel by remember { mutableStateOf(false) }

    // Regex patterns for validation
    val patternYear = Regex("""^\d{4}$""")
    val patternColorHex = Regex("""^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$""")
    val patternPricePerDay = Regex("""^\d+(\.\d{1,2})?$""")
    val patternNumberPlate = Regex("""^[А-Я]{1}[0-9]{1,3}[А-Я]{1,2}[0-9]{2}$""")
    val patternStatus = Regex("""^(Available|Rented|UnderMaintenance)$""")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Car image placeholder
            Image(
                imageVector = CarSvgrepoCom,
                contentDescription = "Car Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape),
            )

            Text(
                text = "Добавить автомобиль",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )


                // Display for selected make
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


                // Display for selected model



            RegexInput(year, patternYear, "Год (YYYY)")
            RegexInput(colorHex, patternColorHex, "Цвет (HEX, #RRGGBB)")
            RegexInput(pricePerDay, patternPricePerDay, "Цена за день")
            RegexInput(numberPlate, patternNumberPlate, "Номерной знак")
            RegexInput(status, patternStatus, "Статус (Available/Rented/UnderMaintenance)") // New input for status

            // Button to add the car
            Button(
                enabled = make.value.isNotEmpty()
                        && model.value.isNotEmpty()
                        && patternYear.matches(year.value)
                        && patternColorHex.matches(colorHex.value)
                        && patternPricePerDay.matches(pricePerDay.value)
                        && patternNumberPlate.matches(numberPlate.value)
                        && patternStatus.matches(status.value), // Include status in validation
                onClick = {
                    // Create CarRequest object and invoke the callback function
                    val newCar = CarRequest(
                        make = make.value,
                        model = model.value,
                        year = year.value.toInt(), // Convert year to Integer
                        color_hex = colorHex.value,
                        price_per_day = pricePerDay.value.toDouble(), // Convert price to Float
                        number_plate = numberPlate.value,
                        status = status.value // Add status to CarRequest
                    )
                    car.value = newCar
                    onCarAdded() // Call the function to handle the addition of the car
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Добавить автомобиль")
            }
        }
    }
}


@Composable
fun RegexInput(string: MutableState<String>, pattern: Regex, label: String) {
    val isValid = pattern.matches(string.value)

    TextField(
        value = string.value,
        onValueChange = { string.value = it },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        textStyle = TextStyle(color = if (isValid) Color.Black else Color.Red), // Change text color based on validity
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green else Color.Red,
        )
    )
}