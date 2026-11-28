package uni;

import java.util.EnumSet;
import user.*;

/**
 * Stores the personal and contact information for one student. The student ID
 * is exposed for reading but has no public setter.
 *
 * @see StudentMenu
 */

public class Student {
	// Student info
	private String firstName;
	private String lastName;
	private String studentID;
	private String email;
	private String termTimeAddress;
	private String correspondenceAddress;
	private String phoneNumber;

	/**
	 * Creates a Student instance and initialises its supplied references or values.
	 * 
	 * @param firstName             student’s first name
	 * @param lastName              student’s last name
	 * @param studentID             unique student identifier
	 * @param email                 student email address
	 * @param termTimeAddress       term-time postal address
	 * @param correspondenceAddress correspondence postal address
	 * @param phoneNumber           contact telephone number
	 */

	// Constructor
	public Student(String firstName, String lastName, String studentID,
			String email, String termTimeAddress,
			String correspondenceAddress, String phoneNumber) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.studentID = studentID;
		this.email = email;
		this.termTimeAddress = termTimeAddress;
		this.correspondenceAddress = correspondenceAddress;
		this.phoneNumber = phoneNumber;
	}

	// Getters
	/**
	 * Returns first name.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getFirstName(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return firstName;
	}

	/**
	 * Returns last name.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getLastName(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return lastName;
	}

	/**
	 * Returns student id.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getStudentID(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return studentID;
	}

	/**
	 * Returns email.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getEmail(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return email;
	}

	/**
	 * Returns term time address.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getTermTimeAddress(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return termTimeAddress;
	}

	/**
	 * Returns correspondence address.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getCorrespondenceAddress(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return correspondenceAddress;
	}

	/**
	 * Returns phone number.
	 * 
	 * @return the requested text value
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String getPhoneNumber(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return phoneNumber;
	}

	// Setters
	/**
	 * Updates first name.
	 * 
	 * @param firstName   student’s first name
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setFirstName(String firstName, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.firstName = firstName;
	}

	/**
	 * Updates last name.
	 * 
	 * @param lastName    student’s last name
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setLastName(String lastName, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.lastName = lastName;
	}

	/**
	 * Updates email.
	 * 
	 * @param email       student email address
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setEmail(String email, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.email = email;
	}

	/**
	 * Updates term time address.
	 * 
	 * @param termTimeAddress term-time postal address
	 * @param permissions     caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setTermTimeAddress(String termTimeAddress, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.termTimeAddress = termTimeAddress;
	}

	/**
	 * Updates correspondence address.
	 * 
	 * @param correspondenceAddress correspondence postal address
	 * @param permissions           caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setCorrespondenceAddress(String correspondenceAddress, EnumSet<Permission> permissions)
			throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.correspondenceAddress = correspondenceAddress;
	}

	/**
	 * Updates phone number.
	 * 
	 * @param phoneNumber contact telephone number
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public void setPhoneNumber(String phoneNumber, EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.writeStudent);
		this.phoneNumber = phoneNumber;
	}

	// Display student info
	/**
	 * Returns student details to an authorized caller.
	 * 
	 * @return a readable summary of this object
	 * @param permissions caller permissions
	 * @throws Exception if permission is denied
	 */

	public String displayStudentDetails(EnumSet<Permission> permissions) throws Exception {
		Permission.CheckOrFail(permissions, Permission.readStudent);
		return "Student Details\n" +
				"Student ID: " + studentID + "\n" +
				"First Name: " + firstName + "\n" +
				"Last Name: " + lastName + "\n" +
				"Email: " + email + "\n" +
				"Term-Time Address: " + termTimeAddress + "\n" +
				"Correspondence Address: " + correspondenceAddress + "\n" +
				"Phone Number: " + phoneNumber;
	}
}

// Intergration with StudentMenu, Semester, Transcript, StudentRecordsSystem
// (Member 4).
