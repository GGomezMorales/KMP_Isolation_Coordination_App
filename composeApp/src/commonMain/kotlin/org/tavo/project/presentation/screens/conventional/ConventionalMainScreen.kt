package org.tavo.project.presentation.screens.conventional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.NavController
import org.tavo.project.presentation.Screen

@Composable
fun ConventionalMainScreen(
    viewModel: ConventionalMainViewModel = koinInject()
) {
    val navController: NavController = LocalNavController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState())
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Isolation Coordination",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            SectionCard(
                title = "Setup",
                description = "Define initial parameters",
                onClick = { navController.navigate(Screen.Setup) }
            )

            SectionCard(
                title = "MOV Selection",
                description = "Choose available MOVs",
                onClick = { navController.navigate(Screen.MOVSelection) }
            )

            SectionCard(
                title = "Isolation Coordination",
                description = "Manage isolation and coordination",
                onClick = { navController.navigate(Screen.IsolationCoordination) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Back")
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
