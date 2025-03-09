package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.models.Strategy
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBotScreen(
    navController: NavController,
    viewModel: BotViewModel = viewModel(
        factory = BotViewModelFactory(BotRepository(), AssetRepository())
    ),
    strategyViewModel: StrategyViewModel = viewModel(
        factory = StrategyViewModelFactory(StrategyRepository()))
) {
    var botName by remember { mutableStateOf("") }
    val selectedAssets by viewModel.selectedAssets.collectAsState()
    val selectedStrategyId by viewModel.selectedStrategyId.collectAsState()
    val strategies by strategyViewModel.strategies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Bot",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (selectedAssets.isEmpty() || selectedStrategyId == null) return@Button
                    viewModel.createBot(botName)
                    navController.popBackStack()
                },
                enabled = botName.length in 3..100 &&
                        selectedAssets.isNotEmpty() &&
                        selectedStrategyId != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Create Bot")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = botName,
                onValueChange = { botName = it },
                label = { Text("Bot Name", color = MaterialTheme.colorScheme.onSurface) },
                isError = botName.length !in 3..100,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            AssetSelectorCard(
                selectedAssets = selectedAssets,
                onClick = { navController.navigate(Routes.SelectAssetsScreen.route) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            StrategySelectorCard(
                selectedStrategyId = selectedStrategyId,
                strategies = strategies,
                onClick = { navController.navigate(Routes.SelectStrategyScreen.route) }
            )
        }
    }
}

@Composable
fun AssetSelectorCard(
    selectedAssets: List<String>,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Selected Assets",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (selectedAssets.isEmpty()) {
                Text(
                    text = "No assets selected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                selectedAssets.forEach { assetId ->
                    Text(
                        text = assetId,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun StrategySelectorCard(
    selectedStrategyId: String?,
    strategies: List<Strategy>,
    onClick: () -> Unit
) {
    val selectedStrategy = strategies.find { it.id == selectedStrategyId }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Selected Strategy",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (selectedStrategy == null) {
                Text(
                    text = "No strategy selected",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                Text(
                    text = selectedStrategy.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}