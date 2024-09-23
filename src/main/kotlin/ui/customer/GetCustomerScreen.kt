package ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.cutomers.CustomerResponse
import components.customer.GetCustomerComponent
import widgets.PopupNotification

@Composable
fun GetCustomerScreen(component: GetCustomerComponent){
    Box(contentAlignment = Alignment.Center) {
        Column {
            SearchWidget({ component.request2Get() }, component.id)
            if (component.customer.value != null) {
                EditUserScreen(component)
            }
            else
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text="Введите ID и нажмите Найти", fontSize = 26.sp)
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
private fun EditUserScreen(component: GetCustomerComponent) {
    // Состояния для редактируемых данных
    val firstName = remember { mutableStateOf(component.customer.value!!.first_name) }
    val lastName = remember { mutableStateOf(component.customer.value!!.last_name) }
    val email = remember { mutableStateOf(component.customer.value!!.email) }
    val phoneNumber = remember { mutableStateOf(component.customer.value!!.phone_number) }
    val driverLicense = remember { mutableStateOf(component.customer.value!!.driver_license) }
    val isBanned = remember { mutableStateOf(component.customer.value!!.is_banned) } // состояниe для статуса

    val patternEmail = Regex("""^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""")
    val patternPhone = Regex("""^\+\d{11}$""")
    val patternDriverLicense = Regex("""^\d{4} \d{6}$""")
    // Состояние для режима редактирования (по умолчанию - режим просмотра) // Режим просмотра по умолчанию

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
            Row(modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    text = if (component.isEdit.value) "Редактировать пользователя" else "Карточка пользователя",
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
            if (!component.isDelete.value) {
                Button(
                    onClick = { component.isEdit.value = !component.isEdit.value },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(text = if (component.isEdit.value) "Перейти в режим просмотра" else "Перейти в режим редактирования")
                }
            }

            if (component.isEdit.value) {
                // Поля ввода с предустановленными значениями
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

                // Переключатель для статуса игрока
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = isBanned.value,
                        onCheckedChange = { isBanned.value = it }
                    )
                    Text(text = if (isBanned.value) "Забанен" else "Активен", modifier = Modifier.padding(start = 8.dp))
                }

                // Кнопка для обновления пользователя
                Button(
                    enabled = !component.isDelete.value
                            && firstName.value.isNotEmpty()
                            && lastName.value.isNotEmpty()
                            && patternPhone.matches(phoneNumber.value)
                            && patternDriverLicense.matches(driverLicense.value)
                            && patternEmail.matches(email.value),
                    onClick = {
                        // Обновление объекта CustomerResponse и вызов функции обратного вызова
                        component.customerBuf.value = CustomerResponse(
                            customer_id = component.id.value!!,
                            first_name = firstName.value,
                            last_name = lastName.value,
                            email = email.value,
                            phone_number = phoneNumber.value,
                            driver_license = driverLicense.value,
                            is_banned = isBanned.value,
                            create_at = component.customerBuf.value!!.create_at
                        )
                        component.request2Put()
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
            Text(text = "Обновить пользователя")
        }

            // Кнопка для удаления пользователя
            Button(
                enabled = !component.isDelete.value,
                onClick = {
                    component.request2Delete()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red), // Красный цвет для кнопки удаления
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Удалить пользователя", color = Color.White) // Белый текст для кнопки
            }
        } else {
        // Режим просмотра
        Text(text = "Имя: ${component.customer.value!!.first_name}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Фамилия: ${component.customer.value!!.last_name}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Email: ${component.customer.value!!.email}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Телефон: ${component.customer.value!!.phone_number}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Водительские права: ${component.customer.value!!.driver_license}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        Text(text = "Статус: ${if (component.customer.value!!.is_banned) "Забанен" else "Активен"}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))
    }
    }
}
}