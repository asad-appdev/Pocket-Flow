package com.xasdify.pocketflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import com.xasdify.pocketflow.core.presentation.App
import com.xasdify.pocketflow.core.presentation.navigation.main.MainComponent
import com.xasdify.pocketflow.core.presentation.navigation.root.RootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val mainComponent = retainedComponent {
            RootComponent(it)
        }

        setContent {
            App(mainComponent)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}