package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolPrinterType

internal fun Set<NativeProtocolPrinterType>.toPrinterTypesUiState(action: PrinterTypesUiState.RequestedAction) =
    PrinterTypesUiState(
        action = action,
        types = map {
            PrinterTypesUiState.PrinterType(
                name = it.name,
            ).apply { payload = it }
        },
    )