package group.fly.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TripleDESEncryption {

	public static String encrypt(String key, String input) throws Exception {

		byte[] bytes = null;
		Security.addProvider(new BouncyCastleProvider());
		Cipher encipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "BC");
		bytes = input.getBytes("US-ASCII");
		byte[] keyByte = getValidKey(key);
		SecretKey sKey = new SecretKeySpec(keyByte, "DESede");
		IvParameterSpec ivSpec = new IvParameterSpec(getValidIV(key, 8));
		// Encrypt
		byte[] enc;
		encipher.init(Cipher.ENCRYPT_MODE, sKey, ivSpec);
		enc = encipher.doFinal(bytes);
		return Base64Utils.base64Encode(enc);
	}

	/**
	 * Decrypt.
	 * 
	 * @param softpinKey
	 *            the softpin key
	 * @param encryptedSoftpin
	 *            the encrypted softpin
	 * @return the string
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 */
	public static String decrypt(String softpinKey, String encryptedSoftpin) throws Exception {

		Cipher _3DESCryptoEngine = Cipher.getInstance("DESede/CBC/PKCS5PADDING");
		String result;

		byte[] key = getValidKey(softpinKey);
		SecretKeySpec keySpec = new SecretKeySpec(key, "DESede");
		IvParameterSpec ivSpec = new IvParameterSpec(getValidIV(softpinKey, 8));

		_3DESCryptoEngine.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		result = new String(_3DESCryptoEngine.doFinal(Base64Utils.base64Decode(encryptedSoftpin)));

		return result;

	}

	/**
	 * Gets the valid key.
	 * 
	 * @param key
	 *            the key
	 * @return the valid key
	 */
	private static byte[] getValidKey(String key) {
		String sTemp;
		if (key.length() > 24) {
			sTemp = key.substring(0, 24);
		} else {
			sTemp = key;
			while (sTemp.length() != 24) {
				sTemp = sTemp + ' ';
			}
		}

		return sTemp.getBytes();
	}

	/**
	 * Gets the valid iv.
	 * 
	 * @param InitVector
	 *            the init vector
	 * @param ValidLength
	 *            the valid length
	 * @return the valid iv
	 */
	private static byte[] getValidIV(String InitVector, int ValidLength) {
		String sTemp;

		if (InitVector.length() > ValidLength) {
			sTemp = InitVector.substring(0, ValidLength);
		} else {
			sTemp = InitVector;
			while (sTemp.length() != ValidLength) {
				sTemp = sTemp + ' ';
			}
		}

		return sTemp.getBytes();
	}

	public static void main(String[] args) throws Exception {

		System.out.println("ENCRYPT DATA=" + encrypt("306f4e6d87680d53be4ba9cb", "123124"));
		System.out.println("KEY=" + decrypt("ec5f44b8a9db501a3f8e712a", "a6eXYOEc3woxlczCAHfdTQ=="));

		// giai ma chuoi pincode luu tren kho itopup
		// System.out.println(decrypt("VNPT_epay_i_topup2oo8$$$",
		// "ZLIqQ56/lA5USjYajReI55Fy/30ljZ6O ".trim()));

		System.out.println(encrypt("VNPT_epay_i_topup2oo8$$$", "112704533181"));

	}

}
