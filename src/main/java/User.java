import java.util.EnumSet;
import java.util.HashMap;

import javax.crypto.SecretKey;

public class User {
	private Integer id;
	private String name;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	private static HashMap<Integer, User> users = new HashMap<Integer, User>();

	public User(Integer id, String name, String password) throws Exception {
		this.id = id;
		this.name = name;

		SecretKey key = PasswordEncryption.getNewKey();
		this.passwordHashed = PasswordEncryption.encrypt(password, key);
		this.passwordSalt = key;
	}

	public EnumSet<Permission> getPermissions() {
		return Permission.STUDENT_PERMISSIONS;
	}

	public static User getUser(Integer id, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readUser);
		return users.get(id);
	}

	public Integer getId(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readUser);
		return id;
	}

	public String getName(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readUser);
		return name;
	}

	public byte[] getPasswordHashed(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readUser);
		return passwordHashed;
	}

	public SecretKey getPasswordSalt(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readUser);
		return passwordSalt;
	}
}
