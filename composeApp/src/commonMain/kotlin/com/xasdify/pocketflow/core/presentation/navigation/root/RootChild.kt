package com.xasdify.pocketflow.core.presentation.navigation.root

import com.xasdify.pocketflow.core.presentation.navigation.main.MainComponent

sealed interface RootChild {

    class Landing(val component: LandingComponent) : RootChild
    class Auth(val component: AuthScreenComponent) : RootChild
    class Main(val component: MainComponent) : RootChild

}