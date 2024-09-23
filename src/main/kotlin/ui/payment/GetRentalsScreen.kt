package ui.payment

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import api.payments.PaymentResponse
import components.payment.GetPaymentsComponent
import widgets.PopupNotification
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Composable
fun GetPaymentsScreen(component: GetPaymentsComponent){

    var expanded by remember { mutableStateOf(false) }

    val attributeNames = PaymentResponse::class.declaredMemberProperties.sortedBy { PaymentResponse::class.java.declaredFields.withIndex().associate { it1 -> it1.value.name to it1.index }[it.name] }
    val dictWeight: Map<String, Float> = mapOf(
        "payment_id" to 1f,
        "rental_id" to 1f,
        "amount" to 2f,
        "step" to 2f,
        "payment_date" to 3f,
        "payment_method" to 2f,
        "create_at" to 3f
    )

    Box(contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        component.ascending.value = !component.ascending.value
                        component.sort()
                    },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(text = if (component.ascending.value) "Сортировать по возрастанию" else "Сортировать по убыванию")
                }
                IconButton(
                    onClick = {
                        component.request2Data()
                    }
                )
                {
                    Icon(Icons.Default.Refresh, contentDescription = "Обновить", tint = Color.Black)
                }
            }

            Box {
                Text(
                    text = "Сортировать по: ${component.selectedAttribute.value}",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    attributeNames.forEach { attribute ->
                        DropdownMenuItem(
                            onClick = {
                                component.selectedAttribute.value = attribute.name
                                expanded = false
                                component.sort()
                            }
                        )
                        {
                            Text(text = attribute.name)
                        }
                    }
                }
            }
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
