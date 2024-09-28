package ui.rental

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.rentals.RentalResponse
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import components.rental.GetRentalsComponent
import navigation.DecomposeNav
import resources.icons.FilterSvgrepoCom
import ui.isValidDate
import widgets.ShimmerEffect
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun GetRentalsScreen(component: GetRentalsComponent, root: DecomposeNav) {
    val associatedMap = mapOf(
        "firstName" to "Имя",
        "email" to "Email",
        "make" to "Марка",
        "model" to "Модель",
        "startDate" to "Начало аренды",
        "endDate" to "Конец аренды",
        "totalPrice" to "Цена за аренду",
        "rentals.createAt" to "Дата создания карточки")
    if (!component.isLoad.value) {
        Column(
            modifier = Modifier.padding(16.dp).background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (component.showFilter.value) Filters(component, associatedMap)
                Row(modifier = Modifier.clickable { component.showFilter.value = !component.showFilter.value }
                    .align(Alignment.TopEnd), verticalAlignment = Alignment.CenterVertically) {
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
            Spacer(modifier = Modifier.height(8.dp))
            RentalViews(data = component.data.value, associationMapRU = associatedMap, root)
        }
    }
    else
    {
        ShimmerEffect(shape = RoundedCornerShape(16.dp)){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text("Загрузка... БЗБЗБЗБЗБ...")
            }
        }
    }
}


@Composable
private fun RentalViews(data: List<RentalResponse>, associationMapRU: Map<String, String>, root: DecomposeNav){
    val fMap = mapOf(
        "firstName" to 1f,
        "email" to 1.5f,
        "make" to 1f,
        "model" to 1f,
        "startDate" to 1.5f,
        "endDate" to 1.5f,
        "rentals.createAt" to 1.5f

    )
    val maxHeight = mutableStateOf(56.dp)

    LazyColumn {
        item {
            Row (modifier = Modifier
                .fillMaxWidth()){
                associationMapRU.keys.forEach { key ->
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minHeight = maxHeight.value)
                            .weight(fMap[key]?: 1f)
                            .border(width = 1.dp, Color.Gray)
                            .heightIn(max = 64.dp)
                            .onGloballyPositioned { coordinates ->
                                if (coordinates.size.height.dp > maxHeight.value) {
                                    println(coordinates.size.height.dp)
                                    maxHeight.value = coordinates.size.height.dp
                                }
                            },
                        contentAlignment = Alignment.Center,

                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = associationMapRU[key] ?: null.toString(),
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                textDirection = TextDirection.Content,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
        items(data){row ->
            Row (modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    root._navigation.pop()
                    root._navigation.pushNew(DecomposeNav.Configuration.GetRentalScreen(row))
                }){
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minHeight = maxHeight.value)
                            .weight(fMap["firstName"]?: 1f)
                            .border(width = 1.dp, Color.Gray)
                            .heightIn(max = 64.dp)
                            .onGloballyPositioned { coordinates ->
                                if (coordinates.size.height.dp > maxHeight.value) {
                                    maxHeight.value = coordinates.size.height.dp
                                }
                            },
                        contentAlignment = Alignment.Center,

                        ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = row.customer?.firstName ?: null.toString(),
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                textDirection = TextDirection.Content,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["email"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.customer?.email ?: null.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["make"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.car?.make ?: null.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["model"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.car?.model ?: null.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["startDate"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["endDate"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["totalPrice"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                println(coordinates.size.height.dp)
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.totalPrice.toString(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = maxHeight.value)
                        .weight(fMap["rentals.createAt"]?: 1f)
                        .border(width = 1.dp, Color.Gray)
                        .heightIn(max = 64.dp)
                        .onGloballyPositioned { coordinates ->
                            if (coordinates.size.height.dp > maxHeight.value) {
                                maxHeight.value = coordinates.size.height.dp
                            }
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = row.startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru"))),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            textDirection = TextDirection.Content,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

                }


            }

    }


@Composable
private fun Filters(component: GetRentalsComponent, associationMapRU: Map<String, String>) {
    val sortDirectionMap = mapOf(
        "ASC" to "По возрастанию",
        "DESC" to "По убыванию"
    )
    Column {
        Text(modifier = Modifier.padding(bottom = 16.dp), text = "Фильтрация", fontWeight = FontWeight.Bold)
        TextField(
            value = component.firstNameF.value?: "",
            onValueChange = { component.firstNameF.value = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
        TextField(
            value = component.emailF.value?: "",
            onValueChange = { component.emailF.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
        TextField(
            value = component.makeF.value?: "",
            onValueChange = { component.makeF.value = it },
            label = { Text("Марка") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
        TextField(
            value = component.modelF.value?: "",
            onValueChange = { component.modelF.value = it },
            label = { Text("Модель") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
      )
        TextField(
            isError = !isValidDate(component.startDateF.value?:"") and !component.startDateF.value.isNullOrEmpty(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Green
            ),
            value = component.startDateF.value ?: "",
            onValueChange = { component.startDateF.value = it },
            label = { Text("Дата начала (ГГГГ-ММ-ДД)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            isError = !isValidDate(component.endDateF.value?:"") and !component.endDateF.value.isNullOrEmpty(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Green
            ),
            value = component.endDateF.value ?: "",
            onValueChange = { component.endDateF.value = it },
            label = { Text("Дата конца (ГГГГ-ММ-ДД)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        TextField(
            value = component.priceF.value?: "",
            onValueChange = { component.priceF.value = it },
            label = { Text("Цена") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )
        TextField(
            isError = !isValidDate(component.createAtF.value?: "") and !component.createAtF.value.isNullOrEmpty(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Green,
                unfocusedIndicatorColor = Color.Green
            ),
            value = component.createAtF.value ?: "",
            onValueChange = { component.createAtF.value = it },
            label = { Text("Дата создания (ГГГГ-ММ-ДД)") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = "Сортировать по столбцу:")
            associationMapRU.keys.forEach {key ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.sortBy.value == key,
                        onClick = { component.sortBy.value = key }
                    )
                    Text(associationMapRU[key]?: "")
                }
            }
        }
        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Сортировать по: ")
            for (key in sortDirectionMap.keys) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = component.sortDirection.value == key,
                        onClick = { component.sortDirection.value = key }
                    )
                    Text(sortDirectionMap[key]!!)
                }
            }

        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.request2Get()
            }
        )
            {
                Text("Применить")
            }


    }
}


