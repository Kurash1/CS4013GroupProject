package user;

import javax.crypto.SecretKey;

import sec.PasswordEncryption;
import ui.*;

public class FacultyUser implements IUser {
	public String username;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	public FacultyUser(String username, String password) throws Exception {
		this.username = username;
		SecretKey key = PasswordEncryption.getNewKey();
		this.passwordHashed = PasswordEncryption.encrypt(password, key);
		this.passwordSalt = key;
		users.put(username, this);
	}

	public String getUsername() {
		return username;
	}

	public byte[] getPasswordHashed() {
		return passwordHashed;
	}

	public SecretKey getPasswordSalt() {
		return passwordSalt;
	}

	public IMenu getMenu() {
		return new LoginMenu();
	}
}
