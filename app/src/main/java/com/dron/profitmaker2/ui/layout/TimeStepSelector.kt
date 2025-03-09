package com.dron.profitmaker2.ui.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dron.profitmaker2.models.TimeStep

@Composable
fun TimeStepSelector(
    selected: TimeStep,
    onSelect: (TimeStep) -> Unit
) {
    Column {
        Text("Select Time Step:", style = MaterialTheme.typography.titleMedium)
        TimeStep.entries.forEach { step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(step) }
            ) {
                RadioButton(
                    selected = step == selected,
                    onClick = { onSelect(step) }
                )
                Text(step.displayName, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}