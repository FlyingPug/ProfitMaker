package com.dron.profitmaker2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dron.profitmaker2.ui.AppNavigation
import com.dron.profitmaker2.ui.theme.ProfitMaker2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfitMaker2Theme {
                AppNavigation(viewModelStoreOwner = this)
            }
        }
    }
}
