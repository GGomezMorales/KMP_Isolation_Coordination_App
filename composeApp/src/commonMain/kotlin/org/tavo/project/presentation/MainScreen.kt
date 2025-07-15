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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.adapter.GradientButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    var visible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        visible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Isolation Coordination Method",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.inversePrimary,
                            MaterialTheme.colorScheme.primaryContainer
                        ),
                        start = Offset.Zero,
                        end = Offset(x = Float.POSITIVE_INFINITY, y = Float.POSITIVE_INFINITY)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)) +
                        slideInHorizontally(
                            initialOffsetX = { -it / 2 },
                            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                        ) +
                        scaleIn(
                            initialScale = 0.8f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                        slideOutHorizontally(
                            targetOffsetX = { it / 2 },
                            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                        ) +
                        scaleOut(
                            targetScale = 0.9f,
                            animationSpec = tween(durationMillis = 300)
                        )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .animateContentSize(animationSpec = tween(500)),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GradientButton(
                        text = "IEC 60071-2",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        onClick = {
                            visible = false
                            coroutineScope.launch {
                                delay(300)
                                navController.navigate(Screen.ItemDetail("123"))
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    GradientButton(
                        text = "Conventional method",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        onClick = {
                            visible = false
                            coroutineScope.launch {
                                delay(300)
                                navController.navigate(Screen.Conventional)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
        }
    }
}
