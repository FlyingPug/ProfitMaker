package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.viewmodels.BotViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBotScreen(
    navController: NavController,
    viewModel: BotViewModel
) {
    var botName by remember { mutableStateOf("") }
    val selectedAssets by viewModel.selectedAssets.collectAsState()
    val selectedStrategyId by viewModel.selectedStrategyId.collectAsState()

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
                            Icons.AutoMirrored.Filled.ArrowBack,
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
                onClick = { navController.navigate(Routes.SelectStrategiesScreen.route) }
            )
        }
    }
}