package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dron.profitmaker2.models.FormulaValidator
import com.dron.profitmaker2.models.StrategyType
import com.dron.profitmaker2.models.TimeStep
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.viewmodels.StrategyViewModel
import com.dron.profitmaker2.viewmodels.StrategyViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStrategyScreen(
    strategyId: String?,
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

    LaunchedEffect(strategyId) {
        strategyId?.let { id ->
            val strategy = viewModel.getStrategyById(id)
            strategy?.let {
                strategyName = it.name
                selectedTimeStep = it.timeStep
                formula = it.formula ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (strategyId == null) "Create Strategy" else "Edit Strategy",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (strategyId == null) {
                        viewModel.createStrategy(
                            name = strategyName,
                            type = StrategyType.valueOf(strategyType ?: StrategyType.MATH.name),
                            timeStep = selectedTimeStep,
                            formula = if (isMathStrategy) formula else null
                        )
                    } else {
                        viewModel.updateStrategy(
                            id = strategyId,
                            name = strategyName,
                            timeStep = selectedTimeStep,
                            formula = if (isMathStrategy) formula else null
                        )
                    }
                    navController.popBackStack()
                },
                enabled = strategyName.length in 3..100 &&
                        (!isMathStrategy || FormulaValidator.validate(formula).isValid),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Continue")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = strategyName,
                onValueChange = { strategyName = it },
                label = { Text("Strategy Name", color = MaterialTheme.colorScheme.onPrimary) },
                isError = strategyName.length !in 3..100,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )

            TimeStepSelector(
                selected = selectedTimeStep,
                onSelect = { selectedTimeStep = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (isMathStrategy) {
                OutlinedTextField(
                    value = formula,
                    onValueChange = { formula = it },
                    label = { Text("Formula (y = x(t))", color = MaterialTheme.colorScheme.onPrimary) },
                    isError = formula.length > 100 || !FormulaValidator.validate(formula).isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}