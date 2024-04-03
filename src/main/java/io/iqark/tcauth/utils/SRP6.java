package io.iqark.tcauth.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import static io.iqark.tcauth.utils.ByteUtil.bytePad;
import static io.iqark.tcauth.utils.ByteUtil.concatBytes;
import static io.iqark.tcauth.utils.GmpUtil.gmpExport;
import static io.iqark.tcauth.utils.GmpUtil.gmpImport;
import static io.iqark.tcauth.utils.Sha1Util.makeSha1;

public class SRP6 {

    private static final BigInteger g = BigInteger.valueOf(7);
    private static final BigInteger n = new BigInteger("894B645E89E1535BBDAD5B8B290650530801B18EBFBF5E8FAB3C82872A3E9BB7", 16);

    public static byte[] generateRandomSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }

    public static byte[] calculateSRP6Verifier(String login, String password, byte[] salt) throws IOException, NoSuchAlgorithmException {
        String loginPass = String.format("%s:%s", login, password).toUpperCase();

        // calculate first hash
        byte[] h1 = makeSha1(loginPass.getBytes(StandardCharsets.UTF_8), true);

        byte[] saltPlusH1 = concatBytes(salt, h1);
        // calculate second hash
        byte[] h2 = makeSha1(saltPlusH1, true);

        // convert to integer (little-endian)
        BigInteger h2bi = gmpImport(h2, ByteOrder.LITTLE_ENDIAN);

        // g^h2 mod N
        BigInteger verifier = g.modPow(h2bi, n);

        // convert back to a byte array (little-endian)
        byte[] verifierBytes = gmpExport(verifier, ByteOrder.LITTLE_ENDIAN);
        System.out.println("verifierBytes: " + Arrays.toString(verifierBytes));

        // pad to 32 bytes, remember that zeros go on the end in little-endian!
        byte[] result = bytePad(verifierBytes, 32);
        System.out.println("result       : " + Arrays.toString(result));
        return result;
    }

    public static boolean verifySRP6(String login, String password, byte[] salt, byte[] verifier) throws IOException, NoSuchAlgorithmException {
        byte[] check = calculateSRP6Verifier(login, password, salt);
        return Arrays.equals(check, verifier);
    }


}
