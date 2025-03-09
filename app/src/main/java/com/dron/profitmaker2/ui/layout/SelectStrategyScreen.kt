package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStrategyScreen(
    navController: NavController,
    onStrategySelected: (String) -> Unit,
    viewModel: StrategyViewModel = viewModel(
        factory = StrategyViewModelFactory(StrategyRepository())
    )
) {
    val strategies by viewModel.strategies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Select Strategy") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(strategies) { strategy ->
                StrategyCard(
                    strategy = strategy,
                    onClick = {
                        onStrategySelected(strategy.id)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}