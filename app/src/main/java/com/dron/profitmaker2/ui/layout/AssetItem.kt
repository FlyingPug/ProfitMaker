package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dron.profitmaker2.Dimens
import com.dron.profitmaker2.models.Asset

@Composable
fun AssetItem(
    asset: Asset,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Card(
        onClick = { onSelect(!isSelected) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.DefaultPadding),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(Dimens.DefaultPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = null
            )
            Column(modifier = Modifier.padding(start = Dimens.DefaultPadding)) {
                Text(asset.symbol, style = MaterialTheme.typography.titleMedium)
                Text(asset.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}