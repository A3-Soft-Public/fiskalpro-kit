@file:OptIn(ExperimentalMaterial3Api::class)

package sk.a3soft.kit.public_demoapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.PrinterTypesUiState
import sk.a3soft.kit.public_demoapp.ui.theme.DemoAppTheme

@Composable
internal fun PrinterTypesDialog(
    printerTypes: List<PrinterTypesUiState.PrinterType>,
    onSelectPrinterType: (PrinterTypesUiState.PrinterType) -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(
                    text = "Select printer",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.size(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(printerTypes) { printerType ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectPrinterType(printerType) }
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp),
                            text = printerType.name,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = onCancel,
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PrinterTypesDialogDefaultPreview() {
    DemoAppTheme {
        PrinterTypesDialog(
            printerTypes = listOf(
                PrinterTypesUiState.PrinterType(name = "INTERNAL"),
                PrinterTypesUiState.PrinterType(name = "SERIAL"),
                PrinterTypesUiState.PrinterType(name = "NETWORK"),
            ),
            onSelectPrinterType = {},
            onCancel = {},
        )
    }
}
