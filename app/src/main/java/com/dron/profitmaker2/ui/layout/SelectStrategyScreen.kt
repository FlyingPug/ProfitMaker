package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dron.profitmaker2.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.dron.profitmaker2.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectStrategyScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.selected_type)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn {
                items(StrategyType.entries.toTypedArray()) { strategyType ->
                    StrategyTypeCard(
                        strategyType = strategyType,
                        onClick = {
                            navController.navigate(Routes.CreateStrategyScreen.route + "/${strategyType.name}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StrategyTypeCard(
    strategyType: StrategyType,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = strategyType.iconResId),
                contentDescription = strategyType.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = strategyType.titleResId),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(id = strategyType.descriptionResId),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

enum class StrategyType(
    val iconResId: Int,
    val titleResId: Int,
    val descriptionResId: Int
) {
    MATH(
        iconResId = R.drawable.math,
        titleResId = R.string.math_strategy,
        descriptionResId = R.string.math_strategy_description
    ),
    RANDOM(
        iconResId = R.drawable.random,
        titleResId = R.string.fish_strategy,
        descriptionResId = R.string.fish_strategy_description
    )
}