package ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.payments.PaymentRequest
import components.payment.PostPaymentComponent
import resources.icons.DollarSvgrepoCom
import widgets.PopupNotification

@Composable
fun PostPaymentScreen(component: PostPaymentComponent) {
    Box(contentAlignment = Alignment.Center) {
        SurveyForm({component.request2Post()}, component.payment)
        PopupNotification(component.showPopup, component.textPopup.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyForm(onPaymentAdded: () -> Unit, payment: MutableState<PaymentRequest?>) {
    var rentalId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var step by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }

    val paymentMethods = setOf("card", "cash", "gift_card")

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
                imageVector = DollarSvgrepoCom, // Замените на ресурс вашего изображения профиля
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )

            Text(
                text = "Добавить платёж",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )

            // Поля ввода
            TextField(
                value = rentalId,
                onValueChange = { rentalId = it },
                label = { Text("ID аренды") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (rentalId.toLongOrNull() != null) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (rentalId.toLongOrNull() != null) Color.Green else Color.Red
                )
            )

            TextField(
                value = step,
                onValueChange = { step = it },
                label = { Text("Шаг") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (step.toIntOrNull() != null) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (step.toIntOrNull() != null) Color.Green else Color.Red
                )
            )

            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Сумма") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (amount.toDoubleOrNull() != null) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (amount.toDoubleOrNull() != null) Color.Green else Color.Red
                )
            )

            TextField(
                value = paymentMethod,
                onValueChange = { paymentMethod = it },
                label = { Text("Метод платежа (card/cash/gift_card)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (paymentMethod in paymentMethods) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (paymentMethod in paymentMethods) Color.Green else Color.Red
                )
            )

            // Кнопка добавления пользователя
            Button(
                enabled = rentalId.toLongOrNull() != null
                        && step.toIntOrNull() != null
                        && amount.toDoubleOrNull() != null
                        && paymentMethod in paymentMethods,
                onClick = {
                    val newPayment = PaymentRequest(
                        rentalId = rentalId.toLong(),
                        amount = amount.toDouble(),
                        step = step.toInt(),
                        paymentMethod = paymentMethod
                    )
                    payment.value = newPayment
                    onPaymentAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Добавить ренту")
            }
        }
    }
}