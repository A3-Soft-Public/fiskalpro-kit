package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NativeProtocolClientScreenContainer(
    viewModel: NativeProtocolClientScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NativeProtocolClientScreen(
        state = state,
        onHostIpValueChange = viewModel::onHostIpValueChange,
        onTcpIpFrInfoClick = viewModel::onTcpIpFrInfoClick,
        onTcpIpSimpleDocumentClick = viewModel::onTcpIpSimpleDocumentClick,
        onTcpIpFtScanClick = viewModel::onTcpIpFtScanClick,
        onTcpIpFtScanContinuousClick = viewModel::onTcpIpFtScanContinuousClick,
        onCloseInfoDialog = viewModel::onCloseInfoDialog,
    )
}
