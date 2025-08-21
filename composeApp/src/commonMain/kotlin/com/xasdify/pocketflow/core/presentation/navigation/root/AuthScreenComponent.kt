package com.xasdify.pocketflow.core.presentation.navigation.root

import com.arkivanov.decompose.ComponentContext

class AuthScreenComponent(val context: ComponentContext, val onLogin: () -> Unit) :
    ComponentContext by context {

    private val instanceKeeper = instanceKeeper()
}