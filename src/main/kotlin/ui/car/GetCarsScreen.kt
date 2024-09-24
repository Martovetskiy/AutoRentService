package ui.car

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.cars.CarResponse
import carMakes
import carModels
import components.car.GetCarsComponent
import icons.Close
import resources.icons.FilterSvgrepoCom
import widgets.PopupNotification
import widgets.ShimmerEffect
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Composable
fun GetCarsScreen(component: GetCarsComponent) {

    var showFilter by remember { component.showFilter }

    val attributeNames = CarResponse::class.declaredMemberProperties.sortedBy { CarResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    val dictWeight: Map<String, Float> = mapOf(
        "car_id" to 1f,
        "make" to 2f,
        "model" to 2f,
        "year" to 1.5f,
        "color_hex" to 2f,
        "price_per_day" to 2f,
        "number_plate" to 2f,
        "status" to 2f,
        "create_at" to 2f
    )
    if (component.isLoad.value)
    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Box(modifier = Modifier.fillMaxWidth()) {
                if (showFilter) Filters(component)
                if (!showFilter) Row (modifier = Modifier.align(Alignment.TopEnd).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Просмотр", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Row(modifier = Modifier.clickable { showFilter = !showFilter } ,verticalAlignment = Alignment.CenterVertically){
                        Icon(
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp),
                            imageVector = FilterSvgrepoCom,
                            contentDescription = null,
                            tint = Color.Gray
                        )

                        Text("Фильтры и сортировка", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(attributeNames.size) { i ->
                    val atr = attributeNames[i]
                    Box(
                        modifier = Modifier
                            .weight(dictWeight[atr.name] ?: 10f)
                            .height(56.dp)
                            .border(width = 1.dp, Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = atr.name,
                            textAlign = TextAlign.Center, color = Color.Black
                        )
                    }
                }
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(component.sortedCars.value) { car ->
                    CarRow(car, dictWeight, attributeNames)
                }
            }
        }
        PopupNotification(component.showPopup, component.textPopup.value)
    }
    else{
        ShimmerEffect(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(16.dp)) {
            Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                Text("Загрузка...")
            }
        }
    }
}


@Composable
fun CarRow(car: CarResponse, dictWeight: Map<String, Float>, attributeNames: List<KProperty1<CarResponse, *>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { /* Обработка нажатия */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(attributeNames.size) { i ->
            val atr = attributeNames[i]
            val value = atr.get(car).toString()
            Box(
                modifier = Modifier
                    .weight(dictWeight[atr.name] ?: 10f)
                    .height(56.dp)
                    .border(width = 1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {  Text(
                text = value,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            }
        }
    }
}
@Composable
private fun Filters(component: GetCarsComponent){
    val attributeNames = CarResponse::class.declaredMemberProperties.sortedBy { CarResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    var expandedMake by remember { mutableStateOf(false) }
    var expandedModel by remember { mutableStateOf(false) }
    Column {
        Text(modifier = Modifier.padding(bottom = 16.dp), text ="Фильтрация", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Box(modifier = Modifier.padding(bottom = 8.dp)) {

            Box (modifier = Modifier.fillMaxWidth()
                .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                .clickable { expandedMake = true }.border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                contentAlignment = Alignment.CenterStart) {
                Text(
                    text = "Марка: ${if (!component.make.value.isNullOrEmpty()) component.make.value else "Не выбрана"}",
                    modifier = Modifier.padding(16.dp)
                )
                Row (Modifier.align(Alignment.CenterEnd).padding(end =8.dp), verticalAlignment = Alignment.CenterVertically){
                    if (!component.make.value.isNullOrEmpty()) Icon(
                        modifier = Modifier.size(24.dp)
                            .clickable { component.make.value = null; component.model.value = null },
                        imageVector = Close,
                        contentDescription = null
                    )
                }
                DropdownMenu(

                    expanded = expandedMake,
                    onDismissRequest = { expandedMake = false }
                ) {
                    carMakes.sorted().forEach { carMake ->
                        DropdownMenuItem(onClick = {
                            component.make.value = carMake
                            component.model.value = null // Reset model when make changes
                            expandedMake = false
                        }) {
                            Text(text = carMake)
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.padding(bottom = 8.dp)) { // Добавляем отступ для Divider

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .clickable { expandedModel = true }
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Модель: ${if (!component.model.value.isNullOrEmpty()) component.model.value else "Не выбрана"}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            Row (Modifier.align(Alignment.CenterEnd).padding(end =8.dp), verticalAlignment = Alignment.CenterVertically){
                if (!component.model.value.isNullOrEmpty()) Icon(
                    modifier = Modifier.size(24.dp)
                        .clickable { component.model.value = null },
                    imageVector = Close,
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = expandedModel,
                onDismissRequest = { expandedModel = false }
            ) {
                // Show models based on selected make
                carModels[component.make.value]?.sorted()?.forEach { carModel ->
                    DropdownMenuItem(onClick = {
                        component.model.value = carModel
                        expandedModel = false
                    }) {
                        Text(text = carModel)
                    }
                }
            }

        }
        for (attribute in attributeNames.slice(3..6)) {
            TextField(
                value = when (attribute.name) {
                    "year" -> component.year.value ?: ""
                    "color_hex" -> component.colorHex.value ?: ""
                    "price_per_day" -> component.pricePerDay.value ?: ""
                    "number_plate" -> component.numberPlate.value ?: ""
                    "status" -> component.status.value ?: ""
                    else -> ""
                },
                onValueChange = {
                    when (attribute.name) {
                        "year" -> component.year.value = it
                        "color_hex" -> component.colorHex.value = it
                        "price_per_day" -> component.pricePerDay.value = it
                        "number_plate" -> component.numberPlate.value = it
                    }
                },
                label = { Text(attribute.name) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Статус: ")
            for (key in listOf("Available", "Rented", "UnderMaintenance", null)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.status.value == key,
                        onClick = { component.status.value = key }
                    )
                    Text(key.toString())
                }
            }
        }

        Text(modifier = Modifier.padding(vertical = 16.dp), text ="Сортировка", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Сортировать по: ")
            for (key in listOf("ASC", "DESC")) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.sortDirection.value == key,
                        onClick = { component.sortDirection.value = key }
                    )
                    Text(key)
                }
            }

        }
        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Сортировать по столбцу: ")
            for (key in attributeNames) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.sortBy.value == key.name,
                        onClick = { component.sortBy.value = key.name }
                    )
                    Text(key.name)
                }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.showFilter.value = false
                component.request2Get()}
        ){
            Text("Применить")
        }
    }
}