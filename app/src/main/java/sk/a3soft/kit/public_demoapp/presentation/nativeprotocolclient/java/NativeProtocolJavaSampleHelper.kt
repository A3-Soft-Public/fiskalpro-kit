package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java

import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.*
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse
import sk.a3soft.kit.provider.nativeprotocol.client.domain.sendCommands
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolPrinterType
import sk.a3soft.kit.public_demoapp.DemoApplication
import sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java.di.NativeProtocolClientJavaEntryPoint
import sk.a3soft.kit.tool.common.model.FailureType
import sk.a3soft.kit.tool.common.model.Resource

@Deprecated("Use Kotlin flow instead.")
object NativeProtocolJavaSampleHelper {

    private val nativeProtocolClient = EntryPointAccessors
        .fromApplication(DemoApplication.applicationContext, NativeProtocolClientJavaEntryPoint::class.java)
        .nativeProtocolClient()

    @JvmStatic
    fun sendCommands(
        commands: Array<String>,
        listener: GeneralResourceListener,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendCommands<NativeProtocolResponse.General>(commands)
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    @JvmStatic
    fun sendFtScanCommand(listener: FtScanResourceListener) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendFtScanCommand()
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    @JvmStatic
    @JvmOverloads
    fun sendFtPrintLocalImageCommand(
        path: String,
        printerType: NativeProtocolPrinterType? = null,
        listener: GeneralResourceListener,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendFtPrintLocalImageCommand(path = path, printerType = printerType)
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    @JvmStatic
    @JvmOverloads
    fun sendCardPaymentPurchaseCommand(
        uuid: String,
        amount: Double,
        variableSymbol: String? = null,
        printerType: NativeProtocolPrinterType? = null,
        listener: CardPaymentPurchaseResourceListener,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendCardPaymentPurchaseCommand(uuid = uuid, amount = amount, variableSymbol = variableSymbol, printerType = printerType)
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    @JvmStatic
    @JvmOverloads
    fun sendCardPaymentCancelLastCommand(
        uuid: String,
        amount: Double,
        printerType: NativeProtocolPrinterType? = null,
        listener: CardPaymentCancelLastResourceListener,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendCardPaymentCancelLastCommand(uuid = uuid, amount = amount, printerType = printerType)
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    @JvmStatic
    fun sendFrPrinterTypesCommand(
        listener: FrPrinterTypesListener,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            nativeProtocolClient
                .sendFrPrinterTypesCommand()
                .collect {
                    listener.onEvent(it)
                }
        }
    }

    interface GeneralResourceListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.General, FailureType.NativeProtocol>)
    }

    interface FtScanResourceListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.FtScanRead, FailureType.NativeProtocol>)
    }

    interface CardPaymentPurchaseResourceListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.FtCardInfo.Purchase, FailureType.NativeProtocol>)
    }

    interface CardPaymentCancelLastResourceListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.FtCardInfo.Reversal, FailureType.NativeProtocol>)
    }

    interface FrPrinterTypesListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.FrPrinterTypes, FailureType.NativeProtocol>)
    }
}