package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.tools.screenshot.isValid
import com.dron.profitmaker2.models.FormulaValidator
import com.dron.profitmaker2.models.StrategyType
import com.dron.profitmaker2.models.TimeStep
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@Composable
fun CreateStrategyScreen(
    strategyType: String?,
    navController: NavController,
    viewModel: StrategyViewModel = viewModel(
        factory = StrategyViewModelFactory(StrategyRepository())
    )
) {
    var strategyName by remember { mutableStateOf("") }
    var formula by remember { mutableStateOf("") }
    var selectedTimeStep by remember { mutableStateOf(TimeStep.HOUR_1) }

    val isMathStrategy = strategyType == StrategyType.MATH.name

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = strategyName,
            onValueChange = { strategyName = it },
            label = { Text("Strategy Name") },
            isError = strategyName.length !in 3..100,
            modifier = Modifier.fillMaxWidth()
        )

        TimeStepSelector(
            selected = selectedTimeStep,
            onSelect = { selectedTimeStep = it }
        )

        if (isMathStrategy) {
            OutlinedTextField(
                value = formula,
                onValueChange = { formula = it },
                label = { Text("Formula (y = x(t))") },
                isError = formula.length > 100 || !FormulaValidator.validate(formula).isValid,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                viewModel.createStrategy(
                    name = strategyName,
                    type = StrategyType.valueOf(strategyType ?: StrategyType.MATH.name),
                    timeStep = selectedTimeStep,
                    formula = if (isMathStrategy) formula else null
                )
                navController.popBackStack()
            },
            enabled = strategyName.length in 3..100 &&
                    (!isMathStrategy || FormulaValidator.validate(formula).isValid),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Strategy")
        }
    }
}