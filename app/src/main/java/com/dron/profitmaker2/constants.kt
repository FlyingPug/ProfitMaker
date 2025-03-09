package com.dron.profitmaker2

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.dimensionResource
import com.dron.profitmaker2.ui.theme.Green
import com.dron.profitmaker2.ui.theme.Red
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Dimens {
    val CardCornerRadius
        @Composable get() = dimensionResource(R.dimen.card_corner_radius)

    val BotCardHeight
        @Composable get() = dimensionResource(R.dimen.bot_card_height)

    val DefaultPadding
        @Composable get() = dimensionResource(R.dimen.padding_medium)
}

sealed class Routes(val route: String) {

    data object BotListScreen : Routes("botListScreen")
    data object CreateBotScreen : Routes("createBotScreen")
    data object BotDetails : Routes("botDetails")
    data object CreateStrategyScreen : Routes("createStrategyList")
    data object EditStrategyScreen : Routes("editStrategyList")
    data object SelectAssetsScreen : Routes("selectAssetsScreen")
    data object SelectStrategyScreen : Routes("selectStrategyScreen")
}

object AppStrings {
    @Composable
    fun getTotalProfit(amount: Double): String {
        return stringResource(R.string.total_profit, amount)
    }

    @Composable
    fun getCreatedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return stringResource(R.string.created_date, date.format(formatter))
    }
}

object AppColors {
    val ProfitColor
        @Composable get() = Green

    val LossColor
        @Composable get() = Red
}