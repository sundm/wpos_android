package com.zc.app.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Algorithm {
	/** 
	 * 3DES CBC 加密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是24的倍数 
	 * @return 返回加密后的数据 
	 * @throws Exception 
	 */  
	public static byte[] CBCencrypt3DES(byte[] src, byte[] key, byte[] iv) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("加密数据长度不是8的整数倍");
		if (key.length != 24)
			throw new IllegalArgumentException("密钥长度错误");
		if (iv.length != 8)
			throw new IllegalArgumentException("初始向量长度错误");
	    // DES算法要求有一个可信任的随机数源   
	    //SecureRandom sr = new SecureRandom();  
	    // 从原始密匙数据创建DESKeySpec对象   
	    DESedeKeySpec dks = new DESedeKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // IV
	    IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    // Cipher对象实际完成加密操作   
	    Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.ENCRYPT_MODE, securekey, ivSpec);  
	    // 现在，获取数据并加密   
	    // 正式执行加密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * 3DES ECB 加密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是24的倍数 
	 * @return 返回加密后的数据 
	 * @throws Exception 
	 */  
	public static byte[] ECBencrypt3DES(byte[] src, byte[] key) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("加密数据长度不是8的整数倍");
		if (key.length != 24)
			throw new IllegalArgumentException("密钥长度错误");
	    // DES算法要求有一个可信任的随机数源   
	    //SecureRandom sr = new SecureRandom();  
	    // 从原始密匙数据创建DESKeySpec对象   
	    DESedeKeySpec dks = new DESedeKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // Cipher对象实际完成加密操作   
	    Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.ENCRYPT_MODE, securekey);  
	    // 现在，获取数据并加密   
	    // 正式执行加密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * 3DES ECB 解密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是8的倍数 
	 * @return 返回解密后的原始数据 
	 * @throws Exception 
	 */  
	public static byte[] ECBdecrypt3DES(byte[] src, byte[] key) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("解密数据长度不是8的整数倍");
		if (key.length != 24)
			throw new IllegalArgumentException("密钥长度错误");
	    // 从原始密匙数据创建一个DESKeySpec对象   
		DESedeKeySpec dks = new DESedeKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	    // Cipher对象实际完成解密操作   
	    Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.DECRYPT_MODE, securekey);  
	    // 现在，获取数据并解密   
	    // 正式执行解密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * 3DES CBC 解密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是8的倍数 
	 * @return 返回解密后的原始数据 
	 * @throws Exception 
	 */  
	public static byte[] CBCdecrypt3DES(byte[] src, byte[] key, byte[] iv) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("解密数据长度不是8的整数倍");
		if (key.length != 24)
			throw new IllegalArgumentException("密钥长度错误");
		if (iv.length != 8)
			throw new IllegalArgumentException("初始向量长度错误");
	    // 从原始密匙数据创建一个DESKeySpec对象   
	    DESKeySpec dks = new DESKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // IV
	    IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    // Cipher对象实际完成解密操作   
	    Cipher cipher = Cipher.getInstance("DESede/CBC");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);  
	    // 现在，获取数据并解密   
	    // 正式执行解密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * DES CBC 加密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是24的倍数 
	 * @return 返回加密后的数据 
	 * @throws Exception 
	 */  
	public static byte[] CBCencryptDES(byte[] src, byte[] key, byte[] iv) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("加密数据长度不是8的整数倍");
		if (key.length != 8)
			throw new IllegalArgumentException("密钥长度错误");
		if (iv.length != 8)
			throw new IllegalArgumentException("初始向量长度错误");
	    // DES算法要求有一个可信任的随机数源   
	    //SecureRandom sr = new SecureRandom();  
	    // 从原始密匙数据创建DESKeySpec对象   
	    DESKeySpec dks = new DESKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // IV
	    IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    // Cipher对象实际完成加密操作   
	    Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.ENCRYPT_MODE, securekey, ivSpec);  
	    // 现在，获取数据并加密   
	    // 正式执行加密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * DES ECB 加密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是24的倍数 
	 * @return 返回加密后的数据 
	 * @throws Exception 
	 */  
	public static byte[] ECBencryptDES(byte[] src, byte[] key) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("加密数据长度不是8的整数倍");
		if (key.length != 8)
			throw new IllegalArgumentException("密钥长度错误");
	    // DES算法要求有一个可信任的随机数源   
	    //SecureRandom sr = new SecureRandom();  
	    // 从原始密匙数据创建DESKeySpec对象   
	    DESKeySpec dks = new DESKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // Cipher对象实际完成加密操作   
	    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.ENCRYPT_MODE, securekey);  
	    // 现在，获取数据并加密   
	    // 正式执行加密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * DES ECB 解密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是8的倍数 
	 * @return 返回解密后的原始数据 
	 * @throws Exception 
	 */  
	public static byte[] ECBdecryptDES(byte[] src, byte[] key) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("解密数据长度不是8的整数倍");
		if (key.length != 8)
			throw new IllegalArgumentException("密钥长度错误");
	    // 从原始密匙数据创建一个DESKeySpec对象   
	    DESKeySpec dks = new DESKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	    // Cipher对象实际完成解密操作   
	    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.DECRYPT_MODE, securekey);  
	    // 现在，获取数据并解密   
	    // 正式执行解密操作   
	    return cipher.doFinal(src);  
	}
	
	/** 
	 * DES CBC 解密 
	 *  
	 * @param src 
	 *            数据源 
	 * @param key 
	 *            密钥，长度必须是8的倍数 
	 * @return 返回解密后的原始数据 
	 * @throws Exception 
	 */  
	public static byte[] CBCdecryptDES(byte[] src, byte[] key, byte[] iv) throws Exception {
		if (src.length%8 != 0)
			throw new IllegalArgumentException("解密数据长度不是8的整数倍");
		if (key.length != 8)
			throw new IllegalArgumentException("密钥长度错误");
		if (iv.length != 8)
			throw new IllegalArgumentException("初始向量长度错误");
	    // 从原始密匙数据创建一个DESKeySpec对象   
	    DESKeySpec dks = new DESKeySpec(key);  
	    // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成   
	    // 一个SecretKey对象   
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    // IV
	    IvParameterSpec ivSpec = new IvParameterSpec(iv);
	    // Cipher对象实际完成解密操作   
	    Cipher cipher = Cipher.getInstance("DES/CBC");  
	    // 用密匙初始化Cipher对象   
	    cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);  
	    // 现在，获取数据并解密   
	    // 正式执行解密操作   
	    return cipher.doFinal(src);
	}
}
