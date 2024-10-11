package com.cisco.wccai.grpc.utils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class Utils {

    private static final Properties PROPERTY = LoadProperties.loadProperties();
    private static final String AUDIO_ENCODING_TYPE = PROPERTY.getProperty("AUDIO_ENCODING_TYPE");
    private static final int BUFFER_SIZE = Integer.parseInt(PROPERTY.getProperty("BUFFER_SIZE"));
    private static final String LINEAR_16 = "LINEAR_16";

    Utils()
    {

    }
    public static byte[] getAudioBytes() {
        byte[] audioBytes;
        if (LINEAR_16.equalsIgnoreCase(AUDIO_ENCODING_TYPE)) {
            audioBytes = new byte[2 * BUFFER_SIZE];
        } else {
            audioBytes = new byte[BUFFER_SIZE];
        }
        Arrays.fill(audioBytes, (byte) 1);
        return audioBytes;
    }

    public static InputStream getInputStreamForBookAFlight() {
        return Utils.class.getClassLoader().getResourceAsStream("audio/flightbook.wav");
    }

}
