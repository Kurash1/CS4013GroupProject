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

		users.put(id, this);
	}

	private User() {
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

	public static void serialize(CSVWriter writer) {
		for (User user : users.values()) {
			writer.startObject("user");
			writer.writeInt(user.id);
			writer.writeString(user.name);
			writer.writeBytes(user.passwordHashed);
			writer.writeKey(user.passwordSalt);
			writer.endObject();
		}
	}

	public static void deserialize(CSVField field) {
		User user = new User();
		user.id = field.getInt(0);
		user.name = field.getString(1);
		user.passwordHashed = field.getBytes(2);
		user.passwordSalt = field.getKey(3);
		users.put(user.id, user);
	}
}
