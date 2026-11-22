import java.util.EnumSet;

public class Faculty extends User {
	@Override
	public EnumSet<Permission> getPermissions() {
		return Permission.FACULTY_PERMISSIONS;
	}

	public Faculty(Integer id, String name, String password) throws Exception {
		super(id, name, password);
	}
}
