package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.AppColors
import com.dron.profitmaker2.Dimens
import com.dron.profitmaker2.R
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.ui.theme.DarkRed
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
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
                Text(stringResource(R.string.not_found))
            }
            return
        }

    val profitData = viewModel.calculateProfitData(bot)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(bot.name)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.deleteBot(bot.id)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.DefaultPadding),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkRed
                )
            ) {
                Text(stringResource(id = R.string.delete), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ProfitChart(data = bot.profitHistory)

            GridLayout(profitData)
        }
    }
}

@Composable
fun ProfitChart(data: List<Double>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background( MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center    ) {
        // graphic moc

        Text("Profit Chart (Placeholder)", style = MaterialTheme.typography.bodyMedium)
    }
}
@Composable
fun GridLayout(profitData: Map<String, Double>) {
    val items = listOf(
        Pair(stringResource(id = R.string.profit_since_creation), profitData["sinceCreation"] ?: 0.0),
        Pair(stringResource(id = R.string.profit_in_last_7_days), profitData["last7Days"] ?: 0.0),
        Pair(stringResource(id = R.string.profit_in_last_30_days), profitData["last30Days"] ?: 0.0),
        Pair(stringResource(id = R.string.profit_in_last_year), profitData["lastYear"] ?: 0.0)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(Dimens.DefaultPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.DefaultPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimens.DefaultPadding)
    ) {
        items(items) { (title, profit) ->
            ProfitCard(title = title, profit = profit)
        }
    }
}

@Composable
fun ProfitCard(title: String, profit: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.DefaultPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text ="$${"%.2f".format(profit)}",
                style = MaterialTheme.typography.labelMedium,
                color = if (profit >= 0) AppColors.ProfitColor else AppColors.LossColor,
                modifier = Modifier.padding(start = Dimens.DefaultPadding)
            )
        }
    }
}