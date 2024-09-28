package ui.payment

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
import api.payments.PaymentResponse
import components.payment.GetPaymentsComponent
import resources.icons.FilterSvgrepoCom
import widgets.PopupNotification
import widgets.ShimmerEffect
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Composable
fun GetPaymentsScreen(component: GetPaymentsComponent){

    var showFilter by remember { component.showFilter }

    val attributeNames = PaymentResponse::class.declaredMemberProperties.sortedBy { PaymentResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    val dictWeight: Map<String, Float> = mapOf(
        "paymentId" to 1f,
        "rentalId" to 1f,
        "amount" to 2f,
        "step" to 2f,
        "paymentDate" to 3f,
        "paymentMethod" to 2f,
        "createAt" to 3f
    )
    if (component.isLoad.value)
    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Box(modifier = Modifier.fillMaxWidth()) {
                if (showFilter) Filters(component)
                Row(modifier = Modifier.clickable { showFilter = !showFilter }.align(Alignment.TopEnd) ,verticalAlignment = Alignment.CenterVertically){
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
                items(component.sortedPayments.value) { payment ->
                    PaymentRow(payment, dictWeight, attributeNames)
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
fun PaymentRow(payment: PaymentResponse, dictWeight: Map<String, Float>, attributeNames: List<KProperty1<PaymentResponse, *>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { /* Обработка нажатия */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(attributeNames.size){i ->
            val atr = attributeNames[i]
            val value = atr.get(payment).toString()
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
private fun Filters(component: GetPaymentsComponent){
    val attributeNames = PaymentResponse::class.declaredMemberProperties.sortedBy { PaymentResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }

    Column {
        Text(modifier = Modifier.padding(bottom = 16.dp), text ="Фильтрация", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        for (attribute in attributeNames.slice(1..5)) {
            TextField(
                value = when (attribute.name) {
                    "rentalId" -> component.rentalId.value ?: ""
                    "amount" -> component.amount.value ?: ""
                    "step" -> component.step.value ?: ""
                    "paymentDate" -> component.paymentDate.value ?: ""
                    else -> ""
                },
                onValueChange = {
                    when (attribute.name) {
                        "rentalId" -> component.rentalId.value = it
                        "amount" -> component.amount.value  = it
                        "step" -> component.step.value  = it
                        "paymentDate" -> component.paymentDate.value  = it
                    }
                },
                label = { Text(attribute.name) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Метод оплаты: ")
            for (key in listOf("card", "cash", "gift_card")) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.paymentMethod.value == key,
                        onClick = { component.paymentMethod.value = key }
                    )
                    Text(key)
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
                component.request2Data()}
        ){
            Text("Применить")
        }
    }
}