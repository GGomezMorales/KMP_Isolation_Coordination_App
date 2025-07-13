package org.tavo.project.presentation.screens.conventional


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConventionalMainScreen(
//    selectMOVUseCase: Unit,
//    viewModel: ConventionalMainViewModel = remember { ConventionalMainViewModel(selectMOVUseCase) }
) {
//    val maxVoltage by remember { derivedStateOf { viewModel.maxVoltage } }
//    val nominalVoltage by remember { derivedStateOf { viewModel.nominalVoltage } }
//    val landingFactor by remember { derivedStateOf { viewModel.landingFactor } }
//    val designFactor by remember { derivedStateOf { viewModel.designFactor } }
//    val timeFactor by remember { derivedStateOf { viewModel.timeFactor } }
//    val result by remember { derivedStateOf { viewModel.result } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Conventional Main Screen",
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}
