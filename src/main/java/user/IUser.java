package user;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import sec.PasswordEncryption;
import ui.*;

public interface IUser {
	public byte[] getPasswordHashed();

	public SecretKey getPasswordSalt();

	public IMenu getMenu();

	public static Map<String, IUser> users = new HashMap<String, IUser>();

	public static IUser getUser(String username) {
		return users.getOrDefault(username, null);
	}

	public static boolean tryLogin(IUser user, String password) throws Exception {
		SecretKey salt = user.getPasswordSalt();
		byte[] encryptedAttempt = PasswordEncryption.encrypt(password, salt);

		byte[] hash = user.getPasswordHashed();

		if (MessageDigest.isEqual(encryptedAttempt, hash))
			return true;
		return false;
	}
}
