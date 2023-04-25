package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolPrinterType

data class PrinterTypesUiState(
    val action: RequestedAction,
    val types: List<PrinterType>,
) {
    data class PrinterType(
        val name: String,
    ) {
        lateinit var payload: NativeProtocolPrinterType
    }

    sealed interface RequestedAction {
        object FtPrintLocalImage: RequestedAction
        object CardPaymentPurchase : RequestedAction
        object SimpleNonFiscalDocument : RequestedAction
    }
}