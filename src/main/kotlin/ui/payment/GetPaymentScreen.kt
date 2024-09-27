package ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.payments.PaymentResponse
import components.payment.GetPaymentComponent
import resources.icons.DollarSvgrepoCom
import widgets.PopupNotification
import java.time.OffsetDateTime

@Composable
fun GetPaymentScreen(component: GetPaymentComponent) {
    Box(contentAlignment = Alignment.Center) {
        Column {
            SearchWidget({ component.request2Get() }, component.id)
            if (component.payment.value != null) {
                EditPaymentScreen(component)
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
private fun EditPaymentScreen(component: GetPaymentComponent) {
    // Состояния для редактируемых данных платежа
    val paymentId = component.payment.value!!.paymentId
    val rentalId = remember { mutableStateOf(component.payment.value!!.rentalId.toString()) }
    val amount = remember { mutableStateOf(component.payment.value!!.amount.toString()) }
    val paymentMethod = remember { mutableStateOf(component.payment.value!!.paymentMethod) }
    val paymentDate = remember { mutableStateOf(component.payment.value!!.paymentDate.toString()) }

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
                imageVector = DollarSvgrepoCom, // Замените на ресурс вашего изображения профиля
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )
            Row(modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    text = if (component.isEdit.value) "Редактировать платеж" else "Карточка платежа",
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
                // Поля ввода для rentalId, amount, paymentMethod и paymentDate
                TextField(
                    value = rentalId.value,
                    onValueChange = { rentalId.value = it },
                    label = { Text("ID Проката") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = amount.value,
                    onValueChange = { amount.value = it },
                    label = { Text("Сумма платежа") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = paymentMethod.value,
                    onValueChange = { paymentMethod.value = it },
                    label = { Text("Метод платежа") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                TextField(
                    value = paymentDate.value,
                    onValueChange = { paymentDate.value = it },
                    label = { Text("Дата платежа") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Кнопка для обновления данных о платеже
                Button(
                    onClick = {
                        // Обновление объекта PaymentResponse и вызов функции обратного вызова
                        component.paymentBuf.value = PaymentResponse(
                            paymentId = paymentId,
                            rentalId = rentalId.value.toLong(), // Преобразование строки в Long
                            amount = amount.value.toDouble(), // Преобразование строки в Double
                            paymentMethod = paymentMethod.value,
                            paymentDate = OffsetDateTime.parse(paymentDate.value),
                            createAt = component.paymentBuf.value!!.createAt
                        )
                        component.request2Put()
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Text(text = "Обновить платеж")
                }

                // Кнопка для удаления платежа
                Button(
                    onClick = {
                        component.request2Delete()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Удалить платеж", color = Color.White)
                }

            } else {
                // Режим просмотра
                Text(text = "ID Платежа: ${component.payment.value!!.paymentId}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "ID Проката: ${component.payment.value!!.rentalId}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Сумма платежа: ${component.payment.value!!.amount}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Метод платежа: ${component.payment.value!!.paymentMethod}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Дата платежа: ${component.payment.value!!.paymentDate}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text(text = "Создано: ${component.payment.value!!.createAt}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}

