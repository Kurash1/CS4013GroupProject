import java.util.EnumSet;

public enum Permission {
	readStudent,
	writeStudent,

	readProgramme,
	writeProgramme,

	readModule,
	writeModule,

	readUser,
	writeUser;

	public static final EnumSet<Permission> NO_PERMISSIONS = EnumSet.noneOf(Permission.class);
	public static final EnumSet<Permission> STUDENT_PERMISSIONS = EnumSet.of(readStudent,
			readProgramme, readModule);
	public static final EnumSet<Permission> FACULTY_PERMISSIONS = EnumSet.of(readStudent, writeStudent,
			readProgramme, writeProgramme, readModule, writeModule);
	public static final EnumSet<Permission> ADMIN_PERMISSIONS = EnumSet.of(readStudent, writeStudent,
			readProgramme, writeProgramme, readModule, writeModule, readUser, writeUser);
	public static final EnumSet<Permission> SUPER_PERMISSIONS = EnumSet.allOf(Permission.class);

	public static void CheckOrFail(EnumSet<Permission> set, Permission perm) throws Exception {
		if (set.contains(perm))
			return;

		throw new IllegalArgumentException();
	}
}
