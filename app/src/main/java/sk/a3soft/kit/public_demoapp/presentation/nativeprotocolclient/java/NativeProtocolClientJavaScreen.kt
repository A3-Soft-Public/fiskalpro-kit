package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.a3soft.kit.public_demoapp.presentation.components.FtPrintLocalImage
import sk.a3soft.kit.public_demoapp.presentation.components.Title

@Composable
fun NativeProtocolClientJavaScreen() {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                text = "Native Protocol Java Sample",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Title(text = "Simple non-fiscal document")
            Button(
                onClick = { NativeProtocolJavaSample().sendSimpleNonFiscalDocumentCommands() }
            ) {
                Text("Send")
            }
            Title(text = "Simple non-fiscal document (printer select)")
            Button(
                onClick = { NativeProtocolJavaSample().sendSimpleNonFiscalDocumentPrinterSelectCommands() }
            ) {
                Text("Send")
            }
            Title(text = "FtScan")
            Button(
                onClick = { NativeProtocolJavaSample().startFtScanCommand() }
            ) {
                Text("Send")
            }
            Title(text = "FtPrintLocalImage")
            FtPrintLocalImage {
                NativeProtocolJavaSample().sendFtPrintLocalImageCommand()
            }
            Title(text = "FtPrintLocalImage (printer select)")
            FtPrintLocalImage {
                NativeProtocolJavaSample().sendFtPrintLocalImagePrinterSelectCommand()
            }
            Title(text = "Card Payment - Purchase")
            Button(
                onClick = { NativeProtocolJavaSample().sendCardPaymentPurchaseCommand() }
            ) {
                Text("Send")
            }
            Title(text = "Card Payment - Purchase (printer select)")
            Button(
                onClick = { NativeProtocolJavaSample().sendCardPaymentPurchasePrinterSelectCommand() }
            ) {
                Text("Send")
            }
            Title(text = "Card Payment - Cancel last")
            Button(
                onClick = { NativeProtocolJavaSample().sendCardPaymentCancelLastCommand() }
            ) {
                Text("Send")
            }
        }
    }
}
