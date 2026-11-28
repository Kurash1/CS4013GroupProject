package user;

import javax.crypto.SecretKey;

import sec.PasswordEncryption;
import ui.*;
import uni.*;

public class StudentUser implements IUser {
	public String username;
	private Student student;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	public StudentUser(String username, String password, Student student) throws Exception {
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
		return student;
	}

	public byte[] getPasswordHashed() {
		return passwordHashed;
	}

	public SecretKey getPasswordSalt() {
		return passwordSalt;
	}

	public IMenu getMenu() {
		return new StudentMenu(student, null);
	}
}
