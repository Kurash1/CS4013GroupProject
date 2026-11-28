package uni;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collections;
import view.*;

/**
 * Stores semester information and module percentage results for one student.
 * 
 * @see Student
 */

public class Semester {

	// Semester information
	private String semesterName;
	private String academicYear;

	// Student associated with this semester
	private Student student;

	// Stores each module and its corresponding grade
	// References Module (Member 2) and Grades (Member 3)
	private Map<Module, Double> moduleResults;

	// Constructor
	/**
	 * Creates a semester with an initially empty result map.
	 * 
	 * @param semesterName semester name
	 * @param academicYear academic year
	 * @param student      associated student
	 */
	public Semester(String semesterName, String academicYear, Student student) {

		this.semesterName = semesterName;
		this.academicYear = academicYear;
		this.student = student;

		this.moduleResults = new LinkedHashMap<>();
	}

	// Getters

	/**
	 * Returns the semester name.
	 * 
	 * @return semester name
	 */
	public String getSemesterName() {
		return semesterName;
	}

	/**
	 * Returns the academic year.
	 * 
	 * @return academic year
	 */
	public String getAcademicYear() {
		return academicYear;
	}

	/**
	 * Returns the associated student.
	 * 
	 * @return associated student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Returns an unmodifiable view of module percentage results.
	 * 
	 * @return unmodifiable module result map
	 */
	public Map<Module, Double> getModuleResults() {
		return Collections.unmodifiableMap(moduleResults);
	}

	// Setters

	/**
	 * Updates the semester name.
	 * 
	 * @param semesterName semester name
	 */
	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}

	/**
	 * Updates the academic year.
	 * 
	 * @param academicYear academic year
	 */
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	// Add a module and its corresponding grade
	/**
	 * Adds or replaces the percentage result for a module.
	 * 
	 * @param module module associated with the result
	 * @param grade  numerical percentage result
	 * @throws IllegalArgumentException if the module or grade is null or the
	 *                                  percentage is invalid
	 */
	public void addModuleResult(Module module, Double grade) {

		if (module == null || grade == null || !Grades.isValidPercentage(grade)) {
			throw new IllegalArgumentException(
					"Module and grade cannot be null.");
		}

		moduleResults.put(module, grade);
	}

	// Calculate semester QCA
	/**
	 * Calculates the credit-weighted semester QCA, or zero if no credits are
	 * recorded.
	 * 
	 * @return credit-weighted semester QCA
	 * @throws IllegalStateException if credits are not positive or grade points are
	 *                               negative
	 */
	public double calculateSemesterQCA() {

		double totalGradePoints = 0.0;
		int totalCredits = 0;

		for (Map.Entry<Module, Double> entry : moduleResults.entrySet()) {

			Module module = entry.getKey();
			Double grade = entry.getValue();

			int credits = module.getCredits();
			double gradePoints = Grades.getQpv(grade);

			if (credits <= 0) {
				throw new IllegalStateException(
						"Module credits must be greater than zero.");
			}

			if (gradePoints < 0) {
				throw new IllegalStateException(
						"Grade points cannot be negative.");
			}

			totalGradePoints += credits * gradePoints;
			totalCredits += credits;
		}

		// Avoid division by zero if no results have been recorded
		if (totalCredits == 0) {
			return 0.0;
		}

		return totalGradePoints / totalCredits;
	}

	// Display semester information
	/**
	 * Returns semester details using permission-protected student names.
	 * 
	 * @param permissions caller permissions
	 * @return readable semester details
	 * @throws Exception if a required permission check or called operation fails
	 */
	public String displaySemesterDetails() throws Exception {

		return "Semester: " + semesterName +
				"\nAcademic Year: " + academicYear +
				"\nStudent: " + student.getFirstName() +
				" " + student.getLastName() +
				"\nNumber of Modules: " + moduleResults.size() +
				"\nSemester QCA: " + calculateSemesterQCA();
	}

	/*
	 * NOT SURE IF NEEDED:
	 * Repeat and link-in module requirements should primarily
	 * be managed by the Progression class (Member 3).
	 *
	 * Semester may need to reference this information when
	 * displaying or calculating a student’s academic results.
	 */
}
