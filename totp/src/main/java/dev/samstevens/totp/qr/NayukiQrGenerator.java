package dev.samstevens.totp.qr;

import dev.samstevens.totp.exceptions.QrGenerationException;
import io.nayuki.qrcodegen.QrCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class NayukiQrGenerator implements QrGenerator{

    private static final int DARK_COLOR = 0xFFFFFF;
    private static final int LIGHT_COLOR = 0x000000;

    private final int scale;
    private final int border;

    public NayukiQrGenerator(int scale, int border){
        this.scale = scale;
        this.border = border;
    }

    public NayukiQrGenerator(){
        this(4,0);
    }

    @Override
    public String getImageMimeType() {
        return "image/png";
    }



    @Override
    public byte[] generate(QrData data) throws QrGenerationException {
        Objects.requireNonNull(data, "data must not be null.");
        QrCode qrCode = QrCode.encodeText(data.getUri(), QrCode.Ecc.HIGH);
        BufferedImage img = toImage(qrCode, this.scale, this.border);

        try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
            ImageIO.write(img, "png", out);
            return out.toByteArray();
        }catch(IOException e){
            throw new QrGenerationException(e.getMessage(), e);
        }
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        Objects.requireNonNull(qr);

        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? DARK_COLOR : LIGHT_COLOR);
            }
        }
        return result;
    }
}
