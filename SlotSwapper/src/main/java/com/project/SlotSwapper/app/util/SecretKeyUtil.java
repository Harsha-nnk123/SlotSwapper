package com.project.SlotSwapper.app.util;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretKeyUtil {
    public SecretKeyUtil(){}

    public static String generateBase64HS256Key() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error generating HS256 key", e);
        }
    }

    public static boolean looksLikeBase64(String s) {
        return s != null && s.matches("^[A-Za-z0-9+/]+={0,2}$") && (s.length() % 4 == 0);
    }
}
