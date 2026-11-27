import java.util.EnumSet;
import java.util.Scanner;

/**
 * Provides a command-line student menu using permissions obtained from Login.
 * Registration and persistence integration remain pending.
 * 
 * @see Student
 */

public class StudentMenu {

	private final Scanner scanner;
	private final EnumSet<Permission> permissions;
	private final Student student;
	private final Transcript transcript;

	/**
	 * Creates the menu and copies the permissions from the supplied login.
	 * 
	 * @param student    associated student
	 * @param transcript student transcript, which may be null
	 * @param login      authenticated login supplying permissions
	 */
	public StudentMenu(Student student, Transcript transcript, Login login) { // Member 4 (Jesse): Login.java
		this.scanner = new Scanner(System.in);
		this.student = student;
		this.transcript = transcript;
		// Retrieve the student's permissions (Member 4(Jesse) Login.java)
		this.permissions = login.getPermissions().clone();
	}

	/**
	 * Displays menu options until logout and reports action failures.
	 */
	public void displayMenu() {

		int choice;

		do {
			System.out.println("\n===== STUDENT MENU =====");
			System.out.println("1. View Personal Details");
			System.out.println("2. Update Personal Details");
			System.out.println("3. View Programme Information");
			System.out.println("4. View Registered Modules");
			System.out.println("5. Select Optional Modules");
			System.out.println("6. View Academic Transcript");
			System.out.println("0. Log Out");

			System.out.print("Enter your choice: ");

			while (!scanner.hasNextInt()) {
				System.out.println("Please enter a valid number.");
				scanner.nextLine();
				System.out.print("Enter your choice: ");
			}

			choice = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (choice) {

					case 1:
						viewPersonalDetails();
						break;

					case 2:
						updatePersonalDetails();
						break;

					case 3:
						viewProgrammeInformation();
						break;

					case 4:
						viewRegisteredModules();
						break;

					case 5:
						selectOptionalModules();
						break;

					case 6:
						viewTranscript();
						break;

					case 0:
						System.out.println("Logging out...");
						break;

					default:
						System.out.println("Invalid choice. Please try again.");
				}

			} catch (Exception e) {
				System.out.println("Action unavailable or permission denied: " + e.getMessage());
			}
		} while (choice != 0);
	}

	/**
	 * Displays personal details using permission-protected getters.
	 * 
	 * @throws Exception if a required permission check or called operation fails
	 */
	private void viewPersonalDetails() throws Exception {

		System.out.println("\n===== PERSONAL DETAILS =====");

		System.out.println("Student ID: " + student.getStudentID(permissions));
		System.out.println("First Name: " + student.getFirstName(permissions));
		System.out.println("Last Name: " + student.getLastName(permissions));
		System.out.println("Email: " + student.getEmail(permissions));
		System.out.println("Phone Number: " + student.getPhoneNumber(permissions));
		System.out.println("Term-Time Address: "
				+ student.getTermTimeAddress(permissions));
		System.out.println("Correspondence Address: "
				+ student.getCorrespondenceAddress(permissions));
	}

	/**
	 * Checks write permission and updates personal details in memory. Persistence
	 * is not yet implemented.
	 * 
	 * @throws Exception                     if a required permission check or
	 *                                       called operation fails
	 * @throws UnsupportedOperationException because persistence is not implemented
	 */
	private void updatePersonalDetails() throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);

		int choice;

		do {
			System.out.println("\n===== UPDATE PERSONAL DETAILS =====");
			System.out.println("1. Update First Name");
			System.out.println("2. Update Last Name");
			System.out.println("3. Update Email");
			System.out.println("4. Update Phone Number");
			System.out.println("5. Update Term-Time Address");
			System.out.println("6. Update Correspondence Address");
			System.out.println("0. Return to Student Menu");

			System.out.print("Enter your choice: ");

			while (!scanner.hasNextInt()) {
				System.out.println("Please enter a valid number.");
				scanner.nextLine();
				System.out.print("Enter your choice: ");
			}

			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {

				case 1:
					System.out.print("Enter new first name: ");
					student.setFirstName(scanner.nextLine(), permissions);
					break;

				case 2:
					System.out.print("Enter new last name: ");
					student.setLastName(scanner.nextLine(), permissions);
					break;

				case 3:
					System.out.print("Enter new email: ");
					student.setEmail(scanner.nextLine(), permissions);
					break;

				case 4:
					System.out.print("Enter new phone number: ");
					student.setPhoneNumber(scanner.nextLine(), permissions);
					break;

				case 5:
					System.out.print("Enter new term-time address: ");
					student.setTermTimeAddress(scanner.nextLine(), permissions);
					break;

				case 6:
					System.out.print("Enter new correspondence address: ");
					student.setCorrespondenceAddress(scanner.nextLine(), permissions);
					break;

				case 0:
					System.out.println("Returning to menu...");
					break;

				default:
					System.out.println("Invalid choice.");
			}

		} while (choice != 0);

		/*
		 * Student's details are updated in memory above.
		 * Persistence requires Member 4's StudentRecordsSystem/CSVFileManager.
		 * The integration method must save the updated student record.
		 * Until that connection exists, report it explicitly.
		 */
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Displays programme information when a programme reference is available.
	 * 
	 * @throws UnsupportedOperationException if programme information is unavailable
	 */
	private void viewProgrammeInformation() {

		System.out.println("\n===== PROGRAMME INFORMATION =====");
		// Transcript already holds the Programme reference for this student.
		if (transcript == null || transcript.getProgramme() == null) {
			throw new UnsupportedOperationException("Not yet implemented");
		}
		System.out.println(transcript.getProgramme().toString());
	}

	/**
	 * Displays the registered modules heading. Registration retrieval is not yet
	 * implemented.
	 * 
	 * @throws UnsupportedOperationException because registration retrieval is not
	 *                                       implemented
	 */
	private void viewRegisteredModules() {

		System.out.println("\n===== REGISTERED MODULES =====");
		// Semester module results are not a complete registration record.
		// StudentRecordsSystem must supply the student's registered modules.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Displays the optional module selection heading. Selection is not yet
	 * implemented.
	 * 
	 * @throws UnsupportedOperationException because module selection is not
	 *                                       implemented
	 */
	private void selectOptionalModules() {

		/*
		 * TO DISCUSS WITH MEMBER 4:
		 * StudentRecordsSystem may already provide module selection
		 * and registration functionality.
		 *
		 * This menu option may not be needed if that functionality
		 * is already provided elsewhere. Avoid duplication.
		 *
		 * Member 2’s Programme class should determine whether a student
		 * can select optional modules and which modules are available.
		 *
		 * Member 4’s StudentRecordsSystem should validate and complete
		 * the official module registration.
		 */

		System.out.println("\n===== OPTIONAL MODULE SELECTION =====");
		// Programme must supply eligible optional modules; StudentRecordsSystem
		// must validate and register the student's selection.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Displays the academic transcript when available.
	 * 
	 * @throws Exception                     if a required permission check or
	 *                                       called operation fails
	 * @throws UnsupportedOperationException if the transcript is unavailable
	 */
	private void viewTranscript() throws Exception {

		System.out.println("\n===== ACADEMIC TRANSCRIPT =====");

		if (transcript == null) {
			throw new UnsupportedOperationException("Not yet implemented");
		}

		System.out.println(transcript.generateTranscript(permissions));
	}
}
