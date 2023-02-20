package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java

import kotlinx.coroutines.*
import sk.a3soft.kit.provider.nativeprotocol.client.data.NativeProtocolClientImpl
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse
import sk.a3soft.kit.tool.common.model.FailureType
import sk.a3soft.kit.tool.common.model.Resource

object NativeProtocolJavaSampleHelper {

    private val nativeProtocolClient = NativeProtocolClientImpl(Dispatchers.IO)

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

    interface FtScanResourceListener {
        fun onEvent(resource: Resource<NativeProtocolResponse.FtScanRead, FailureType.NativeProtocol>)
    }
}