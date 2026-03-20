package org.example.mediqback.user.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
public class Aes256 {

    private static String SECRET_KEY;

    @Value("${project.aes.key}")
    public void setSecretKey(String key) {
        SECRET_KEY = key;
    }

    public static String encrypt(byte[] data) {
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);

            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(data);

            byte[] ivAndEncrypted = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, ivAndEncrypted, 0, iv.length);
            System.arraycopy(encrypted, 0, ivAndEncrypted, iv.length, encrypted.length);

            // 핵심 수정: 쿠키에서 특수문자가 깨지지 않게 UrlEncoder 사용!
            return Base64.getUrlEncoder().withoutPadding().encodeToString(ivAndEncrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static byte[] decrypt(byte[] encryptedData) {
        try {
            // 핵심 수정: UrlDecoder 사용!
            byte[] decoded = Base64.getUrlDecoder().decode(encryptedData);

            byte[] iv = Arrays.copyOfRange(decoded, 0, 16);
            byte[] ciphertext = Arrays.copyOfRange(decoded, 16, decoded.length);

            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}