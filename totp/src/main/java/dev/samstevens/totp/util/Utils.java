package dev.samstevens.totp.util;

public class Utils {
    private static final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    // Class not meant to be instantiated
    private Utils() {
    }

    /**
     * Given the raw data of an image and the mime type, returns
     * a data URI string representing the image for embedding in
     * HTML/CSS.
     *
     * @param data The raw bytes of the image.
     * @param mimeType The mime type of the image.
     * @return The data URI string representing the image.
     */
    public static String getDataUriForImage(byte[] data, String mimeType) {
        String encodedData = new String(encoder.encode(data));
        return String.format("data:%s;base64,%s", mimeType, encodedData);
    }
}
