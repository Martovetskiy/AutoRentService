package ui.rental

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.rentals.RentalResponse
import components.rental.GetRentalsComponent
import resources.icons.FilterSvgrepoCom
import widgets.PopupNotification
import widgets.ShimmerEffect
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Composable
fun GetRentalsScreen(component: GetRentalsComponent){

    var showFilter by remember { component.showFilter }

    val attributeNames = RentalResponse::class.declaredMemberProperties.sortedBy { RentalResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    val dictWeight: Map<String, Float> = mapOf(
        "rentalId" to 1f,
        "customerId" to 1f,
        "carId" to 1f,
        "startDate" to 3f,
        "endDate" to 3f,
        "totalPrice" to 3f,
        "createAt" to 3f
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
                items(component.sortedRentals.value) { rental ->
                    RentalRow(rental, dictWeight, attributeNames)
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
fun RentalRow(rental: RentalResponse, dictWeight: Map<String, Float>, attributeNames: List<KProperty1<RentalResponse, *>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { /* Обработка нажатия */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(attributeNames.size){i ->
            val atr = attributeNames[i]
            val value = atr.get(rental).toString()
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
private fun Filters(component: GetRentalsComponent){
    val attributeNames = RentalResponse::class.declaredMemberProperties.sortedBy { RentalResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }

    Column {
        Text(modifier = Modifier.padding(bottom = 16.dp), text ="Фильтрация", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        for (attribute in attributeNames.slice(1..5)) {
            TextField(
                value = when (attribute.name) {
                    "customerId" -> component.customerID.value ?: ""
                    "carId" -> component.carID.value ?: ""
                    "startDate" -> component.startDate.value ?: ""
                    "endDate" -> component.endDate.value ?: ""
                    "totalPrice" -> component.totalPrice.value ?: ""
                    else -> ""
                },
                onValueChange = {
                    when (attribute.name) {
                        "customerId" -> component.customerID.value = it
                        "carId" -> component.carID.value = it
                        "startDate" -> component.startDate.value = it
                        "endDate" -> component.endDate.value = it
                        "totalPrice" -> component.totalPrice.value = it
                    }
                },
                label = { Text(attribute.name) },
                modifier = Modifier.fillMaxWidth()
            )
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
