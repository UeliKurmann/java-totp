package dev.samstevens.totp.qr;

import dev.samstevens.totp.exceptions.QrGenerationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NayukiQrGeneratorTest {

    @Test
    public void testSomething() throws QrGenerationException, IOException {
        NayukiQrGenerator generator = new NayukiQrGenerator();
        Files.write(Paths.get("./target/test_qr.png"), generator.generate(getData()));
    }

    @Test
    public void testMimeType() {
        assertEquals("image/png", new NayukiQrGenerator().getImageMimeType());
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
