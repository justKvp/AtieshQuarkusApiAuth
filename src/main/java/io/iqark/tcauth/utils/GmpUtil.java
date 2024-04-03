package io.iqark.tcauth.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GmpUtil {
    // convert to integer (little-endian)
    public static BigInteger gmpImport(byte[] source, ByteOrder order) {
        return new BigInteger(convertBytesInLittleEndian(source, order));
    }

    public static byte[] gmpExport(BigInteger source, ByteOrder order) {
        return convertBigIntegerInLittleEndian(source, order);
    }

    private static byte[] convertBigIntegerInLittleEndian(BigInteger source, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.allocate(source.toByteArray().length).order(order);
        buffer.put(source.toByteArray());
        return buffer.array();
    }

    private static byte[] convertBytesInLittleEndian(byte[] source, ByteOrder order) {
        ByteBuffer buffer = ByteBuffer.allocate(source.length).order(order);
        buffer.put(source);
        return buffer.array();
    }
}
