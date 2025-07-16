package org.tavo.project.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    var visible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Isolation Coordination Method",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(56.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, easing = FastOutSlowInEasing)) +
                        slideInVertically(initialOffsetY = { it / 6 }, animationSpec = tween(500)) +
                        scaleIn(
                            initialScale = 0.95f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
                        ),
                exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 6 }, animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(vertical = 32.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionButton(
                        text = "IEC 60071-2",
                        onClick = {
                            coroutineScope.launch {
                                visible = false
                                delay(300)
                                navController.navigate(Screen.ItemDetail("123"))
                            }
                        }
                    )

                    SectionButton(
                        text = "Conventional Method",
                        onClick = {
                            coroutineScope.launch {
                                visible = false
                                delay(300)
                                navController.navigate(Screen.Conventional)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionButton(
    text: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
        )
    }
}
