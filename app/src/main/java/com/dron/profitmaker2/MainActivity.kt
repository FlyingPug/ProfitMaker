package com.dron.profitmaker2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dron.profitmaker2.ui.AppNavigation
import com.dron.profitmaker2.ui.theme.ProfitMaker2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Main() {
    ProfitMaker2Theme {
        AppNavigation()
    }
}
