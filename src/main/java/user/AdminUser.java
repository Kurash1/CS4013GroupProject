package user;

import javax.crypto.SecretKey;

import db.*;
import sec.PasswordEncryption;
import ui.*;

public class AdminUser implements IUser {
	public String username;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	protected AdminUser(String username) {
		this.username = username;
		users.put(username, this);
	}

	public AdminUser(String username, String password) throws Exception {
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

	public static void serialize(CSVWriter writer) {
		for (IUser user : users.values())
			if (user instanceof AdminUser admin) {
				writer.startObject("admin-user");
				writer.writeString(admin.username);
				writer.writeBytes(admin.passwordHashed);
				writer.writeKey(admin.passwordSalt);
				writer.endObject();
			}
	}

	public static void deserialize(CSVField field) {
		if (field.getType().equals("admin-user") == false)
			return;

		AdminUser user = new AdminUser(field.getString(0));
		user.passwordHashed = field.getBytes(1);
		user.passwordSalt = field.getKey(2);
	}
}
