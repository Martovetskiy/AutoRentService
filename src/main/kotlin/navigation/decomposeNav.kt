package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import components.HomeScreenComponent
import components.car.GetCarComponent
import components.car.GetCarsComponent
import components.car.PostCarComponent
import components.customer.GetCustomerComponent
import components.customer.GetCustomersComponent
import components.customer.PostCustomerComponent
import components.payment.GetPaymentComponent
import components.payment.GetPaymentsComponent
import components.payment.PostPaymentComponent
import components.rental.GetRentalComponent
import components.rental.GetRentalsComponent
import components.rental.PostRentalComponent
import kotlinx.serialization.Serializable

class DecomposeNav(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val _navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = _navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )


    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.HomeScreen -> Child.HomeScreen(
                component = HomeScreenComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetCustomersScreen -> Child.GetCustomersScreen(
                component = GetCustomersComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetCustomerScreen -> Child.GetCustomerScreen(
                component = GetCustomerComponent(
                    componentContext = context,
                )
            )
            is Configuration.PostCustomerScreen -> Child.PostCustomerScreen(
                component = PostCustomerComponent(
                    componentContext = context,
                )
            )
            // New Car Screens
            is Configuration.PostCarScreen -> Child.PostCarScreen(
                component = PostCarComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetCarsScreen -> Child.GetCarsScreen(
                component = GetCarsComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetCarScreen -> Child.GetCarScreen(
                component = GetCarComponent(
                    componentContext = context,
                )
            )
            is Configuration.PostRentalScreen -> Child.PostRentalScreen(
                component = PostRentalComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetRentalsScreen -> Child.GetRentalsScreen(
                component = GetRentalsComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetRentalScreen -> Child.GetRentalScreen(
                component = GetRentalComponent(
                    componentContext = context,
                )
            )
            is Configuration.PostPaymentScreen -> Child.PostPaymentScreen(
                component = PostPaymentComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetPaymentsScreen -> Child.GetPaymentsScreen(
                component = GetPaymentsComponent(
                    componentContext = context,
                )
            )
            is Configuration.GetPaymentScreen -> Child.GetPaymentScreen(
                component = GetPaymentComponent(
                    componentContext = context,
                )
            )
        }
    }

    sealed class Child {
        data class HomeScreen(val component: HomeScreenComponent) : Child()

        data class GetCustomersScreen(val component: GetCustomersComponent) : Child()
        data class GetCustomerScreen(val component: GetCustomerComponent) : Child()
        data class PostCustomerScreen(val component: PostCustomerComponent) : Child()
        // New Car Child Screens
        data class PostCarScreen(val component: PostCarComponent) : Child()
        data class GetCarsScreen(val component: GetCarsComponent) : Child()
        data class GetCarScreen(val component: GetCarComponent) : Child()

        data class PostRentalScreen(val component: PostRentalComponent) : Child()
        data class GetRentalsScreen(val component: GetRentalsComponent) : Child()
        data class GetRentalScreen(val component: GetRentalComponent) : Child()

        data class PostPaymentScreen(val component: PostPaymentComponent) : Child()
        data class GetPaymentsScreen(val component: GetPaymentsComponent) : Child()
        data class GetPaymentScreen(val component: GetPaymentComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object HomeScreen : Configuration()

        @Serializable
        data object GetCustomersScreen : Configuration()

        @Serializable
        data object GetCustomerScreen : Configuration()

        @Serializable
        data object PostCustomerScreen : Configuration()

        // New Car Screen Configurations
        @Serializable
        data object PostCarScreen : Configuration()

        @Serializable
        data object GetCarsScreen : Configuration()

        @Serializable
        data object GetCarScreen : Configuration()

        @Serializable
        data object PostRentalScreen : Configuration()

        @Serializable
        data object GetRentalsScreen : Configuration()

        @Serializable
        data object GetRentalScreen : Configuration()

        @Serializable
        data object PostPaymentScreen : Configuration()

        @Serializable
        data object GetPaymentsScreen : Configuration()

        @Serializable
        data object GetPaymentScreen : Configuration()
    }
}