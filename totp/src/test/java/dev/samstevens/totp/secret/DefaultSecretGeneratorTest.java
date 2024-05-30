package dev.samstevens.totp.secret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultSecretGeneratorTest {

    @Test
    public void testSecretGenerated() {
        DefaultSecretGenerator generator = new DefaultSecretGenerator();
        String secret = generator.generate();
        assertNotNull(secret);
        assertFalse(secret.isEmpty());
    }

    @Test
    public void testCharacterLengths() {
        for (int charCount : new int[]{16, 32, 64, 128}) {
            DefaultSecretGenerator generator = new DefaultSecretGenerator(charCount);
            String secret = generator.generate();
            assertEquals(charCount, secret.length());
        }
    }

    @Test
    public void testValidBase32Encoded() {
        DefaultSecretGenerator generator = new DefaultSecretGenerator();
        String secret = generator.generate();

        // Test the string contains only A-Z, 2-7 with optional ending =s
        assertTrue(secret.matches("^[A-Z2-7]+=*$"));

        // And the length must be a multiple of 8
        assertEquals(0, secret.length() % 8);
    }
}
