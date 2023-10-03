package io.iqark.tcauth.utils;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
    public static String base64Decode(String input) {
        Base64 decoder = new Base64(true);
        return bytesToString(decoder.decode(input));
    }

    public static String base64Encode(String input) {
        Base64 encoder = new Base64(true);
        String result = bytesToString(encoder.encode(stringToBytes(input)));
        return result.substring(0, result.length() - 2);
    }

    private static byte[] stringToBytes(String value) {
        byte[] result = null;

        try {
            result = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
        }

        return result;
    }

    private static String bytesToString(byte[] value) {
        String result = "";

        try {
            result = new String(value, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
        }

        return result;
    }
}
