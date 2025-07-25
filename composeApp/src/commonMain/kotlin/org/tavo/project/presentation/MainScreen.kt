package org.tavo.project.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen

private val ElectricBlue = Color(0xFF1E3A8A)
private val SteelBlue = Color(0xFF3B82F6)
private val LightBlue = Color(0xFF60A5FA)
private val ElectricTeal = Color(0xFF0891B2)
private val DeepNavy = Color(0xFF0F172A)
private val SlateGray = Color(0xFF475569)
private val LightGray = Color(0xFFF8FAFC)
private val AccentOrange = Color(0xFFEA580C)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    var visible by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(200)
        visible = true
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = headerVisible,
                        enter = fadeIn(tween(600)) + slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(AccentOrange, Color(0xFFDC2626))
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ElectricalServices,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Isolation Coordination",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DeepNavy
                ),
                modifier = Modifier
                    .shadow(8.dp)
                    .verticalScroll(rememberScrollState())
            )
        },
        containerColor = LightGray
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            LightGray,
                            Color(0xFFE2E8F0),
                            Color(0xFFCBD5E1)
                        )
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = (-50).dp, y = 100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                LightBlue.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = 250.dp, y = 400.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                ElectricTeal.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(800, delayMillis = 200)) +
                            slideInVertically(
                                initialOffsetY = { -it / 3 },
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(ElectricBlue, SteelBlue)
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Science,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Electrical Engineering Tools",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                ),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Professional calculation methods for power systems",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SlateGray
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(800, delayMillis = 400)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProfessionalSectionButton(
                            title = "IEC 60071-2",
                            subtitle = "International standard method",
                            icon = Icons.Default.Engineering,
                            gradientColors = listOf(ElectricBlue, SteelBlue),
                            onClick = {
                                coroutineScope.launch {
                                    visible = false
                                    headerVisible = false
                                    delay(400)
                                    navController.navigate(Screen.ItemDetail("123"))
                                }
                            }
                        )

                        ProfessionalSectionButton(
                            title = "Conventional Method",
                            subtitle = "Traditional calculation approach",
                            icon = Icons.Default.ElectricalServices,
                            gradientColors = listOf(ElectricTeal, Color(0xFF0E7490)),
                            onClick = {
                                coroutineScope.launch {
                                    visible = false
                                    headerVisible = false
                                    delay(400)
                                    navController.navigate(Screen.Conventional)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfessionalSectionButton(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .shadow(
                elevation = if (isPressed) 2.dp else 6.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.linearGradient(colors = gradientColors),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SlateGray
                    )
                )
            }

            Icon(
                imageVector = Icons.Default.ElectricalServices,
                contentDescription = null,
                tint = gradientColors.first().copy(alpha = 0.6f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
