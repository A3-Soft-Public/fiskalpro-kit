package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

sealed interface NativeProtocolScreenAction {

    object FrInfo: NativeProtocolScreenAction
    object SimpleFiscalDocument: NativeProtocolScreenAction
    object SimpleNonFiscalDocument: NativeProtocolScreenAction
    object FtScan: NativeProtocolScreenAction
    object FtPrintLocalImage: NativeProtocolScreenAction
    object FtScanContinuous: NativeProtocolScreenAction
    object CardPaymentPurchase: NativeProtocolScreenAction
    object CardPaymentCancelLast: NativeProtocolScreenAction

}