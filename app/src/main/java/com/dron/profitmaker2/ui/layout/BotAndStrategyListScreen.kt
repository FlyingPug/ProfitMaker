package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dron.profitmaker2.R
import com.dron.profitmaker2.Routes
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.viewmodels.BotViewModel
import com.dron.profitmaker2.viewmodels.BotViewModelFactory
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@Composable
fun BotAndStrategyListScreen(
    navController: NavController,
    botViewModel: BotViewModel = viewModel(factory = BotViewModelFactory(BotRepository(), AssetRepository())),
    strategyViewModel: StrategyViewModel = viewModel(factory = StrategyViewModelFactory(
        StrategyRepository()
    ))
) {
    val bots by botViewModel.bots.collectAsState()
    val strategies by strategyViewModel.strategies.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val filteredBots = bots.filter { bot ->
        bot.name.contains(searchQuery, ignoreCase = true)
    }

    val filteredStrategies = strategies.filter { strategy ->
        strategy.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_top))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTab == 0) {
                        navController.navigate(Routes.CreateBotScreen.route)
                    } else {
                        navController.navigate(Routes.SelectStrategyScreen.route)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(dimensionResource(R.dimen.circle_radius)),
                modifier = Modifier.size(dimensionResource(R.dimen.fab_size))
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Create Item",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { selectedTab = 0 },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.robot),
                            contentDescription = "Bot List",
                            tint = if (selectedTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                        )
                    }
                    IconButton(
                        onClick = { selectedTab = 1 },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.strategy),
                            contentDescription = "Strategy List",
                            tint = if (selectedTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(initialOffsetX = { 1000 }) togetherWith slideOutHorizontally(targetOffsetX = { -1000 })
                    } else {
                        slideInHorizontally(initialOffsetX = { -1000 }) togetherWith slideOutHorizontally(targetOffsetX = { 1000 })
                    }
                }
            ) { targetTab ->
                when (targetTab) {
                    0 -> {
                        if (filteredBots.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.no_bots),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            LazyColumn {
                                items(filteredBots) { bot ->
                                    BotCard(
                                        bot = bot,
                                        onClick = { navController.navigate(Routes.BotDetails.route + "/${bot.id}") }
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        if (filteredStrategies.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.no_strategies),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            LazyColumn {
                                items(filteredStrategies) { strategy ->
                                    StrategyCard(
                                        strategy = strategy,
                                        onClick = { navController.navigate(Routes.EditStrategyScreen.route + "/${strategy.id}") }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text(text = stringResource(R.string.search_for_bots)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        shape = RoundedCornerShape(dimensionResource(R.dimen.search_corner_radius)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBotAndStrategyListScreen() {
    BotAndStrategyListScreen(navController = rememberNavController())
}