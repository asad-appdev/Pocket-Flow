package com.xasdify.pocketflow.core.presentation.navigation.analytics

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value

class AnalyticsComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<AnalyticsRoute>()

    val childStack: Value<ChildStack<AnalyticsRoute, AnalyticsChild>> =
        childStack(
            source = navigation,
            serializer = AnalyticsRoute.serializer(),
            initialConfiguration = AnalyticsRoute.Dashboard,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: AnalyticsRoute, context: ComponentContext): AnalyticsChild {
        return when (route) {
            is AnalyticsRoute.Dashboard -> AnalyticsChild.Dashboard(
                AnalyticsDashBoardComponent(
                    context = context,
                    onNavigateToReport = { text ->
                        navigation.pushNew(AnalyticsRoute.Report(text))
                    })
            )

            is AnalyticsRoute.Report -> AnalyticsChild.Report(
                AnalyticsReportComponent(
                    text = route.text,
                    context = context
                ) {
                    navigation.pop()
                }
            )
        }
    }


}





