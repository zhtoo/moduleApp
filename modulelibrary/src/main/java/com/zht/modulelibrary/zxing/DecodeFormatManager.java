package com.zht.modulelibrary.zxing;

import com.google.zxing.BarcodeFormat;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @Date 2023/1/9 16:42
 * @Author zhanghaitao
 * @Description
 */
public class DecodeFormatManager {

    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    public static final Set<BarcodeFormat> PRODUCT_FORMATS;
    public static final Set<BarcodeFormat> INDUSTRIAL_FORMATS;
    public static final Set<BarcodeFormat> ONE_D_FORMATS;
    public static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    public static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    public static final Set<BarcodeFormat> AZTEC_FORMATS = EnumSet.of(BarcodeFormat.AZTEC);
    public static final Set<BarcodeFormat> PDF417_FORMATS = EnumSet.of(BarcodeFormat.PDF_417);

    static {
        PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.RSS_14,
                BarcodeFormat.RSS_EXPANDED);

        INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.CODE_128,
                BarcodeFormat.ITF,
                BarcodeFormat.CODABAR);

        ONE_D_FORMATS = EnumSet.copyOf(PRODUCT_FORMATS);

        ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS);
    }


}
