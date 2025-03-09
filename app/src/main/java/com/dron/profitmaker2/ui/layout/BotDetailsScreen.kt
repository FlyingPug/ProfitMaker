package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.R
import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory


@Composable
fun BotDetailsScreen(
    botId: String?,
    navController: NavController,
    viewModel: BotViewModel = viewModel(
        factory = BotViewModelFactory(BotRepository(), AssetRepository())
    )
) {
    val bot by viewModel.bots.collectAsState().value
        .find { it.id == botId }
        ?.let { remember { mutableStateOf(it) } }
        ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Bot not found")
            }
            return
        }

    val profitData by viewModel.calculateProfitData(bot)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = bot.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.selected_assets),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        bot.assets.forEach { asset ->
            Text(
                text = "- $asset",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Text(
            text = "Strategy: ${bot.strategyId}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        ProfitChart(data = bot.profitHistory)

        GridLayout(profitData = profitData)
    }
}

@Composable
fun ProfitChart(data: List<Double>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text("Profit Chart Placeholder")
    }
}

@Composable
fun GridLayout(profitData: Map<String, Double>) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ProfitCard(
                title = stringResource(id = R.string.profit_since_creation),
                profit = profitData["sinceCreation"] ?: 0.0
            )
            Spacer(modifier = Modifier.width(16.dp))
            ProfitCard(
                title = stringResource(id = R.string.profit_in_last_7_days),
                profit = profitData["last7Days"] ?: 0.0
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ProfitCard(
                title = stringResource(id = R.string.profit_in_last_30_days),
                profit = profitData["last30Days"] ?: 0.0
            )
            Spacer(modifier = Modifier.width(16.dp))
            ProfitCard(
                title = stringResource(id = R.string.profit_in_last_year),
                profit = profitData["lastYear"] ?: 0.0
            )
        }
    }
}

@Composable
fun ProfitCard(title: String, profit: Double) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "$${"%.2f".format(profit)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}