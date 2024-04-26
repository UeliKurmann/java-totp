package dev.samstevens.totp.qr;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static dev.samstevens.totp.IOUtils.writeFile;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ZxingPngQrGeneratorTest {

    @Test
    public void testSomething() throws QrGenerationException, IOException {
        ZxingPngQrGenerator generator = new ZxingPngQrGenerator();

        QrData data = new QrData.Builder()
                .label("example@example.com")
                .secret("EX47GINFPBK5GNLYLILGD2H6ZLGJNNWB")
                .issuer("AppName")
                .digits(6)
                .period(30)
                .build();

        writeFile(generator.generate(data), "./target/test_qr.png");
    }

    @Test
    public void testMimeType() {
        assertEquals("image/png", new ZxingPngQrGenerator().getImageMimeType());
    }



    private QrData getData() {
        return new QrData.Builder()
                .label("example@example.com")
                .secret("EX47GINFPBK5GNLYLILGD2H6ZLGJNNWB")
                .issuer("AppName")
                .digits(6)
                .period(30)
                .build();
    }
}
