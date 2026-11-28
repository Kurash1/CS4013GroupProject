package user;

import java.security.MessageDigest;
import java.util.EnumSet;
import javax.crypto.SecretKey;
import sec.*;

public class Login {
	private User account;

	public EnumSet<Permission> getPermissions() {
		return account.getPermissions();
	}

	private Login(User user) {
		account = user;
	}

	public static Login TryLogin(User user, String password) throws Exception {
		SecretKey salt = user.getPasswordSalt(Permission.SUPER_PERMISSIONS);
		byte[] encryptedAttempt = PasswordEncryption.encrypt(password, salt);

		byte[] hash = user.getPasswordHashed(Permission.SUPER_PERMISSIONS);

		if (MessageDigest.isEqual(encryptedAttempt, hash))
			return new Login(user);
		return null;
	}
}
