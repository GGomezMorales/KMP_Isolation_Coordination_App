package org.tavo.project.presentation.screens.conventional.sections

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import org.tavo.project.domain.usecase.movs.*
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel
import org.tavo.project.presentation.screens.conventional.ConventionalUiState
import kotlin.math.roundToInt

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SetupScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    computeVr1: ComputeVr1UseCase = koinInject(),
    computeVr2: ComputeVr2UseCase = koinInject(),
    selectVr: SelectVrUseCase = koinInject(),
    computeMargin: ComputeSafetyMarginUseCase = koinInject(),
    computeRatedSafety: ComputeRatedMarginVoltageUseCase = koinInject()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    var visible by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    var resultsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(200)
        visible = true
    }

    LaunchedEffect(state.surgeArrester) {
        if (state.surgeArrester != null) {
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
                                            colors = listOf(ElectricBlue, SteelBlue)
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "1",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Text(
                                text = "Setup Parameters",
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
                    .offset(x = (-60).dp, y = 120.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                ElectricBlue.copy(alpha = 0.08f),
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
                                            colors = listOf(ElectricBlue, SteelBlue)
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "System Parameters",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                ),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Configure initial system parameters for surge arrester calculations",
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
                                    dampingRatio = Spring.DampingRatioMediumBouncy
                                )
                            )
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
                                                colors = listOf(ElectricBlue, SteelBlue)
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Input,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = "Input Parameters",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            val inputGroups = listOf(
                                InputGroup(
                                    title = "Voltage Parameters",
                                    icon = Icons.Default.ElectricalServices,
                                    color = ElectricBlue,
                                    inputs = listOf(
                                        InputField(
                                            "Nominal Voltage",
                                            state.nominalVoltage,
                                            "kV"
                                        ) { viewModel.onNominalVoltageChange(it) },
                                        InputField(
                                            "Max. Voltage",
                                            state.maxVoltage,
                                            "kV"
                                        ) { viewModel.onMaxVoltageChange(it) }
                                    )
                                ),
                                InputGroup(
                                    title = "Design Factors",
                                    icon = Icons.Default.Engineering,
                                    color = SuccessGreen,
                                    inputs = listOf(
                                        InputField(
                                            "Landing Factor",
                                            state.landingFactor,
                                            ""
                                        ) { viewModel.onLandingFactorChange(it) },
                                        InputField(
                                            "Design Factor",
                                            state.designFactor,
                                            ""
                                        ) { viewModel.onDesignFactorChange(it) },
                                        InputField("Time Factor", state.timeFactor, "") {
                                            viewModel.onTimeFactorChange(
                                                it
                                            )
                                        }
                                    )
                                ),
                                InputGroup(
                                    title = "System Constants",
                                    icon = Icons.Default.Calculate,
                                    color = AccentOrange,
                                    inputs = listOf(
                                        InputField("Ki Factor", state.ki, "") { viewModel.onKiChange(it) },
                                        InputField("K Factor", state.k, "") { viewModel.onKChange(it) }
                                    )
                                )
                            )

                            inputGroups.forEach { group ->
                                InputGroupCard(group)
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { viewModel.computeSetup() },
                                    enabled = state.isInputValid,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ElectricBlue,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.height(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Calculate,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Compute",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = resultsVisible,
                    enter = fadeIn(tween(800)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy
                                )
                            ),
                    exit = fadeOut(tween(300)) + slideOutVertically()
                ) {
                    ResultsCard(
                        state = state,
                        computeVr1 = computeVr1,
                        computeVr2 = computeVr2,
                        selectVr = selectVr,
                        computeMargin = computeMargin,
                        computeRatedSafety = computeRatedSafety
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
                            onClick = { navController.navigate(Screen.Conventional) },
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
                            onClick = { navController.navigate(Screen.MOVSelection) },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SuccessGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Next")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
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

data class InputField(
    val label: String,
    val value: String,
    val unit: String,
    val onValueChange: (String) -> Unit
)

data class InputGroup(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val inputs: List<InputField>
)

@Composable
private fun InputGroupCard(group: InputGroup) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = group.color.copy(alpha = 0.05f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            group.color.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(
                            color = group.color,
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = group.icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = group.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = group.color
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            group.inputs.forEach { input ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = input.label,
                        modifier = Modifier.weight(1.2f),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = DeepNavy
                        )
                    )

                    OutlinedTextField(
                        value = input.value,
                        onValueChange = input.onValueChange,
                        modifier = Modifier.weight(0.8f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = group.color,
                            focusedLabelColor = group.color,
                            unfocusedBorderColor = SlateGray.copy(alpha = 0.3f)
                        )
                    )

                    Text(
                        text = input.unit,
                        modifier = Modifier.weight(0.2f),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = SlateGray
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultsCard(
    state: ConventionalUiState,
    computeVr1: ComputeVr1UseCase,
    computeVr2: ComputeVr2UseCase,
    selectVr: SelectVrUseCase,
    computeMargin: ComputeSafetyMarginUseCase,
    computeRatedSafety: ComputeRatedMarginVoltageUseCase
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
                                colors = listOf(SuccessGreen, Color(0xFF047857))
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Calculation Results",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            state.surgeArrester?.let { surgeArrester ->
                val mcov: Double = surgeArrester.mcov
                val tov: Double = surgeArrester.tov
                val vr1: Double = computeVr1(mcov, state.designFactor.toDouble())
                val vr2: Double = computeVr2(tov, state.timeFactor.toDouble())
                val vr: Int = selectVr(vr1, vr2)
                val margin: Double = computeMargin(state.nominalVoltage.toDouble())
                val ratedSafety: Double = computeRatedSafety(vr, margin)

                val results = listOf(
                    ResultItem("MCOV", "${mcov}", "kV", Icons.Default.ElectricalServices),
                    ResultItem("TOV", "${tov}", "kV", Icons.Default.ElectricalServices),
                    ResultItem("Vr1", "${vr1}", "kV", Icons.Default.Calculate),
                    ResultItem("Vr2", "${vr2}", "kV", Icons.Default.Calculate),
                    ResultItem("Vr", "${vr}", "kV", Icons.Default.Bolt),
                    ResultItem(
                        "Suggested Voltage",
                        "${ratedSafety.roundToInt()}",
                        "kV",
                        Icons.Default.Recommend,
                        isHighlight = true
                    )
                )

                results.forEach { result ->
                    ResultRow(result)
                    if (result != results.last()) {
                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = LightGray
                        )
                    }
                }
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
                        text = "Enter all parameters and press \"Compute\" to see results",
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

data class ResultItem(
    val label: String,
    val value: String,
    val unit: String,
    val icon: ImageVector,
    val isHighlight: Boolean = false
)

@Composable
private fun ResultRow(result: ResultItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = if (result.isHighlight) AccentOrange else ElectricBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = result.icon,
                    contentDescription = null,
                    tint = if (result.isHighlight) Color.White else ElectricBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = result.label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (result.isHighlight) FontWeight.Bold else FontWeight.Medium,
                    color = if (result.isHighlight) AccentOrange else DeepNavy
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = result.value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (result.isHighlight) AccentOrange else DeepNavy
                )
            )
            Text(
                text = result.unit,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SlateGray
                )
            )
        }
    }
}

private fun Double?.orZero() = this ?: 0.0
