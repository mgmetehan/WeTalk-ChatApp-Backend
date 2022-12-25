package com.mgmetehan.WeTalk.shared.encryption;


import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class Encryption {
    private final SecureRandom secureRandom = new SecureRandom();
    private static final int GCM_IV_LENGTH = 12;
    private static final String DATA_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH = 128;
    private static final byte[] PROTOCOL_VERSION = "ProtocolVersion1".getBytes(StandardCharsets.UTF_8);

    @SneakyThrows
    public byte[] encrypt(String plaintext, SecretKey secretKey, byte[] associatedData) {
        byte[] iv = new byte[GCM_IV_LENGTH];
        secureRandom.nextBytes(iv);
        final var cipher = Cipher.getInstance(DATA_TRANSFORMATION);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        if (associatedData != null) {
            cipher.updateAAD(associatedData);
        }

        var cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        var byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);

        return byteBuffer.array();
    }

    @SneakyThrows
    public String decrypt(byte[] cipherMessage, SecretKey secretKey, byte[] charset) {
        final Cipher cipher = Cipher.getInstance(DATA_TRANSFORMATION);
        //use first 12 bytes for iv
        AlgorithmParameterSpec gcmIv = new GCMParameterSpec(TAG_LENGTH, cipherMessage, 0, GCM_IV_LENGTH);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmIv);

        if (charset != null) {
            cipher.updateAAD(charset);
        }
        //use everything from 12 bytes on as ciphertext
        byte[] plainText = cipher.doFinal(cipherMessage, GCM_IV_LENGTH, cipherMessage.length - GCM_IV_LENGTH);

        return new String(plainText, StandardCharsets.UTF_8);
    }

    public byte[] getBytes() {
        return PROTOCOL_VERSION;
    }

    public SecretKey getSecretKey() {
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return new SecretKeySpec(key, "AES");
    }

/*    public static void main(String[] args) {
        Encryption encryption = new Encryption();
        var secretKey = encryption.getSecretKey();
        var charSet = encryption.getBytes();
        var message = "Metehan Gultekin 80 @ & * ( )";
        var encryptMessage = encryption.encrypt(message, secretKey, charSet);
        var decryptedMessage = encryption.decrypt(encryptMessage, secretKey, charSet);
        System.out.println("Original message: " + message + "\nEncrypt message: " + encryptMessage + "\nDecrypted message: " + decryptedMessage);
    }*/
}