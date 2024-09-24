import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import icons.Close
import icons.Compress
import icons.Expand
import icons.Minimize
import navigation.DecomposeNav
import resources.icons.CarSvgrepoCom
import ui.HomeScreen
import ui.car.GetCarScreen
import ui.car.GetCarsScreen
import ui.car.PostCarScreen
import ui.customer.GetCustomerScreen
import ui.customer.GetCustomersScreen
import ui.customer.PostCustomerScreen
import ui.payment.GetPaymentScreen
import ui.payment.GetPaymentsScreen
import ui.payment.PostPaymentScreen
import ui.rental.GetRentalScreen
import ui.rental.GetRentalsScreen
import ui.rental.PostRentalScreen
import ui.theme.gradientBackground

@Composable
fun App(root: DecomposeNav) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row (modifier = Modifier
                .fillMaxWidth(0.2f)
                .fillMaxHeight()
                .padding(end = 8.dp)
                .background(color = Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))) {
                Column(modifier = Modifier.padding(16.dp).verticalScroll(scrollState)) {
                    // Группа Cars
                    Text(text = "Пользователи", fontWeight = FontWeight.Bold, color = Color.White)
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetCustomersScreen)
                    }) {
                        Text("Получить всех пользователей")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetCustomerScreen)
                    }) {
                        Text("Карточка пользователя (ID)")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.PostCustomerScreen)
                    }) {
                        Text("Добавить пользователя")
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Пробел между группами

                    // Группа Customers
                    Text(text = "Автомобили", fontWeight = FontWeight.Bold, color = Color.White)
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetCarsScreen)
                    }) {
                        Text("Получить все авто")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetCarScreen)
                    }) {
                        Text("Карточка авто (ID)")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.PostCarScreen)
                    }) {
                        Text("Добавить авто")
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Пробел между группами

                    // Группа Payments
                    Text(text = "Аренда", fontWeight = FontWeight.Bold, color = Color.White)
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetRentalsScreen)
                    }) {
                        Text("Получить все аренды")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetRentalScreen)
                    }) {
                        Text("Карточка аренды (ID)")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.PostRentalScreen)
                    }) {
                        Text("Добавить аренду")
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Пробел между группами

                    // Группа Rentals
                    Text(text = "Платежи", fontWeight = FontWeight.Bold, color = Color.White)
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetPaymentsScreen)
                    }) {
                        Text("Получить все платежи")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.GetPaymentScreen)
                    }) {
                        Text("Карточка платежа (ID)")
                    }
                    Button(onClick = {
                        root._navigation.pop()
                        root._navigation.pushNew(DecomposeNav.Configuration.PostPaymentScreen)
                    }) {
                        Text("Добавить платеж")
                    }

                }
            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 8.dp,)
                .background(color = Color(0xFFEFEFEF), shape = RoundedCornerShape(16.dp))) {
                Children(
                    stack = childStack
                )
                { child ->
                    when (val instance = child.instance) {
                        is DecomposeNav.Child.HomeScreen -> HomeScreen(instance.component)

                        is DecomposeNav.Child.GetCustomersScreen -> GetCustomersScreen(instance.component)
                        is DecomposeNav.Child.GetCustomerScreen -> GetCustomerScreen(instance.component)
                        is DecomposeNav.Child.PostCustomerScreen -> PostCustomerScreen(instance.component)

                        is DecomposeNav.Child.GetCarsScreen -> GetCarsScreen(instance.component)
                        is DecomposeNav.Child.GetCarScreen -> GetCarScreen(instance.component)
                        is DecomposeNav.Child.PostCarScreen -> PostCarScreen(instance.component)

                        is DecomposeNav.Child.GetRentalsScreen -> GetRentalsScreen(instance.component)
                        is DecomposeNav.Child.GetRentalScreen -> GetRentalScreen(instance.component)
                        is DecomposeNav.Child.PostRentalScreen -> PostRentalScreen(instance.component)

                        is DecomposeNav.Child.GetPaymentsScreen -> GetPaymentsScreen(instance.component)
                        is DecomposeNav.Child.GetPaymentScreen -> GetPaymentScreen(instance.component)
                        is DecomposeNav.Child.PostPaymentScreen -> PostPaymentScreen(instance.component)
                    }
                }
            }
        }
    }
}

fun main() = application {
    val root = remember {
        DecomposeNav(DefaultComponentContext(LifecycleRegistry()))
    }

    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1635.dp, 920.dp),

        )
    Window(
        state = state,
        resizable = true,
        onCloseRequest = ::exitApplication,
        undecorated = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF263545)),
            contentAlignment = Alignment.Center
        )
        {
            Box(modifier = Modifier
                .gradientBackground(
                    listOf(Color.Green.copy(alpha = 0.1f), Color.Transparent, Color.Transparent),
                    angle = -96f
                )
                .align(Alignment.TopEnd)
                .width(500.dp)
                .height(150.dp)
            )

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(state.size.height.value.toInt() / 20) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        repeat(state.size.width.value.toInt() / 20)
                        {
                            Box(
                                modifier = Modifier
                                    .padding(9.dp)
                                    .size(2.dp)
                                    .background(
                                        color = Color.Gray.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }

            Column (modifier = Modifier.fillMaxSize()) {
                AppBar(
                    state = state,
                    onClose = ::exitApplication
                )
                App(root = root)
            }
        }

    }
}

@Composable
fun WindowScope.AppBar(
    modifier: Modifier = Modifier,
    state: WindowState,
    onClose: () -> Unit,
    onMinimize: () -> Unit = { state.isMinimized = state.isMinimized.not() },
    onMaximize: () -> Unit = {
        state.placement = if (state.placement == WindowPlacement.Maximized)
            WindowPlacement.Floating else WindowPlacement.Maximized
    },
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    )
    {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Spacer(modifier=Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = CarSvgrepoCom,
                contentDescription = "AppIcon",
                tint = Color.Yellow

            )
            Spacer(modifier=Modifier.width(8.dp))
            Text(
                text = "Сервис Аренды Авто (Админ)",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        WindowDraggableArea {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            )
            {
                IconButton(onClick = onMinimize) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Minimize,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                val isFloating = state.placement == WindowPlacement.Floating
                IconButton(onClick = onMaximize) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector =  if (isFloating) Expand else Compress ,
                        contentDescription = null,
                        tint = Color.White)
                }
                IconButton(onClick = onClose) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}