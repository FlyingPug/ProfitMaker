package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.dron.profitmaker2.models.TimeStep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeStepSelector(
    selected: TimeStep,
    onSelect: (TimeStep) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val timeSteps = TimeStep.values()

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selected.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Time Step", color = MaterialTheme.colorScheme.onPrimary) },
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                        contentDescription = "Toggle dropdown",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            timeSteps.forEach { timeStep ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = timeStep.name,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        onSelect(timeStep)
                        expanded = false
                    }
                )
            }
        }
    }
}