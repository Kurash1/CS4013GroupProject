package user;

import java.security.MessageDigest;
import java.util.EnumSet;
import javax.crypto.SecretKey;
import sec.*;

public class Login {
	private IUser account;

	private Login(IUser user) {
		account = user;
	}

	public static Login TryLogin(IUser user, String password) throws Exception {
		SecretKey salt = user.getPasswordSalt();
		byte[] encryptedAttempt = PasswordEncryption.encrypt(password, salt);

		byte[] hash = user.getPasswordHashed();

		if (MessageDigest.isEqual(encryptedAttempt, hash))
			return new Login(user);
		return null;
	}
}
