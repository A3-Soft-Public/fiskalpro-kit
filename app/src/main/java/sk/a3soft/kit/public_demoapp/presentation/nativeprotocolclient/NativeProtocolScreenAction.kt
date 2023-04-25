package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

sealed interface NativeProtocolScreenAction {

    object FrInfo: NativeProtocolScreenAction
    object SimpleFiscalDocument: NativeProtocolScreenAction
    object SimpleNonFiscalDocument: NativeProtocolScreenAction
    object SimpleNonFiscalDocumentPrinterSelect : NativeProtocolScreenAction
    object FtScan: NativeProtocolScreenAction
    object FtPrintLocalImage: NativeProtocolScreenAction
    object FtPrintLocalImagePrinterSelect : NativeProtocolScreenAction
    object FtScanContinuous: NativeProtocolScreenAction
    object CardPaymentPurchase: NativeProtocolScreenAction
    object CardPaymentPurchasePrinterSelect : NativeProtocolScreenAction
    object CardPaymentCancelLast: NativeProtocolScreenAction
    object PrinterTypesCanceled : NativeProtocolScreenAction
    data class PrinterTypeSelected(
        val type: PrinterTypesUiState.PrinterType,
        val action: PrinterTypesUiState.RequestedAction,
    ) : NativeProtocolScreenAction
}