package io.iqark.tcauth.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteUtil {

    //Функция str_pad в PHP дополняет строку другой строкой до заданной длины.
    //
    //Она имеет следующие параметры:
    //
    //Первый параметр принимает строку.
    //
    //Второй параметр — количество символов, до которого следует дополнить строку.
    //
    //Третий параметр — то, чем следует заполнять строку.
    //
    //Четвертый необязательный параметр задает, с какой стороны заполнять строку. Этот параметр может принимать следующие значения:
    //
    //STR_PAD_LEFT — дополнять строку слева;
    //
    //STR_PAD_RIGHT — дополнять строку справа (это значение по умолчанию).

    public static byte[] bytePad(byte[] source, int byteSize)
    {
        // Создание нового массива размером 32
        byte[] result = new byte[byteSize];

        // Копирование байтов из source в result
        System.arraycopy(source, 0, result, 0, Math.min(source.length, byteSize));

        // Если source.length < byteSize, дополнить массив result нулевыми байтами
        if (source.length < byteSize) {
            for (int i = source.length; i < byteSize; i++) {
                result[i] = 0;
            }
        }

        return result;
    }

    public static byte[] concatBytes(byte[] arg1, byte[] arg2) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(arg1);
        outputStream.write(arg2);
        return outputStream.toByteArray();
    }
}
