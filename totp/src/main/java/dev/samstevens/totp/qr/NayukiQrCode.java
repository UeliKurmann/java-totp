package dev.samstevens.totp.qr;

import dev.samstevens.totp.exceptions.QrGenerationException;
import io.nayuki.qrcodegen.QrCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class NayukiQrCode implements QrGenerator{
    @Override
    public String getImageMimeType() {
        return "image/png";
    }

    @Override
    public byte[] generate(QrData data) throws QrGenerationException {
        QrCode qr0 = QrCode.encodeText(data.getUri(), QrCode.Ecc.HIGH);
        BufferedImage img = toImage(qr0, 4, 0);  // See QrCodeGeneratorDemo
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ImageIO.write(img, "png", baos);
            return baos.toByteArray();
        }catch(IOException e){
            throw new QrGenerationException(e.getMessage(), e);
        }


    }

    public static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }
}
