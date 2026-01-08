package com.xasdify.pocketflow.onBoarding.presentation.landing

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xasdify.pocketflow.core.presentation.navigation.root.LandingComponent
import com.xasdify.pocketflow.ui.theme.GradientEnd
import com.xasdify.pocketflow.ui.theme.GradientStart
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(component: LandingComponent) {
    // Animation for logo scale
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Auto-navigate after delay
    LaunchedEffect(Unit) {
        delay(2500) // 2.5 seconds
        // TODO: Navigate to next screen
        // component.onNavigateToAuth() or component.onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo Icon with animation
            Icon(
                imageVector = Icons.Default.AccountBalance,
                contentDescription = "Pocket Flow Logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale),
                tint = androidx.compose.ui.graphics.Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = "Pocket Flow",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Your Personal Finance Manager",
                fontSize = 16.sp,
                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = androidx.compose.ui.graphics.Color.White,
                strokeWidth = 3.dp
            )
        }

        // Version text at bottom
        Text(
            text = "Version 1.0.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            fontSize = 12.sp,
            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
        )
    }
}