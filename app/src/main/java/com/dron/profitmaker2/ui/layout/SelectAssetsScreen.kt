package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.dron.profitmaker2.viewmodels.BotViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dron.profitmaker2.R
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.viewmodels.BotViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAssetsScreen(
    navController: NavController,
    viewModel: BotViewModel = viewModel(
        factory = BotViewModelFactory(BotRepository(), AssetRepository())
    )
) {
    val selectedAssets by viewModel.selectedAssets.collectAsState()
    val assets by viewModel.assets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Select Assets") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(dimensionResource(R.dimen.circle_radius)),
                modifier = Modifier.size(dimensionResource(R.dimen.fab_size))
            )
            {
                Icon(Icons.Default.Check, "Confirm")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(assets) { asset ->
                AssetItem(
                    asset = asset,
                    isSelected = selectedAssets.contains(asset.symbol),
                    onSelect = { selected ->
                        val updatedSelection = if (selected) {
                            selectedAssets + asset.symbol
                        } else {
                            selectedAssets - asset.symbol
                        }
                        viewModel.selectAssets(updatedSelection)
                    }
                )
            }
        }
    }
}