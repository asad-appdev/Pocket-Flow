package com.xasdify.pocketflow.core.presentation.navigation.home

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.home.presentation.HomeScreen

@Composable
fun HomeContent(component: HomeComponent) {

    Children(stack = component.childStack) { child ->

        when (val c = child.instance) {
            is HomeChild.DashBoard -> HomeScreen(c.component)
        }

    }


}