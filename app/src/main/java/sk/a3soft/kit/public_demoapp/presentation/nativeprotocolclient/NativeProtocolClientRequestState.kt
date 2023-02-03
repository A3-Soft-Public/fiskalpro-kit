package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient

sealed interface NativeProtocolClientRequestState {
    object NotStarted : NativeProtocolClientRequestState
    object InProgress : NativeProtocolClientRequestState
    data class Finished(val message: String) : NativeProtocolClientRequestState
}