package org.tavo.project.presentation.screens.conventional.sections

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import org.koin.compose.koinInject
import org.tavo.project.domain.usecase.criteria.IsoCoordinationUseCase
import org.tavo.project.domain.usecase.iso_coord.ComputeBSLUseCase
import org.tavo.project.domain.usecase.iso_coord.TheoreticalBILUseCase
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel
import org.tavo.project.presentation.screens.conventional.ConventionalUiState

private val ElectricBlue = Color(0xFF1E3A8A)
private val SteelBlue = Color(0xFF3B82F6)
private val LightBlue = Color(0xFF60A5FA)
private val ElectricTeal = Color(0xFF0891B2)
private val DeepNavy = Color(0xFF0F172A)
private val SlateGray = Color(0xFF475569)
private val LightGray = Color(0xFFF8FAFC)
private val AccentOrange = Color(0xFFEA580C)
private val SuccessGreen = Color(0xFF059669)
private val WarningAmber = Color(0xFFF59E0B)
private val ErrorRed = Color(0xFFDC2626)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun IsolationCoordinationScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    theoreticalBILUseCase: TheoreticalBILUseCase = koinInject(),
    computeBSLUseCase: ComputeBSLUseCase = koinInject(),
    isoCoordinationUseCase: IsoCoordinationUseCase = koinInject(),
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    var visible by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    var resultsVisible by remember { mutableStateOf(false) }

    val canCompute = state.surgeArrester != null &&
            state.ki.toDoubleOrNull() != null &&
            state.k.toDoubleOrNull() != null &&
            state.bilNormalized.toDoubleOrNull() != null

    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(200)
        visible = true
    }

    LaunchedEffect(state.surgeArrester, state.ki, state.k, state.bilNormalized) {
        if (canCompute) {
            delay(300)
            resultsVisible = true
        } else {
            resultsVisible = false
        }
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
                                Text(
                                    text = "3",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
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
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screen.Conventional) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DeepNavy
                ),
                modifier = Modifier.shadow(8.dp)
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
                    .offset(x = (-50).dp, y = 120.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                AccentOrange.copy(alpha = 0.1f),
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
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
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
                        modifier = Modifier.fillMaxWidth(),
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
                                            colors = listOf(AccentOrange, Color(0xFFDC2626))
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Analytics,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Final Analysis",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                ),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Complete isolation coordination analysis and system classification",
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
                    enter = fadeIn(tween(800, delayMillis = 300))
                ) {
                    ParametersStatusCard(state = state, canCompute = canCompute)
                }

                AnimatedVisibility(
                    visible = resultsVisible,
                    enter = fadeIn(tween(1000)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy
                                )
                            ),
                    exit = fadeOut(tween(300)) + slideOutVertically()
                ) {
                    IsolationResultsCard(
                        state = state,
                        theoreticalBILUseCase = theoreticalBILUseCase,
                        computeBSLUseCase = computeBSLUseCase,
                        isoCoordinationUseCase = isoCoordinationUseCase
                    )
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(800, delayMillis = 600))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.navigate(Screen.MOVSelection) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = SlateGray
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Back")
                        }

                        Button(
                            onClick = { navController.navigate(Screen.Conventional) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SuccessGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Finish")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ParametersStatusCard(
    state: ConventionalUiState,
    canCompute: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    if (canCompute) SuccessGreen else WarningAmber,
                                    if (canCompute) Color(0xFF047857) else Color(0xFFD97706)
                                )
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (canCompute) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Parameters Status",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val debugInfo = listOf(
                "Surge Arrester" to if (state.surgeArrester != null) "✓ Configured" else "✗ Missing",
                "Ki Factor" to if (state.ki.isNotBlank()) "✓ ${state.ki}" else "✗ Missing",
                "K Factor" to if (state.k.isNotBlank()) "✓ ${state.k}" else "✗ Missing",
                "BIL Normalized" to if (state.bilNormalized.isNotBlank()) "✓ ${state.bilNormalized} kV" else "✗ Missing",
                "NPR" to if (state.surgeArrester != null) "✓ ${state.surgeArrester!!.npr} kV" else "✗ Missing",
                "NPM" to if (state.surgeArrester != null) "✓ ${state.surgeArrester!!.npm} kV" else "✗ Missing"
            )

            debugInfo.forEach { (label, value) ->
                val isValid = value.startsWith("✓")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = DeepNavy,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isValid) SuccessGreen else ErrorRed,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (canCompute) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = SuccessGreen.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "✓ All parameters ready - Analysis will appear below",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SuccessGreen,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            } else {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = WarningAmber.copy(alpha = 0.1f)
                    ),
                    border = BorderStroke(1.dp, WarningAmber.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "⚠ Complete all previous steps to see analysis results",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = WarningAmber,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun IsolationResultsCard(
    state: ConventionalUiState,
    theoreticalBILUseCase: TheoreticalBILUseCase,
    computeBSLUseCase: ComputeBSLUseCase,
    isoCoordinationUseCase: IsoCoordinationUseCase
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(ElectricTeal, Color(0xFF0E7490))
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Assessment,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Analysis Results",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            state.surgeArrester?.let { sa ->
                val ki = state.ki.toDoubleOrNull() ?: 0.0
                val k = state.k.toDoubleOrNull() ?: 1.0
                val bilNormalized = state.bilNormalized.toDoubleOrNull() ?: 0.0
                val npr = sa.npr
                val npm = sa.npm

                val bilTeorico = theoreticalBILUseCase(npr, ki)
                val bsl = computeBSLUseCase(bilNormalized, k)
                val isoRatio = isoCoordinationUseCase(bsl, npm)

                InputDataCard(
                    data = mapOf(
                        "NPR" to "$npr kV",
                        "NPM" to "$npm kV",
                        "Ki Factor" to "$ki",
                        "K Factor" to "$k",
                        "BIL Normalized" to "$bilNormalized kV"
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                val calculations = listOf(
                    CalculationResult(
                        title = "Theoretical BIL",
                        formula = "NPR × Ki = $npr × $ki",
                        value = bilTeorico,
                        unit = "kV",
                        icon = Icons.Default.Calculate,
                        color = ElectricBlue
                    ),
                    CalculationResult(
                        title = "BSL (Basic Switching Level)",
                        formula = "BIL Normalized × K = ${String.format("%.2f", bilNormalized)} × $k",
                        value = bsl,
                        unit = "kV",
                        icon = Icons.Default.ElectricalServices,
                        color = ElectricTeal
                    ),
                    CalculationResult(
                        title = "Coordination Ratio",
                        formula = "BSL / NPM = ${String.format("%.2f", bsl)} / $npm",
                        value = isoRatio,
                        unit = "",
                        icon = Icons.Default.CompareArrows,
                        color = AccentOrange
                    )
                )

                calculations.forEach { calc ->
                    CalculationCard(calc)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                val classificationData = getClassificationData(isoRatio)
                ClassificationCard(classificationData)

            } ?: run {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Complete previous steps to see analysis results",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SlateGray
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun InputDataCard(data: Map<String, String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8FAFC)
        ),
        border = BorderStroke(1.dp, SlateGray.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Input Values",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = DeepNavy
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            data.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = key,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SlateGray
                        )
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = DeepNavy,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

data class CalculationResult(
    val title: String,
    val formula: String,
    val value: Double,
    val unit: String,
    val icon: ImageVector,
    val color: Color
)

data class ClassificationData(
    val classification: String,
    val description: String,
    val color: Color,
    val icon: ImageVector
)

@Composable
private fun CalculationCard(calc: CalculationResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = calc.color.copy(alpha = 0.05f)
        ),
        border = BorderStroke(
            1.dp,
            calc.color.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = calc.color,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = calc.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = calc.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = DeepNavy
                        )
                    )
                    Text(
                        text = calc.formula,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SlateGray
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = String.format("%.2f", calc.value),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = calc.color
                    )
                )
                if (calc.unit.isNotEmpty()) {
                    Text(
                        text = calc.unit,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SlateGray
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ClassificationCard(classificationData: ClassificationData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = classificationData.color.copy(alpha = 0.05f)
        ),
        border = BorderStroke(
            2.dp,
            classificationData.color.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = classificationData.color,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = classificationData.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "System Classification",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = SlateGray
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = classificationData.classification,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = classificationData.color
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = classificationData.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SlateGray
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun getClassificationData(isoRatio: Double): ClassificationData {
    return when {
        isoRatio >= 1.5 -> ClassificationData(
            classification = "OPTIMAL",
            description = "System provides excellent protection with high safety margins",
            color = SuccessGreen,
            icon = Icons.Default.CheckCircle
        )

        isoRatio >= 1.0 -> ClassificationData(
            classification = "ACCEPTABLE",
            description = "System meets minimum requirements but may need monitoring",
            color = WarningAmber,
            icon = Icons.Default.Warning
        )

        else -> ClassificationData(
            classification = "INADEQUATE",
            description = "System does not meet protection requirements and needs redesign",
            color = ErrorRed,
            icon = Icons.Default.Error
        )
    }
}

private fun Double?.orZero() = this ?: 0.0
