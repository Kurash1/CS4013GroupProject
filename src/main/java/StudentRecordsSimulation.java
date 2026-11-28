/* INTEGRATION HANDOFF: Member 4 will replace the sample-data startup with authenticated CSV-backed data. */
/**
 * Provides a temporary demonstration entry point using sample student data and
 * an authenticated demonstration account.
 * 
 * @see StudentMenu
 */

public class StudentRecordsSimulation {

	/**
	 * Starts the demonstration student menu. CSV-backed student retrieval remains
	 * pending.
	 * 
	 * @param args command-line arguments (unused)
	 * @throws Exception if authentication or menu startup fails
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("====================================");
		System.out.println("       STUDENT RECORDS SYSTEM       ");
		System.out.println("====================================");

		/*
		 * SAMPLE STUDENT DATA
		 *
		 * This is temporary demonstration data.
		 * In the completed application, student information should
		 * be retrieved from the system's CSV files.
		 */

		Student student = new Student(
				"John",
				"Murphy",
				"12345678",
				"john.murphy@studentmail.ul.ie",
				"Castletroy, Limerick",
				"Dublin, Ireland",
				"0871234567");

		/*
		 * TRANSCRIPT
		 *
		 * Transcript currently depends on Programme, Progression
		 * and AwardClassification, which are being developed
		 * by other group members.
		 *
		 * We temporarily pass null so that the student menu
		 * can be demonstrated before those classes are integrated.
		 */

		Transcript transcript = null;

		/*
		 * STUDENT MENU
		 *
		 * In the completed application, the logged-in student's
		 * own Student object and transcript should be passed here.
		 *
		 * Login and user authentication should be connected
		 * through Member 4's system-management classes.
		 */

		// Pass Jesse's authenticated login to the student menu (Member 4(Jesse)
		// Login.java)
		// Temporary demonstration account, not CSV-backed authentication.
		User demo = new User(UserType.Student, "12345678", "John Murphy", "demo-only");
		Login login = Login.TryLogin(demo, "demo-only");
		if (login == null)
			return;
		StudentMenu studentMenu = new StudentMenu(student, transcript, login);

		// Start the student menu.
		studentMenu.Open();

		System.out.println("\nThank you for using the Student Records System.");
		System.out.println("Application closed.");
	}
}

/*
 * // Member 4 must supply the authenticated student and their saved records.
 * private static Student loadAuthenticatedStudent() {
 * throw new UnsupportedOperationException("Not yet implemented");
 * }
 * private static Transcript loadStudentTranscript(Student student) {
 * throw new UnsupportedOperationException("Not yet implemented");
 * }
 */
