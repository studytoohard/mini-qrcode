package cn.hannahl.mini;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<EncodeHintType, Object> hints = new HashMap() {
        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            put(EncodeHintType.CHARACTER_SET, "UTF-8");
            put(EncodeHintType.MARGIN, 1);
        }
    };

    public static void main(String[] args) throws Exception {
        String content = "123";
        String fileName = "123.jpg";
        produceQrcode(content, fileName);
    }

    private static void produceQrcode(String content, String fileName) throws WriterException, IOException {
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, 300, 300, Main.hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        FileOutputStream fo = new FileOutputStream(new File(fileName));
        ImageIO.write(image, fileSuffix, fo);
    }

}
