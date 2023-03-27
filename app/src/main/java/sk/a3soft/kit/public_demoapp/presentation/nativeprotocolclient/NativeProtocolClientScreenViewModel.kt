package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolCommandsBuilder
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse
import sk.a3soft.kit.provider.nativeprotocol.client.domain.NativeProtocolClient
import sk.a3soft.kit.provider.nativeprotocol.client.domain.sendCommands
import sk.a3soft.kit.provider.nativeprotocol.common.LOCALHOST
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolCommand
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolConfig
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolMode
import sk.a3soft.kit.public_demoapp.extension.stateInWhileSubscribed
import sk.a3soft.kit.public_demoapp.utils.FileUtils
import sk.a3soft.kit.tool.common.model.FailureType
import sk.a3soft.kit.tool.common.model.Resource
import java.util.UUID
import javax.inject.Inject

// Note: This view model is just a demo example
@HiltViewModel
class NativeProtocolClientScreenViewModel @Inject constructor(
    private val nativeProtocolClient: NativeProtocolClient,
) : ViewModel() {

    private val tcpIpHost = MutableStateFlow(LOCALHOST)
    private val requestState = MutableStateFlow<NativeProtocolClientRequestState>(NativeProtocolClientRequestState.NotStarted)

    val state = combine(
        tcpIpHost,
        requestState,
    ) { tcpIpHost, requestState ->
        NativeProtocolClientScreenUiState(
            tcpIpHost = tcpIpHost,
            infoMessage = if (requestState is NativeProtocolClientRequestState.Finished) requestState.message else null,
            isLoading = requestState is NativeProtocolClientRequestState.InProgress
        )
    }.stateInWhileSubscribed(viewModelScope, NativeProtocolClientScreenUiState())

    init {
        tcpIpHost
            .onEach {
                nativeProtocolClient.config = NativeProtocolConfig(
                    mode = NativeProtocolMode.TcpIp(
                        hostname = it
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun onHostIpValueChange(newValue: String) {
        tcpIpHost.value = newValue
    }

    fun onClick(action: NativeProtocolScreenAction) {
        when (action) {
            NativeProtocolScreenAction.FrInfo -> onFrInfoClick()
            NativeProtocolScreenAction.SimpleFiscalDocument -> onSimpleFiscalDocumentClick()
            NativeProtocolScreenAction.SimpleNonFiscalDocument -> onSimpleNonFiscalDocumentClick()
            NativeProtocolScreenAction.FtScan -> onFtScanClick()
            NativeProtocolScreenAction.FtScanContinuous -> onFtScanContinuousClick()
            is NativeProtocolScreenAction.FtPrintLocalImage -> onFtPrintLocalImageClick()
            NativeProtocolScreenAction.CardPaymentPurchase -> onCardPaymentPurchaseClick()
            NativeProtocolScreenAction.CardPaymentCancelLast -> onCardPaymentCancelLastClick()
        }
    }

    private fun onFrInfoClick() {
        nativeProtocolClient
            .sendFrInfoCommand()
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onSimpleFiscalDocumentClick() {
        val nativeProtocolCommandsReceiptBuilder = NativeProtocolCommandsBuilder
            .Document(
                uuid = UUID.randomUUID().toString(),
                type = NativeProtocolCommand.FtOpen.Type.FISCAL_SALE,
                items = listOf(
                    NativeProtocolCommandsBuilder.Document.Item(
                        totalAmount = "0.30",
                        quantity = "1",
                        text = "Apple",
                        unit = "ks",
                        vatIndex = 1,
                    ),
                    NativeProtocolCommandsBuilder.Document.Item(
                        totalAmount = "1.10",
                        quantity = "2",
                        text = "Pear",
                        unit = "ks",
                        vatIndex = 1,
                    )
                ),
                payments = listOf(
                    NativeProtocolCommandsBuilder.Document.Payment(
                        index = NativeProtocolCommand.FPay.Index.CASH,
                        text = "Cash",
                        amount = "10",
                    ),
                    NativeProtocolCommandsBuilder.Document.Payment(
                        index = NativeProtocolCommand.FPay.Index.CASH,
                        text = "Money back",
                        amount = "8.60",
                        moneyBack = true,
                    )
                ),
                totalAmount = "1.40"
            )

        nativeProtocolClient
            .sendCommands<NativeProtocolResponse.General>(nativeProtocolCommandsReceiptBuilder.build())
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onSimpleNonFiscalDocumentClick() {
        val nativeProtocolCommandsReceiptBuilder = NativeProtocolCommandsBuilder
            .Document(
                uuid = UUID.randomUUID().toString(),
                type = NativeProtocolCommand.FtOpen.Type.NON_FISCAL_DOCUMENT,
                items = listOf(
                    NativeProtocolCommandsBuilder.Document.Item(
                        totalAmount = "0.30",
                        quantity = "1",
                        text = "Apple",
                        unit = "ks",
                        vatIndex = 1,
                    ),
                ),
                payments = listOf(
                    NativeProtocolCommandsBuilder.Document.Payment(
                        index = NativeProtocolCommand.FPay.Index.CASH,
                        text = "Cash",
                        amount = "5",
                    ),
                ),
                totalAmount = "0.30"
            )

        nativeProtocolClient
            .sendCommands<NativeProtocolResponse.General>(nativeProtocolCommandsReceiptBuilder.build())
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onFtScanClick() {
        nativeProtocolClient
            .sendFtScanCommand()
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onFtScanContinuousClick() {
        nativeProtocolClient
            .sendFtScanContinuousCommands()
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onFtPrintLocalImageClick() {
        viewModelScope.launch {
            FileUtils
                .saveSampleImage()
                ?.let { filePath ->
                    nativeProtocolClient
                        .sendFtPrintLocalImage(filePath)
                        .collect {
                            it.toRequestState()

                            if (it is Resource.Failure || it is Resource.Success) {
                                FileUtils.deleteSampleImage()
                            }
                        }
                }
        }
    }

    private fun onCardPaymentPurchaseClick() {
        nativeProtocolClient
            .sendCardPaymentPurchaseCommand(
                uuid = UUID.randomUUID().toString(),
                amount = 15.50,
                variableSymbol = "1234567890",
            )
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private fun onCardPaymentCancelLastClick() {
        nativeProtocolClient
            .sendCardPaymentCancelLastCommand(
                uuid = UUID.randomUUID().toString(),
                amount = -15.50,
            )
            .onEach {
                it.toRequestState()
            }
            .launchIn(viewModelScope)
    }

    private inline fun <reified T : NativeProtocolResponse> Resource<T, FailureType.NativeProtocol>.toRequestState() {
        requestState.value = when (this) {
            Resource.Loading -> NativeProtocolClientRequestState.InProgress
            is Resource.Failure -> NativeProtocolClientRequestState.Finished(toString())
            is Resource.Success -> when (this.data) {
                is NativeProtocolResponse.FrInfo,
                is NativeProtocolResponse.FtCardInfo,
                is NativeProtocolResponse.FtScanRead,
                is NativeProtocolResponse.FtScanResult -> NativeProtocolClientRequestState.Finished(data.toString())
                else -> NativeProtocolClientRequestState.Finished("Success!")
            }
        }
    }

    fun onCloseInfoDialog() = viewModelScope.launch {
        requestState.value = NativeProtocolClientRequestState.NotStarted
    }
}
