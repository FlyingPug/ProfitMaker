package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dron.profitmaker2.AppColors
import com.dron.profitmaker2.AppStrings
import com.dron.profitmaker2.Dimens
import com.dron.profitmaker2.R
import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@Composable
fun BotCard(
    bot: Bot,
    onClick: () -> Unit,
    strategyViewModel: StrategyViewModel = viewModel(
        factory = StrategyViewModelFactory(StrategyRepository())
    )
    ) {
    val strategy = strategyViewModel.getStrategyById(bot.strategyId)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.BotCardHeight)
            .padding(Dimens.DefaultPadding),
        shape = RoundedCornerShape(Dimens.CardCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.DefaultPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (strategy != null) {
                Icon(
                    painter = painterResource(id = strategyViewModel.getStrategyIcon(strategy.type)),
                    contentDescription = strategy.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                )
            } else {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "missing",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                )
            }
            Spacer(modifier = Modifier.width(Dimens.DefaultPadding))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = bot.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = AppStrings.getCreatedDate(bot.creationDate),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = AppStrings.getTotalProfit(bot.profitUSD),
                style = MaterialTheme.typography.labelMedium,
                color = if (bot.profitUSD >= 0) AppColors.ProfitColor else AppColors.LossColor,
                modifier = Modifier.padding(start = Dimens.DefaultPadding)
            )
        }
    }
}