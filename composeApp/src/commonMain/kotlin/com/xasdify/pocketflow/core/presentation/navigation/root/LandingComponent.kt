package com.xasdify.pocketflow.core.presentation.navigation.root

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LandingComponent(val context: ComponentContext, onFinish: (Boolean) -> Unit) :
    ComponentContext by context {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        onFinish(false)
        scope.launch {
            delay(3000)

        }
    }

    fun onDestroy() {
        scope.cancel()
    }
}