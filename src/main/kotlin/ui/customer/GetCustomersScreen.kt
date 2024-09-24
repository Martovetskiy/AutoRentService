package ui.customer

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
import api.cutomers.CustomerResponse
import components.customer.GetCustomersComponent
import resources.icons.FilterSvgrepoCom
import widgets.PopupNotification
import widgets.ShimmerEffect
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Composable
fun GetCustomersScreen(component: GetCustomersComponent){

    val attributeNames = CustomerResponse::class.declaredMemberProperties.sortedBy { CustomerResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    var showFilter by remember {component.showFilter}

    val dictWeight: Map<String, Float> = mapOf(
        "customer_id" to 1f,
        "first_name" to 2f,
        "last_name" to 2f,
        "email" to 3f,
        "phone_number" to 2f,
        "driver_license" to 2f,
        "is_banned" to 2f,
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
                items(component.sortedCustomers.value) { customer ->
                    CustomerRow(customer, dictWeight, attributeNames)
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
fun CustomerRow(customer: CustomerResponse, dictWeight: Map<String, Float>, attributeNames: List<KProperty1<CustomerResponse, *>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { /* Обработка нажатия */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(attributeNames.size){i ->
            val atr = attributeNames[i]
            val value = atr.get(customer).toString()
            Box(
                modifier = Modifier
                    .weight(dictWeight[atr.name]?: 10f)
                    .height(56.dp)
                    .border(width = 1.dp, Color.Gray),
                contentAlignment = Alignment.Center) {
                Text(
                    text = value,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun Filters(component: GetCustomersComponent){
    val attributeNames = CustomerResponse::class.declaredMemberProperties.sortedBy { CustomerResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }

    Column {
        Text(modifier = Modifier.padding(bottom = 16.dp), text ="Фильтрация", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        for (attribute in attributeNames.slice(1..5)) {
            TextField(
                value = when (attribute.name) {
                    "first_name" -> component.firstName.value ?: ""
                    "last_name" -> component.lastName.value ?: ""
                    "email" -> component.email.value ?: ""
                    "phone_number" -> component.phone.value ?: ""
                    "driver_license" -> component.license.value ?: ""
                    else -> ""
                },
                onValueChange = {
                    when (attribute.name) {
                        "first_name" -> component.firstName.value = it
                        "last_name" -> component.lastName.value = it
                        "email" -> component.email.value = it
                        "phone_number" -> component.phone.value = it
                        "driver_license" -> component.license.value = it
                    }
                },
                label = { Text(attribute.name) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        ThreeStateSelector(component.isBanned, mapOf(
            "Без выбора" to null,
            "Активен" to false,
            "Заблокирован" to true))
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

@Composable
fun ThreeStateSelector(selectedState: MutableState<Boolean?>, data: Map<String, Boolean?>) {
    Row (verticalAlignment = Alignment.CenterVertically){
        Text("Выберите состояние:")

        for (key in data.keys) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedState.value == data[key],
                    onClick = { selectedState.value = data[key] }
                )
                Text(data[key].toString())
            }
        }
    }
}