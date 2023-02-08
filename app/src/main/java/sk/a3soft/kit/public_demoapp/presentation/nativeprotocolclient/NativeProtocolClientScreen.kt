@file:OptIn(ExperimentalMaterial3Api::class)

package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import sk.a3soft.kit.public_demoapp.presentation.components.InfoDialog
import sk.a3soft.kit.public_demoapp.presentation.components.LoadingOverlay
import sk.a3soft.kit.public_demoapp.presentation.components.Title

// Note: This screen is just a demo example
@Composable
fun NativeProtocolClientScreen(
    state: NativeProtocolClientScreenUiState,
    onHostIpValueChange: (String) -> Unit,
    onTcpIpFrInfoClick: () -> Unit,
    onTcpIpSimpleDocumentClick: () -> Unit,
    onTcpIpFtScanClick: () -> Unit,
    onTcpIpFtScanContinuousClick: () -> Unit,
    onTcpIpFtPrintLocalImageClick: (selectedUri: Uri) -> Unit,
    onCloseInfoDialog: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
) {

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let { onTcpIpFtPrintLocalImageClick(it) }
    }

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
                onClick = onTcpIpFrInfoClick
            ) {
                Text("Send")
            }
            Title(text = "Simple document")
            Button(
                onClick = onTcpIpSimpleDocumentClick
            ) {
                Text("Send")
            }
            Title(text = "FtScan")
            Button(
                onClick = onTcpIpFtScanClick
            ) {
                Text("Send")
            }
            Title(text = "FtScanContinuous")
            Button(
                onClick = onTcpIpFtScanContinuousClick
            ) {
                Text("Send")
            }
            Title(text = "FtPrintLocalImage")
            Button(
                onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
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
    }
}
