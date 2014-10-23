package com.zc.app.utils.crypto;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.zc.app.utils.ZCLog;

public class RSA {
	KeyPairGenerator kpg;
	KeyPair kp;
	RSAPublicKey publicKey;
	PrivateKey privateKey;
	byte[] encryptedBytes, decryptedBytes;
	Cipher cipher;
	String encrypted, decrypted;

	public void setPublicKey(String publicKeyString,String exp) throws NoSuchAlgorithmException, InvalidKeySpecException {
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(publicKeyString), new BigInteger(exp));
		KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
		publicKey = (RSAPublicKey)publicKeyFactory.generatePublic(publicKeySpec);
	}
	
	public void setPublicKey(RSAPublicKey key){
		publicKey = key;
	}
	
	public void init(int keySize) throws NoSuchAlgorithmException{
		kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(keySize);
		kp = kpg.genKeyPair();
		publicKey = (RSAPublicKey) kp.getPublic();
		privateKey = kp.getPrivate();
	}

	public String Encrypt(String plain) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		encryptedBytes = cipher.doFinal(plain.getBytes());
		
		ZCLog.i("len", String.valueOf(encryptedBytes.length));
		
		encrypted = BinaryHex.getHexString(encryptedBytes);
		return encrypted;

	}

	public String Decrypt(String result) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		decryptedBytes = cipher.doFinal(BinaryHex.getByteArray(result));
		decrypted = new String(decryptedBytes);
		return decrypted;

	}

//	public String bytesToString(byte[] b) {
//		byte[] b2 = new byte[b.length + 1];
//		b2[0] = 1;
//		System.arraycopy(b, 0, b2, 1, b.length);
//		return new BigInteger(b2).toString(16);
//	}
//
//	public byte[] stringToBytes(String s) {
//		byte[] b2 = new BigInteger(s, 16).toByteArray();
//		return Arrays.copyOfRange(b2, 1, b2.length);
//	}
}
