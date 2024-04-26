package dev.samstevens.totp.qr;


import dev.samstevens.totp.exceptions.QrGenerationException;

@Deprecated(forRemoval = true)
public class ZxingPngQrGenerator implements QrGenerator {


    public ZxingPngQrGenerator() {

    }


    public String getImageMimeType() {
        return "image/png";
    }

    @Override
    public byte[] generate(QrData data) throws QrGenerationException {
        return new NayukiQrCode().generate(data);
    }
}
