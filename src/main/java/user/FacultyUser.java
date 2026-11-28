package user;

import javax.crypto.SecretKey;

import sec.PasswordEncryption;
import ui.*;
import db.*;

public class FacultyUser implements IUser {
	public String username;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	protected FacultyUser(String username) {
		this.username = username;
		users.put(username, this);
	}

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

	public static void serialize(CSVWriter writer) {
		for (IUser user : users.values())
			if (user instanceof FacultyUser faculty) {
				writer.startObject("faculty-user");
				writer.writeString(faculty.username);
				writer.writeBytes(faculty.passwordHashed);
				writer.writeKey(faculty.passwordSalt);
				writer.endObject();
			}
	}

	public static void deserialize(CSVField field) {
		if (field.getType().equals("faculty-user") == false)
			return;

		FacultyUser user = new FacultyUser(field.getString(0));
		user.passwordHashed = field.getBytes(1);
		user.passwordSalt = field.getKey(2);
	}
}
