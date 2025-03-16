package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dron.profitmaker2.Dimens
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dron.profitmaker2.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStrategiesScreen(
    navController: NavController,
    viewModel: BotViewModel,
    strategyViewModel: StrategyViewModel
) {
    val selectedStrategyId by viewModel.selectedStrategyId.collectAsState()
    val strategies by strategyViewModel.strategies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Strategy") }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.DefaultPadding)
            ) {
                Text(stringResource(R.string._continue))
            }
        },
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(strategies) { strategy ->
                SelectableStrategyCard(
                    strategy = strategy,
                    isSelected = selectedStrategyId == strategy.id,
                    onClick = {
                        if (selectedStrategyId != strategy.id) {
                            viewModel.selectStrategy(strategy.id)
                        }
                    }
                )
            }
        }
    }
}