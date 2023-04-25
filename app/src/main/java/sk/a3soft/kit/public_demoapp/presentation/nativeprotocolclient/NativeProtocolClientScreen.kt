@file:OptIn(ExperimentalMaterial3Api::class)

package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.a3soft.kit.public_demoapp.presentation.components.FtPrintLocalImage
import sk.a3soft.kit.public_demoapp.presentation.components.InfoDialog
import sk.a3soft.kit.public_demoapp.presentation.components.LoadingOverlay
import sk.a3soft.kit.public_demoapp.presentation.components.PrinterTypesDialog
import sk.a3soft.kit.public_demoapp.presentation.components.Title

// Note: This screen is just a demo example
@Composable
fun NativeProtocolClientScreen(
    state: NativeProtocolClientScreenUiState,
    onHostIpValueChange: (String) -> Unit,
    onCloseInfoDialog: () -> Unit,
    onClick: (action: NativeProtocolScreenAction) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                text = "Native Protocol TCP IP Samples",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            TextField(
                modifier = Modifier.padding(top = 8.dp),
                label = { Text("Host IP") },
                value = state.tcpIpHost,
                onValueChange = onHostIpValueChange,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
            )
            Title(text = "FrInfo")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.FrInfo) }
            ) {
                Text("Send")
            }
            Title(text = "Simple fiscal document")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.SimpleFiscalDocument) }
            ) {
                Text("Send")
            }
            Title(text = "Simple non-fiscal document")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.SimpleNonFiscalDocument) }
            ) {
                Text("Send")
            }
            Title(text = "Simple non-fiscal document (printer select)")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.SimpleNonFiscalDocumentPrinterSelect) }
            ) {
                Text("Send")
            }
            Title(text = "FtScan")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.FtScan) }
            ) {
                Text("Send")
            }
            Title(text = "FtScanContinuous")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.FtScanContinuous) }
            ) {
                Text("Send")
            }
            Title(text = "FtPrintLocalImage")
            FtPrintLocalImage(
                onClick = { onClick(NativeProtocolScreenAction.FtPrintLocalImage) }
            )
            Title(text = "FtPrintLocalImage (printer select)")
            FtPrintLocalImage(
                onClick = { onClick(NativeProtocolScreenAction.FtPrintLocalImagePrinterSelect) }
            )
            Title(text = "Card Payment - Purchase")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.CardPaymentPurchase) }
            ) {
                Text("Send")
            }
            Title(text = "Card Payment - Purchase (printer select)")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.CardPaymentPurchasePrinterSelect) }
            ) {
                Text("Send")
            }
            Title(text = "Card Payment - Cancel last")
            Button(
                onClick = { onClick(NativeProtocolScreenAction.CardPaymentCancelLast) }
            ) {
                Text("Send")
            }
        }
        if (state.isLoading) {
            LoadingOverlay()
        }
        if (state.infoMessage != null) {
            InfoDialog(
                message = state.infoMessage,
                onClose = onCloseInfoDialog,
            )
        }
        if (state.printerTypesUiState != null) {
            PrinterTypesDialog(
                printerTypes = state.printerTypesUiState.types,
                onCancel = { onClick(NativeProtocolScreenAction.PrinterTypesCanceled) },
                onSelectPrinterType = { onClick(NativeProtocolScreenAction.PrinterTypeSelected(it, state.printerTypesUiState.action)) },
            )
        }
    }
}
