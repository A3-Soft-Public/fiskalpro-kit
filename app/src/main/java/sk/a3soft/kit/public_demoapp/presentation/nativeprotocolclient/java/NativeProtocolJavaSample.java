package sk.a3soft.kit.public_demoapp.presentation.nativeprotocolclient.java;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolCommandsBuilder;
import sk.a3soft.kit.provider.nativeprotocol.client.data.model.NativeProtocolResponse;
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolCommand;
import sk.a3soft.kit.provider.nativeprotocol.common.data.model.NativeProtocolPrinterType;
import sk.a3soft.kit.public_demoapp.utils.FileUtils;
import sk.a3soft.kit.tool.common.model.FailureType;
import sk.a3soft.kit.tool.common.model.Resource;

@Deprecated
public class NativeProtocolJavaSample {

    void startFtScanCommand() {
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

    void sendFtPrintLocalImageCommand() {
        sendFtPrintLocalImageCommand(null);
    }

    void sendFtPrintLocalImageCommand(@Nullable final NativeProtocolPrinterType printerType) {
        new Thread(() -> {
            final String sampleImagePath = FileUtils.saveSampleImage();
            if (sampleImagePath != null) {
                NativeProtocolJavaSampleHelper.sendFtPrintLocalImageCommand(
                        sampleImagePath,
                        printerType,
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

    void sendSimpleNonFiscalDocumentCommands() {
        sendSimpleNonFiscalDocumentCommands(null);
    }

    void sendSimpleNonFiscalDocumentCommands(@Nullable final NativeProtocolPrinterType printerType) {

        final NativeProtocolCommandsBuilder.Document builder = new NativeProtocolCommandsBuilder.Document(
                UUID.randomUUID().toString(),
                NativeProtocolCommand.FtOpen.Type.NON_FISCAL_DOCUMENT,
                printerType,
                List.of(
                        new NativeProtocolCommandsBuilder.Document.Item(
                                "0.30",
                                "1",
                                (short) 1,
                                "ks",
                                "Apple"
                        )
                ),
                List.of(
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

    void sendCardPaymentPurchaseCommand() {
        sendCardPaymentPurchaseCommand(null);
    }

    void sendCardPaymentPurchaseCommand(@Nullable final NativeProtocolPrinterType printerType) {
        NativeProtocolJavaSampleHelper.sendCardPaymentPurchaseCommand(
                UUID.randomUUID().toString(),
                15.50,
                "1234567890",
                printerType,
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "CardPaymentPurchase: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "CardPaymentPurchase: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        final Resource.Success<NativeProtocolResponse.FtCardInfo.Purchase> successResource = ((Resource.Success<NativeProtocolResponse.FtCardInfo.Purchase>) resource);
                        final NativeProtocolResponse.FtCardInfo.Purchase data = successResource.getData();
                        Log.i("JavaSample", "CardPaymentPurchase: Success, AID: " + data.getAid());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, AUTH CODE: " + data.getAuthCode());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD TYPE: " + data.getCardType());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD MASK PAN: " + data.getCardMaskPan());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD DATE TIME: " + data.getDateTime());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD IS ACCEPTED: " + data.isAccepted());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD MESSAGE: " + data.getMessageOrReasonNotAccepted());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD MESSAGE 2: " + data.getMessage2());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD PIN: " + data.getPin());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD SEQ NUMBER: " + data.getSeqNumber());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD SIGNATURE CHECK: " + data.getSignatureCheck());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD TERMINAL ID: " + data.getTerminalId());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD UID: " + data.getUid());
                        Log.i("JavaSample", "CardPaymentPurchase: Success, CARD VARIABLE SYMBOL: " + data.getVariableSymbol());
                    }
                }
        );
    }

    void sendCardPaymentCancelLastCommand() {
        sendCardPaymentCancelLastCommand(null);
    }

    void sendCardPaymentCancelLastCommand(@Nullable final NativeProtocolPrinterType printerType) {
        NativeProtocolJavaSampleHelper.sendCardPaymentCancelLastCommand(
                UUID.randomUUID().toString(),
                -15.50,
                printerType,
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

    void sendSimpleNonFiscalDocumentPrinterSelectCommands() {
        sendFrPrinterTypesCommand(types -> sendSimpleNonFiscalDocumentCommands(getFirstOrNull(types)));
    }

    void sendFtPrintLocalImagePrinterSelectCommand() {
        sendFrPrinterTypesCommand(types -> sendFtPrintLocalImageCommand(getFirstOrNull(types)));
    }

    void sendCardPaymentPurchasePrinterSelectCommand() {
        sendFrPrinterTypesCommand(types -> sendCardPaymentPurchaseCommand(getFirstOrNull(types)));
    }

    private void sendFrPrinterTypesCommand(@NonNull final PrinterTypesCallback callback) {
        NativeProtocolJavaSampleHelper.sendFrPrinterTypesCommand(
                resource -> {
                    if (resource instanceof Resource.Loading) {
                        Log.i("JavaSample", "FrPrinterTypes: In progress.");
                    } else if (resource instanceof Resource.Failure) {
                        Log.i("JavaSample", "FrPrinterTypes: Failure, type: " + ((Resource.Failure<FailureType.NativeProtocol>) resource).getType());
                    } else if (resource instanceof Resource.Success) {
                        final Resource.Success<NativeProtocolResponse.FrPrinterTypes> successResource = ((Resource.Success<NativeProtocolResponse.FrPrinterTypes>) resource);
                        final NativeProtocolResponse.FrPrinterTypes data = successResource.getData();
                        Log.i("JavaSample", "FrPrinterTypes: Success, result: " + data.toString());

                        callback.onSuccess(new ArrayList<>(data.getTypes()));
                    }
                }
        );
    }

    private interface PrinterTypesCallback {
        void onSuccess(ArrayList<NativeProtocolPrinterType> types);
    }

    // Note: This is just a dummy example simulating the first pick from the obtained collection.
    @Nullable
    private static NativeProtocolPrinterType getFirstOrNull(@NonNull final ArrayList<NativeProtocolPrinterType> types) {
        if (types.isEmpty()) {
            return null;
        } else {
            return types.get(0);
        }
    }
}
