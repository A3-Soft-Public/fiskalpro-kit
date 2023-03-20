package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.a3soft.kit.provider.nativeprotocol.client.domain.NativeProtocolClient

@EntryPoint
@Deprecated("For deprecated java usage only.")
@InstallIn(SingletonComponent::class)
interface NativeProtocolClientJavaEntryPoint {
    fun nativeProtocolClient(): NativeProtocolClient
}