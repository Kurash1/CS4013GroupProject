import java.util.EnumSet;
import java.util.HashMap;
import javax.crypto.SecretKey;

public class User {
	private UserType type;
	private Integer id;
	private String name;
	private byte[] passwordHashed;
	private SecretKey passwordSalt;

	private static HashMap<Integer, User> users = new HashMap<Integer, User>();
	private static HashMap<UserType, EnumSet<Permission>> permissionMapping = new HashMap<UserType, EnumSet<Permission>>();
	static {
		permissionMapping.put(UserType.Student, Permission.STUDENT_PERMISSIONS);
		permissionMapping.put(UserType.Faculty, Permission.FACULTY_PERMISSIONS);
		permissionMapping.put(UserType.Admin, Permission.ADMIN_PERMISSIONS);
		permissionMapping.put(UserType.System, Permission.SUPER_PERMISSIONS);
	}

	public User(UserType type, Integer id, String name, String password) throws Exception {
		this.type = type;
		this.id = id;
		this.name = name;

		SecretKey key = PasswordEncryption.getNewKey();
		this.passwordHashed = PasswordEncryption.encrypt(password, key);
		this.passwordSalt = key;

		users.put(id, this);
	}

	protected User() {
	}

	public EnumSet<Permission> getPermissions() {
		return permissionMapping.getOrDefault(type, Permission.NO_PERMISSIONS);
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
			writer.writeString(user.type.name());
			writer.writeInt(user.id);
			writer.writeString(user.name);
			writer.writeBytes(user.passwordHashed);
			writer.writeKey(user.passwordSalt);
			writer.endObject();
		}
	}

	public static void deserialize(CSVField field) {
		if (field.getType().equals("user") == false)
			return;

		User user = new User();

		user.type = UserType.valueOf(field.getString(0));
		user.id = field.getInt(1);
		user.name = field.getString(2);
		user.passwordHashed = field.getBytes(3);
		user.passwordSalt = field.getKey(4);

		users.put(user.id, user);
	}
}
