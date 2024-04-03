package io.iqark.tcauth.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {

    public static byte[] makeSha1(byte[] source, Boolean inBinaryMode) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(source);
        byte[] textBytes = md.digest();
        return inBinaryMode ? convertBytesInBinaryMode(textBytes) : textBytes;
    }

    private static byte[] convertBytesInBinaryMode(byte[] source) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : source) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString().getBytes(StandardCharsets.UTF_8);
    }
}
