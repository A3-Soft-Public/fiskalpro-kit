package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java;

import android.util.Log;

import androidx.annotation.NonNull;

import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse;
import sk.a3soft.kit.tool.common.model.FailureType;
import sk.a3soft.kit.tool.common.model.Resource;

public class NativeProtocolJavaSample {

    void startFtScan() {
        NativeProtocolJavaSampleHelper.sendFtScanCommand(resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "FtScan: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "FtScan: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        final Resource.Success<NativeProtocolResponse.FtScanRead> successResource = ((Resource.Success<NativeProtocolResponse.FtScanRead>) resource);
                        final NativeProtocolResponse.FtScanRead data = successResource.getData();
                        switch (data.getStatus()) {
                            case SCANNED:
                                Log.i("JavaSample", "FtScan: Success, result: " + data.getScanResult());
                                break;
                            case UNKNOWN:
                                Log.i("JavaSample", "FtScan: UNKNOWN status.");
                                break;
                            case UNSUPPORTED_MODE:
                                Log.i("JavaSample", "FtScan: UNSUPPORTED_MODE.");
                                break;
                            case UNSUPPORTED_CAMERA:
                                Log.i("JavaSample", "FtScan: UNSUPPORTED_CAMERA.");
                                break;
                            case SCAN_ERROR:
                                Log.i("JavaSample", "FtScan: SCAN_ERROR.");
                                break;
                            case TIMEOUT:
                                Log.i("JavaSample", "FtScan: TIMEOUT.");
                                break;
                            case ENDED_BY_USER:
                                Log.i("JavaSample", "FtScan: ENDED_BY_USER.");
                                break;
                        }
                    }
                }
        );
    }

    void sendFtPrintLocalImage(@NonNull final String path) {
        NativeProtocolJavaSampleHelper.sendFtPrintLocalImageCommand(
                path,
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "FtPrintLocalImage: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "FtPrintLocalImage: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        Log.i("JavaSample", "FtPrintLocalImage: Success");
                    }
                }
        );
    }
}
