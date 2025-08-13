package com.xasdify.pocketflow.core.presentation.navigation.home

sealed interface HomeChild {
    class DashBoard(val component: HomeScreenComponent) : HomeChild
}