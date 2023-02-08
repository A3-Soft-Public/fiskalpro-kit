# FiskalPRO Kit Public

This repository contains artefacts and example usages of the FiskalPRO Kit public modules.

## Implementation

### Repository

Get the access token from the A3Soft company and add the FiskalPRO Kit public repository configuration to the settings.gradle dependencyResolutionManagement:

```
dependencyResolutionManagement {
    …
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/A3-Soft-Public/fiskalpro-kit")

            credentials {
                username = ""
                password = "enter_your_access_token_here"
            }
        }
    }
}
```

### Dependency

Add the required dependency to the target build.gradle module. For example Native-protocol client:

```
android {
    …
}

dependencies {
    …

    implementation "sk.a3soft.kit.provider.native-protocol:client:2.1.10"
}
```

### JavaVersion

The minimal required Java version is currently JavaVersion.VERSION_11.

```
android {
    …

    compileOptions {
        …
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

```


### Enable desugaring (for min API 25 and lower only!)

* https://developer.android.com/studio/write/java8-support#library-desugaring

```
android {
    …
    compileOptions {
        …
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:[VERSION]")
}
```

## Usage

### Native-protocol client - Scan 

Native-protocol client library currently supports single and continuous scan mode. Examples:

###### Barcode one-time scan

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val nativeProtocolClient: NativeProtocolClient,
) : ViewModel() {

    fun onTcpIpFtScanClick() {
        nativeProtocolClient
            .sendFtScanCommand()
            .onEach {
                when (it) {
                    Resource.Loading -> TODO()
                    is Resource.Failure -> TODO()
                    is Resource.Success -> {
                        val ftScanRead: NativeProtocolResponse.FtScanRead = it.data
                        when (ftScanRead.status) {
                            NativeProtocolResponse.FtScanRead.Status.UNKNOWN -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.UNSUPPORTED_MODE -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.UNSUPPORTED_CAMERA -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.SCAN_ERROR -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.TIMEOUT -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.ENDED_BY_USER -> TODO()
                            NativeProtocolResponse.FtScanRead.Status.SCANNED -> {
                                val scanResult: String = ftScanRead.scanResult
                                TODO()
                            }
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
```

###### Continuous scan

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val nativeProtocolClient: NativeProtocolClient,
) : ViewModel() {

    fun onTcpIpFtScanContinuousClick() {
        nativeProtocolClient
            .sendFtScanContinuousCommands()
            .onEach {
                when (it) {
                    Resource.Loading -> TODO()
                    is Resource.Failure -> TODO()
                    is Resource.Success -> {
                        val ftScanResult: NativeProtocolResponse.FtScanResult = it.data
                        when (ftScanResult.status) {
                            NativeProtocolResponse.FtScanResult.Status.UNKNOWN -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.UNSUPPORTED_CAMERA -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.SCAN_ERROR -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.TIMEOUT -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.ENDED_BY_USER -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.SCANNER_NOT_OPENED -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.NEW_DATA_NOT_AVAILABLE -> TODO()
                            NativeProtocolResponse.FtScanResult.Status.SCANNED -> {
                                val scanResult: String = ftScanResult.scanResult
                                TODO()
                            }
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
```

Note: FiskalPRO Manager 2.3.7 or newer is required to be installed on the target device (and eKasa 1.2.91 or newer if SK fiscal is required) .

### Native-protocol client - FrInfo

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val nativeProtocolClient: NativeProtocolClient,
) : ViewModel() {

    fun onTcpIpFrInfoClick() {
        nativeProtocolClient
            .sendFrInfoCommand()
            .onEach {
                when (it) {
                    Resource.Loading -> TODO()
                    is Resource.Failure -> TODO()
                    is Resource.Success -> {
                        val frInfo: NativeProtocolResponse.FrInfo = it.data
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
```

### Native-protocol client - FtPrintLocalImage

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val nativeProtocolClient: NativeProtocolClient,
) : ViewModel() {

    fun onTcpIpFtPrintLocalImageClick() {
        nativeProtocolClient
            .sendFtPrintLocalImage("/public/raw/path/to/the/target/image.extension")
            .onEach {
                when (it) {
                    Resource.Loading -> TODO()
                    is Resource.Failure -> TODO()
                    is Resource.Success -> TODO()
                }
            }
            .launchIn(viewModelScope)
    }
}
```

For a detailed usage example, feel free to clone this repository Demo App.
