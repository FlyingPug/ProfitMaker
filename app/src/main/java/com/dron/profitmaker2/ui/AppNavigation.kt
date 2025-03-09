package com.dron.profitmaker2.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.ui.layout.BotAndStrategyListScreen
import com.dron.profitmaker2.ui.layout.BotDetailsScreen
import com.dron.profitmaker2.ui.layout.CreateBotScreen
import com.dron.profitmaker2.ui.layout.CreateStrategyScreen
import com.dron.profitmaker2.ui.layout.SelectAssetsScreen
import com.dron.profitmaker2.ui.layout.SelectStrategyScreen
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.BotListScreen.route) {
        composable(Routes.BotListScreen.route) { BotAndStrategyListScreen(navController) }
        composable(Routes.CreateBotScreen.route) { CreateBotScreen(navController) }
        composable(Routes.SelectAssetsScreen.route) { SelectAssetsScreen(navController) }
        composable(Routes.SelectStrategyScreen.route) {
            val botViewModel: BotViewModel = viewModel(
                factory = BotViewModelFactory(BotRepository(), AssetRepository())
            )
            SelectStrategyScreen(
                navController = navController,
                onStrategySelected = { strategyId ->
                    botViewModel.selectStrategy(strategyId)
                    navController.popBackStack()
                }
            )
        }
        composable(Routes.CreateStrategyScreen.route + "/{strategyType}") { backStackEntry ->
            val strategyType = backStackEntry.arguments?.getString("strategyType")
            CreateStrategyScreen(strategyType, navController)
        }
        composable(Routes.BotDetails.route + "/{botId}") { backStackEntry ->
            val botId = backStackEntry.arguments?.getString("botId")
            BotDetailsScreen(botId, navController)
        }
    }
}