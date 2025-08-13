package com.xasdify.pocketflow.core.presentation.navigation.root

import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceComponent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeComponent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileComponent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionComponent


sealed interface RootChild {
    class Home(val component: HomeComponent) : RootChild
    class Transaction(val component: TransactionComponent) : RootChild
    class Finance(val component: FinanceComponent) : RootChild
    class Analytics(val component: AnalyticsComponent) : RootChild
    class Profile(val component: ProfileComponent) : RootChild
}