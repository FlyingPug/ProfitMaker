package com.dron.profitmaker2.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.ui.layout.BotAndStrategyListScreen
import com.dron.profitmaker2.ui.layout.BotDetailsScreen
import com.dron.profitmaker2.ui.layout.CreateBotScreen
import com.dron.profitmaker2.ui.layout.CreateStrategyScreen
import com.dron.profitmaker2.ui.layout.SelectAssetsScreen
import com.dron.profitmaker2.ui.layout.SelectStrategiesScreen
import com.dron.profitmaker2.ui.layout.SelectStrategyScreen
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModel

@Composable
fun AppNavigation(viewModelStoreOwner: ViewModelStoreOwner) {
    val navController = rememberNavController()
    val botViewModel: BotViewModel = hiltViewModel()
    val strategyViewModel: StrategyViewModel = hiltViewModel()

    NavHost(navController, startDestination = Routes.BotListScreen.route) {
        composable(Routes.BotListScreen.route) { BotAndStrategyListScreen(navController, botViewModel, strategyViewModel) }
        composable(Routes.CreateBotScreen.route) { CreateBotScreen(navController, botViewModel) }
        composable(Routes.SelectAssetsScreen.route) { SelectAssetsScreen(navController, botViewModel) }
        composable(Routes.SelectStrategyScreen.route) { SelectStrategyScreen(navController = navController) }
        composable(Routes.SelectStrategiesScreen.route) { SelectStrategiesScreen(navController = navController, botViewModel, strategyViewModel) }
        composable(Routes.CreateStrategyScreen.route + "/{strategyType}") { backStackEntry ->
            val strategyType = backStackEntry.arguments?.getString("strategyType")
            if (strategyType != null) {
                CreateStrategyScreen(
                    strategyId = null,
                    strategyType = strategyType,
                    navController = navController
                )
            }
        }
        composable(Routes.EditStrategyScreen.route + "/{strategyId}") { backStackEntry ->
            val strategyId = backStackEntry.arguments?.getString("strategyId")
            val strategyViewModel: StrategyViewModel = hiltViewModel()
            val strategy = strategyId?.let {
                strategyViewModel.getStrategyById(it)
            }
            strategy?.type?.name?.let {
                CreateStrategyScreen(
                    strategyId = strategyId,
                    strategyType = it,
                    navController = navController
                )
            }
        }
        composable(Routes.BotDetails.route + "/{botId}") { backStackEntry ->
            val botId = backStackEntry.arguments?.getString("botId")
            BotDetailsScreen(botId, navController)
        }
    }
}