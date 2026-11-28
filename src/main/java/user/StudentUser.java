package user;

import javax.crypto.SecretKey;

import db.*;
import sec.PasswordEncryption;
import ui.*;
import uni.*;

public class StudentUser implements IUser {
	public String username;
	private String student;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	protected StudentUser(String username) {
		this.username = username;
		users.put(username, this);
	}

	public StudentUser(String username, String password, String student) throws Exception {
		this.username = username;
		this.student = student;
		SecretKey key = PasswordEncryption.getNewKey();
		this.passwordHashed = PasswordEncryption.encrypt(password, key);
		this.passwordSalt = key;
		users.put(username, this);
	}

	public String getUsername() {
		return username;
	}

	public Student getStudent() {
		return Student.getStudent(student);
	}

	public byte[] getPasswordHashed() {
		return passwordHashed;
	}

	public SecretKey getPasswordSalt() {
		return passwordSalt;
	}

	public IMenu getMenu() {
		return new StudentMenu(getStudent(), null);
	}

	public static void serialize(CSVWriter writer) {
		for (IUser user : users.values())
			if (user instanceof StudentUser student) {
				writer.startObject("student-user");
				writer.writeString(student.username);
				writer.writeBytes(student.passwordHashed);
				writer.writeKey(student.passwordSalt);
				writer.writeString(student.student);
				writer.endObject();
			}
	}

	public static void deserialize(CSVField field) {
		if (field.getType().equals("student-user") == false)
			return;

		StudentUser user = new StudentUser(field.getString(0));
		user.passwordHashed = field.getBytes(1);
		user.passwordSalt = field.getKey(2);
		user.student = field.getString(3);
	}
}
