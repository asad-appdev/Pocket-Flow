package com.xasdify.pocketflow.core.presentation.navigation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.core.presentation.navigation.main.MainContent
import com.xasdify.pocketflow.onBoarding.presentation.auth.LoginScreen
import com.xasdify.pocketflow.onBoarding.presentation.auth.LoginViewModel
import com.xasdify.pocketflow.onBoarding.presentation.landing.LandingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootContent(root: RootComponent) {
    Children(stack = root.childStack) { child ->
        when (val inst = child.instance) {
            is RootChild.Landing -> {
                LandingScreen(inst.component)
            }

            is RootChild.Auth -> {
                val vm: LoginViewModel = koinViewModel()
                vm.login()
                LoginScreen(inst.component)
            }

            is RootChild.Main -> MainContent(inst.component)
        }
    }
}