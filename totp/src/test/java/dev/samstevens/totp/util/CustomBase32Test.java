package dev.samstevens.totp.util;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class CustomBase32Test {

    @Test
    @DisplayName("Random comparison of two different Base32 algorithm.")
    public void compareTest() {
        Random random = new SecureRandom();
        org.apache.commons.codec.binary.Base32 encoder = new org.apache.commons.codec.binary.Base32(0, null, false, (byte) '=', CodecPolicy.STRICT);

        for (int i = 0; i < 1_000; i++) {
            byte[] bytes = createArray(random, 32);
            String e1 = Base32.encode(bytes);
            String e2 = encoder.encodeToString(bytes);
            if (!e1.equals(e2)) {
                throw new RuntimeException(e1 + ":" + e2);
            }
            if (0 != Arrays.compare(bytes, Base32.decode(e1))) {
                throw new RuntimeException(e1);
            }
        }
    }

    private byte[] createArray(Random random, int length) {
        byte[] result = new byte[length];
        random.nextBytes(result);
        return result;
    }

}
