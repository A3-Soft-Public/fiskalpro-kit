package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java;

import android.util.Log;

import java.util.Arrays;
import java.util.UUID;

import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolCommandsBuilder;
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse;
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolCommand;
import sk.a3soft.kit.public_demoapp.utils.FileUtils;
import sk.a3soft.kit.tool.common.model.FailureType;
import sk.a3soft.kit.tool.common.model.Resource;

@Deprecated
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

    void sendFtPrintLocalImage() {
        new Thread(() -> {
            final String sampleImagePath = FileUtils.saveSampleImage();
            if (sampleImagePath != null) {
                NativeProtocolJavaSampleHelper.sendFtPrintLocalImageCommand(
                        sampleImagePath,
                        resource -> {
                            if (resource instanceof Resource.Loading) {
                                Log.i("JavaSample", "FtPrintLocalImage: In progress.");
                            } else if (resource instanceof Resource.Failure) {
                                FileUtils.deleteSampleImage();
                                Log.i("JavaSample", "FtPrintLocalImage: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                            } else if (resource instanceof Resource.Success) {
                                FileUtils.deleteSampleImage();
                                Log.i("JavaSample", "FtPrintLocalImage: Success");
                            }
                        }
                );
            }
        }).start();
    }

    public void sendSimpleNonFiscalDocument() {

        final NativeProtocolCommandsBuilder.Document builder = new NativeProtocolCommandsBuilder.Document(
                UUID.randomUUID().toString(),
                NativeProtocolCommand.FtOpen.Type.NON_FISCAL_DOCUMENT,
                Arrays.asList(
                        new NativeProtocolCommandsBuilder.Document.Item(
                                "0.30",
                                "1",
                                (short) 1,
                                "ks",
                                "Apple"
                        )
                ),
                Arrays.asList(
                        new NativeProtocolCommandsBuilder.Document.Payment(
                                NativeProtocolCommand.FPay.Index.CASH,
                                "5",
                                "Cash",
                                false,
                                null
                        )
                ),
                "0.30"
        );

        NativeProtocolJavaSampleHelper.sendCommands(
                builder.build(),
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "Non-fiscal document: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "Non-fiscal document: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        Log.i("JavaSample", "Non-fiscal document: Success");
                    }
                }
        );
    }

    public void sendCardPaymentPurchaseCommand() {
        NativeProtocolJavaSampleHelper.sendCardPaymentPurchaseCommand(
                UUID.randomUUID().toString(),
                15.50,
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "CardPaymentPurchase: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "CardPaymentPurchase: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        final Resource.Success<NativeProtocolResponse.FtCardInfo.Purchase> successResource = ((Resource.Success<NativeProtocolResponse.FtCardInfo.Purchase>) resource);
                        final NativeProtocolResponse.FtCardInfo.Purchase data = successResource.getData();
                        Log.i("JavaSample", "CardPaymentPurchase: Success, result: " + data.toString());
                    }
                }
        );
    }

    public void sendCardPaymentCancelLastCommand() {
        NativeProtocolJavaSampleHelper.sendCardPaymentCancelLastCommand(
                UUID.randomUUID().toString(),
                -15.50,
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "CardPaymentCancelLast: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "CardPaymentCancelLast: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        final Resource.Success<NativeProtocolResponse.FtCardInfo.Reversal> successResource = ((Resource.Success<NativeProtocolResponse.FtCardInfo.Reversal>) resource);
                        final NativeProtocolResponse.FtCardInfo.Reversal data = successResource.getData();
                        Log.i("JavaSample", "CardPaymentCancelLast: Success, result: " + data.toString());
                    }
                }
        );
    }
}
