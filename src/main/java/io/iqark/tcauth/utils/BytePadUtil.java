package io.iqark.tcauth.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BytePadUtil {
    /**************************
     *
     * STR_PAD IMPLEMENTED
     *
     **************************/
    public static byte[] bytePad(byte[] source, int byteSize, ByteOrder order)
    {
        // Создание нового массива размером 32
        byte[] result = new byte[byteSize];

        // Копирование байтов из source в result
        System.arraycopy(source, 0, result, 0, Math.min(source.length, byteSize));

        // Если source.length < 32, дополнить массив result нулевыми байтами
        if (source.length < byteSize) {
            for (int i = source.length; i < byteSize; i++) {
                result[i] = 0;
            }
        }

        // Преобразование в little-endian
        ByteBuffer buffer = ByteBuffer.wrap(result);
        buffer.order(order);

        // Получение нового массива в little-endian
        return buffer.array();
    }
}
