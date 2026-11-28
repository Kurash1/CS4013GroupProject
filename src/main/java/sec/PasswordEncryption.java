package sec;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class PasswordEncryption {

	private static final String ALGORITHM = "AES";

	public static byte[] encrypt(String password, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(password.getBytes());
	}

	public static String decrypt(byte[] encryptedPassword, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(encryptedPassword);
		return new String(decrypted);
	}

	public static SecretKey getNewKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
		SecureRandom secureRandom = new SecureRandom();
		keyGenerator.init(256, secureRandom);
		return keyGenerator.generateKey();
	}
}
