/**
 * 
 */
package com.zc.app.utils.crypto;

public class BinaryHex {

	private static final String[] hex = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static String getHexString(byte b) {

		return (hex[(0xFF & b) / 16] + hex[(0xFF & b) % 16]);
	}

	/**
	 * 将十六进制不可见字符数转换成可见字符
	 * 
	 * @param buf
	 * @return
	 */
	public static String getHexString(byte[] buf) {

		if (null == buf) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (byte a : buf) {
			builder.append(getHexString(a));
		}
		return builder.toString();
	}

	/**
	 * 可见字符转换为16进制不可见字符
	 * 
	 * @param buf
	 * @return
	 */
	public static byte[] getByteArray(String buf) {

		if (buf == null || buf.length() == 0 || buf.length() % 2 != 0) {
			return null;
		}

		byte[] b = new byte[buf.length() / 2];

		for (int i = 0; i < buf.length(); i += 2) {
			b[i / 2] = (byte) Integer.decode("0X" + buf.substring(i, i + 2))
					.intValue();
		}
		return b;

	}

	/**
	 * 位取反
	 * 
	 * @param buf
	 * @return
	 */
	public static byte[] doNot(byte[] buf) {
		byte[] ret = new byte[buf.length];

		for (int i = 0; i < buf.length; i++) {
			ret[i] = (byte) (buf[i] ^ (byte) 0xFF);
		}
		return ret;
	}

	/**
	 * 位取反
	 * 
	 * @param buf
	 * @return
	 */
	public static byte[] doNot(String buf) {

		byte[] temp = getByteArray(buf);
		if (temp == null) {
			return null;
		}

		return doNot(temp);

	}

	/**
	 * 异或操作
	 * 
	 * @param buf1
	 * @param buf2
	 * @return
	 */
	public static byte[] doXor(byte[] buf1, byte[] buf2) {

		if (buf1 == null || buf2 == null || buf1.length != buf2.length) {
			return null;
		}

		int len = buf1.length;

		byte[] data = new byte[len];

		for (int i = 0; i < len; i++) {
			data[i] = (byte) (buf1[i] ^ buf2[i]);
		}

		return data;
	}

	/**
	 * 比较字节
	 * 
	 * @param buf1
	 * @param buf2
	 * @return
	 */
	public static boolean compareByte(byte[] buf1, byte[] buf2) {

		if (buf1 == null || buf2 == null || buf1.length != buf2.length) {
			return false;
		}

		int len = buf1.length;
		for (int i = 0; i < len; i++) {

			if (buf1[i] != buf2[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 查找buf中find的位置
	 * 
	 * @param buf
	 * @param find
	 * @return
	 */
	public static int findByte(byte[] buf, byte[] find) {
		int i = 0;
		int maxLen = buf.length - find.length;
		for (; i < maxLen; i++) {
			if (compareByte(buf, find, i) == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 十六进制转换成十进制可见字符，用于EDEP金额表示
	 * 
	 * @param buf
	 * @return
	 */
	public static String getDemicalFromHex(String hex) {

		String value = String.valueOf(Long.parseLong(hex, 16));

		if (value.length() % 2 != 0) {
			value = "0" + value;
		}

		return value;
	}

	/**
	 * 將十进制数字转换成十六进制数字，在EDEP中使用,不能超过LONG型
	 * 
	 * @param buf
	 * @return
	 */
	public static String getHexFromDecimal(String decimal) {

		String hex = Long.toHexString(Long.parseLong(decimal));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex;
	}

	private static int compareByte(byte[] buf, byte[] find, int index) {
		int i = 0;
		for (; i < find.length; i++) {
			// 判断index开始的内容是否和find相等
			if (buf[i + index] != find[i]) {
				break;
			}
		}
		if (i == find.length) {
			return 0;
		}
		return -1;
	}

}
