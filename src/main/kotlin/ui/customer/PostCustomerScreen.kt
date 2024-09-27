package ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.cutomers.CustomerRequest

import components.customer.PostCustomerComponent
import widgets.PopupNotification

@Composable
fun PostCustomerScreen(component: PostCustomerComponent) {
    Box(contentAlignment = Alignment.Center) {
        AddUserScreen(onUserAdded = { component.request2Post() }, component.customer)
        PopupNotification(component.showPopup, component.textPopup.value)
    }
}

@Composable
fun AddUserScreen(onUserAdded: () -> Unit, customer: MutableState<CustomerRequest?>) {
    // Состояния для ввода данных
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val driverLicense = remember { mutableStateOf("") }

    val patternEmail = Regex("""^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""")
    val patternPhone = Regex("""^\+\d{11}$""")
    val patternDriverLicense = Regex("""^\d{4} \d{6}$""")

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
                imageVector = Icons.Filled.Person, // Замените на ресурс вашего изображения профиля
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape) // Круглая форма
            )

            Text(
                text = "Добавить пользователя",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )

            // Поля ввода
            TextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (firstName.value.isNotEmpty()) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (firstName.value.isNotEmpty()) Color.Green else Color.Red,
                )
            )

            TextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = if (lastName.value.isNotEmpty()) Color.Green else Color.Red,
                    unfocusedIndicatorColor = if (lastName.value.isNotEmpty()) Color.Green else Color.Red,
                )
            )

            RegexInput(email, patternEmail, "Email")
            RegexInput(phoneNumber, patternPhone, "Телефон (+7)")
            RegexInput(driverLicense, patternDriverLicense, "Серия и номер водительского удостоверения (XXXX XXXXXX)")
            // Кнопка добавления пользователя
            Button(
                enabled = firstName.value.isNotEmpty()
                        && lastName.value.isNotEmpty()
                        && patternPhone.matches(phoneNumber.value)
                        && patternDriverLicense.matches(driverLicense.value)
                        && patternEmail.matches(email.value),
                onClick = {
                    // Создание объекта CustomerResponse и вызов функции обратного вызова
                    val newUser = CustomerRequest(
                        firstName = firstName.value,
                        lastName = lastName.value,
                        email = email.value,
                        phoneNumber = phoneNumber.value,
                        driverLicense = driverLicense.value,
                        isBanned = false, // Или ваш логика по умолчанию
                    )
                    customer.value = newUser
                    onUserAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Добавить пользователя")
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
        textStyle = TextStyle(color = if (isValid) Color.Black else Color.Red), // Установка цвета текста
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = if (isValid) Color.Green else Color.Red,
            unfocusedIndicatorColor = if (isValid) Color.Green else Color.Red,
        )
    )
}
