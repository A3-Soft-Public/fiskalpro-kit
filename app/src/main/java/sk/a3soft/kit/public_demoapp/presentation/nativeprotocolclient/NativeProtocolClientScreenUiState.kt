package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import sk.a3soft.kit.provider.nativeprotocol.common.LOCALHOST

data class NativeProtocolClientScreenUiState(
    val tcpIpHost: String = LOCALHOST,
    val infoMessage: String? = null,
    val isLoading: Boolean = false,
)