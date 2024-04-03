package io.iqark.tcauth.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Sha1Util {
    public static byte[] makeSha1Solo(String text, Boolean inBinaryMode) throws NoSuchAlgorithmException {
        byte[] textBytes = makeSoloDigest(text.getBytes(StandardCharsets.UTF_8));
        return inBinaryMode ? convertBytesInBinaryMode(textBytes) : textBytes;
    }

    public static byte[] makeSha1Double(byte[] arg1, byte[] arg2, Boolean inBinaryMode) throws NoSuchAlgorithmException {
        byte[] result = makeDoubleDigest(arg1, arg2);
        return inBinaryMode ? convertBytesInBinaryMode(result) : result;
    }

    private static byte[] makeSoloDigest(byte[] source) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(source);
        return md.digest();
    }

    private static byte[] makeDoubleDigest(byte[] source1, byte[] source2) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(source1);
        md.update(source2);
        return md.digest();
    }

    private static byte[] convertBytesInBinaryMode(byte[] source) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : source) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString().getBytes(StandardCharsets.UTF_8);
    }
}
