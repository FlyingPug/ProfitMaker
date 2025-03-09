package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory

@Composable
fun CreateBotScreen(
    navController: NavController,
    viewModel: BotViewModel = viewModel(
        factory = BotViewModelFactory(BotRepository(), AssetRepository())
    )
) {
    var botName by remember { mutableStateOf("") }
    val selectedAssets by viewModel.selectedAssets.collectAsState()
    val selectedStrategyId by viewModel.selectedStrategyId.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = botName,
            onValueChange = { botName = it },
            label = { Text("Bot Name") },
            isError = botName.length !in 3..100,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { navController.navigate(Routes.SelectAssetsScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Assets")
        }

        Button(
            onClick = { navController.navigate(Routes.SelectStrategyScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Strategy")
        }

        Button(
            onClick = {
                if (selectedAssets.isEmpty()) {
                    return@Button
                }
                if (selectedStrategyId == null) {
                    return@Button
                }
                viewModel.createBot(botName)
                navController.popBackStack()
            },
            enabled = botName.length in 3..100 &&
                    selectedAssets.isNotEmpty() &&
                    selectedStrategyId != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Bot")
        }
    }
}