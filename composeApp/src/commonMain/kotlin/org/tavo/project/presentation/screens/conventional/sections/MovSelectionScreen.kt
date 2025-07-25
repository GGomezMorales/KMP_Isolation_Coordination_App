package org.tavo.project.presentation.screens.conventional.sections

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import org.tavo.project.domain.usecase.criteria.ProtectionCapacityUseCase
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel
import org.tavo.project.presentation.screens.conventional.ConventionalUiState

data class VoltageData(
    val Um: Double,
    val lightning_impulse_withstand: List<Int>
)

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
fun MovSelectionScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    protectionCapacity: ProtectionCapacityUseCase = koinInject(),
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    var visible by remember { mutableStateOf(false) }
    var headerVisible by remember { mutableStateOf(false) }
    var resultsVisible by remember { mutableStateOf(false) }

    val nextEnabled = state.surgeArrester != null &&
            state.movRatedVoltage.toDoubleOrNull() != null &&
            state.npm.toDoubleOrNull() != null &&
            state.npr.toDoubleOrNull() != null &&
            state.bilNormalized.toDoubleOrNull() != null

    LaunchedEffect(Unit) {
        delay(100)
        headerVisible = true
        delay(200)
        visible = true
    }

    LaunchedEffect(state.surgeArrester) {
        if (state.surgeArrester != null && state.bilNormalized.isNotBlank()) {
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
                                            colors = listOf(SuccessGreen, Color(0xFF047857))
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "2",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Text(
                                text = "MOV Selection",
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
                    .size(180.dp)
                    .offset(x = (-40).dp, y = 100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                SuccessGreen.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 280.dp, y = 450.dp)
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
                                            colors = listOf(SuccessGreen, Color(0xFF047857))
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Memory,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "MOV Configuration",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = DeepNavy
                                ),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "Configure Metal Oxide Varistor parameters for protection analysis",
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
                    MovInputsCard(state, viewModel)
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
                    MovResultsCard(state, protectionCapacity)
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
                            onClick = { navController.navigate(Screen.Setup) },
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
                            onClick = { navController.navigate(Screen.IsolationCoordination) },
                            enabled = nextEnabled,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (nextEnabled) AccentOrange else SlateGray.copy(alpha = 0.3f),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovInputsCard(
    state: ConventionalUiState,
    viewModel: ConventionalMainViewModel
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
                                colors = listOf(SuccessGreen, Color(0xFF047857))
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
                    text = "MOV Parameters",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            val regularInputs = listOf(
                MovInputField(
                    label = "MOV Rated Voltage",
                    value = state.movRatedVoltage,
                    unit = "kV",
                    icon = Icons.Default.ElectricalServices,
                    description = "Rated voltage of the MOV",
                    onValueChange = { viewModel.onMovRatedVoltageChange(it) }
                ),
                MovInputField(
                    label = "NPM",
                    value = state.npm,
                    unit = "kV",
                    icon = Icons.Default.Power,
                    description = "Nominal protection margin",
                    onValueChange = { viewModel.onNpmChange(it) }
                ),
                MovInputField(
                    label = "NPR",
                    value = state.npr,
                    unit = "kV",
                    icon = Icons.Default.Shield,
                    description = "Nominal protection ratio",
                    onValueChange = { viewModel.onNprChange(it) }
                )
            )

            regularInputs.forEach { input ->
                MovInputRow(input)
                Spacer(modifier = Modifier.height(16.dp))
            }

            NormalizedBilInputRow(
                state = state,
                viewModel = viewModel
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { viewModel.computeMovSelection() },
                    enabled = state.isInputValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SuccessGreen,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NormalizedBilInputRow(
    state: ConventionalUiState,
    viewModel: ConventionalMainViewModel
) {
    var showDropdown by remember { mutableStateOf(false) }

    val availableValues = state.selectedVoltage?.lightning_impulse_withstand ?: emptyList()
    val hasVoltageSelection = state.selectedVoltage != null

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = AccentOrange.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = AccentOrange,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Normalized BIL",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                )
                Text(
                    text = if (hasVoltageSelection) {
                        "Select from lightning impulse values or enter custom"
                    } else {
                        "Select voltage in Setup first for predefined values"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (hasVoltageSelection) SlateGray else WarningAmber
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (hasVoltageSelection) {
                    ExposedDropdownMenuBox(
                        expanded = showDropdown,
                        onExpandedChange = { showDropdown = !showDropdown },
                        modifier = Modifier.width(70.dp)
                    ) {
                        OutlinedTextField(
                            value = if (availableValues.contains(state.bilNormalized.toIntOrNull())) "⚡" else "",
                            onValueChange = { },
                            readOnly = true,
                            placeholder = { Text("⚡", style = MaterialTheme.typography.bodySmall) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdown)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentOrange,
                                focusedLabelColor = AccentOrange,
                                unfocusedBorderColor = SlateGray.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall
                        )

                        ExposedDropdownMenu(
                            expanded = showDropdown,
                            onDismissRequest = { showDropdown = false }
                        ) {
                            availableValues.forEach { value ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "$value kV",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    },
                                    onClick = {
                                        viewModel.onBilNormalizedChange(value.toString())
                                        showDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                }

                OutlinedTextField(
                    value = state.bilNormalized,
                    onValueChange = { viewModel.onBilNormalizedChange(it) },
                    modifier = Modifier.width(100.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentOrange,
                        focusedLabelColor = AccentOrange,
                        unfocusedBorderColor = SlateGray.copy(alpha = 0.3f)
                    )
                )

                Text(
                    text = "kV",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = SlateGray
                    ),
                    modifier = Modifier.width(30.dp)
                )
            }
        }

        if (hasVoltageSelection && availableValues.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 44.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Available:",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SlateGray,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                availableValues.take(4).forEach { value ->
                    val isSelected = state.bilNormalized == value.toString()
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) AccentOrange else AccentOrange.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .wrapContentSize()
                            .let { modifier ->
                                if (!isSelected) {
                                    modifier.clickable {
                                        viewModel.onBilNormalizedChange(value.toString())
                                    }
                                } else modifier
                            }
                    ) {
                        Text(
                            text = "$value",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White else AccentOrange
                            )
                        )
                    }
                }

                if (availableValues.size > 4) {
                    Text(
                        text = "+${availableValues.size - 4} more",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SlateGray
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MovResultsCard(
    state: ConventionalUiState,
    protectionCapacity: ProtectionCapacityUseCase
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
                        imageVector = Icons.Default.Analytics,
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
                val bilNormalized = state.bilNormalized.toDoubleOrNull() ?: 0.0
                val nprValue = sa.npr
                val ratio = if (nprValue != 0.0) bilNormalized / nprValue else 0.0
                val isValid = if (bilNormalized != 0.0) {
                    protectionCapacity(bilNormalized, nprValue)
                } else {
                    false
                }

                ResultMetricCard(
                    title = "Protection Ratio",
                    value = String.format("%.3f", ratio),
                    unit = "BIL/NPR",
                    icon = Icons.Default.Calculate,
                    color = ElectricBlue,
                    description = "Normalized BIL divided by NPR"
                )

                Spacer(modifier = Modifier.height(16.dp))

                ValidationCard(
                    isValid = isValid,
                    title = "Isolation Coordination Status",
                    validMessage = "System meets protection requirements",
                    invalidMessage = "System does not meet protection requirements"
                )

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
                        text = "Enter all parameters and press \"Compute\" to see analysis",
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

data class MovInputField(
    val label: String,
    val value: String,
    val unit: String,
    val icon: ImageVector,
    val description: String,
    val onValueChange: (String) -> Unit
)

@Composable
private fun MovInputRow(input: MovInputField) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = SuccessGreen.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = input.icon,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(16.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = input.label,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = DeepNavy
                    )
                )
                Text(
                    text = input.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SlateGray
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input.value,
                    onValueChange = input.onValueChange,
                    modifier = Modifier.width(100.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SuccessGreen,
                        focusedLabelColor = SuccessGreen,
                        unfocusedBorderColor = SlateGray.copy(alpha = 0.3f)
                    )
                )

                Text(
                    text = input.unit,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = SlateGray
                    ),
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
}

@Composable
private fun ResultMetricCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    color: Color,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.05f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.2f)
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
                        .size(40.dp)
                        .background(
                            color = color,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = DeepNavy
                        )
                    )
                    Text(
                        text = description,
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
                    text = value,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SlateGray
                    )
                )
            }
        }
    }
}

@Composable
private fun ValidationCard(
    isValid: Boolean,
    title: String,
    validMessage: String,
    invalidMessage: String
) {
    val color = if (isValid) SuccessGreen else ErrorRed
    val icon = if (isValid) Icons.Default.CheckCircle else Icons.Default.Error
    val message = if (isValid) validMessage else invalidMessage

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.05f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = color
                    )
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SlateGray
                    )
                )
            }

            Text(
                text = if (isValid) "VALID" else "INVALID",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            )
        }
    }
}

private fun Double?.orZero() = this ?: 0.0
