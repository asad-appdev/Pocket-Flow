//package com.xasdify.pocketflow.core.presentation.navigation.route
//
//sealed interface RootChild {
//    class Home(val component: HomeComponent) : RootChild
//    class Transaction(val component: TransactionComponent) : RootChild
//    class Finance(val component: FinanceComponent) : RootChild
//    class Analytics(val component: AnalyticsComponent) : RootChild
//    class Profile(val component: ProfileComponent) : RootChild
//}
//
//sealed class RootGraphRoute {
//    object Home : RootGraphRoute()
//    object Transaction : RootGraphRoute()
//    object Finance : RootGraphRoute()
//    object Analytics : RootGraphRoute()
//    object Profile : RootGraphRoute()
//}