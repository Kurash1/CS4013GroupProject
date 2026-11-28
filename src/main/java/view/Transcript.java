package view;

import java.util.EnumSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import uni.*;
import uni.Module;
import user.*;

/**
 * Stores student, programme, semester, progression and award references for an
 * academic transcript.
 * 
 * @see Semester
 */

public class Transcript {

	// References to other members’ classes

	// Member 1: Student class
	// Supplies the student’s personal information.
	private Student student;

	// Member 2: Programme class
	// Supplies programme name, code, department,
	// programme level, type and duration.
	private Programme programme;

	// Member 1: Semester class
	// Supplies academic year, semester results
	// and semester QCA.
	private List<Semester> semesters;

	// Member 3: Progression class
	// Supplies the student’s progression status.
	private Progression progression;

	// Member 3: AwardClassification class
	// Supplies the final award and classification.
	private AwardClassification awardClassification;

	// Constructor

	/**
	 * Creates a transcript with an initially empty semester list.
	 * 
	 * @param student             associated student
	 * @param programme           associated programme
	 * @param progression         progression reference
	 * @param awardClassification award classification reference
	 * @throws IllegalArgumentException if student or programme is null
	 */
	public Transcript(Student student, Programme programme,
			Progression progression,
			AwardClassification awardClassification) {

		if (student == null || programme == null) {
			throw new IllegalArgumentException(
					"Student and programme cannot be null.");
		}

		this.student = student;
		this.programme = programme;
		this.progression = progression;
		this.awardClassification = awardClassification;

		this.semesters = new ArrayList<>();
	}

	// Getters

	/**
	 * Returns the associated student.
	 * 
	 * @return associated student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Returns the associated programme.
	 * 
	 * @return programme reference
	 */
	public Programme getProgramme() {
		return programme;
	}

	/**
	 * Returns an unmodifiable view of the semester list.
	 * 
	 * @return unmodifiable semester list
	 */
	public List<Semester> getSemesters() {
		return Collections.unmodifiableList(semesters);
	}

	/**
	 * Returns the progression reference, which may be null.
	 * 
	 * @return progression reference
	 */
	public Progression getProgression() {
		return progression;
	}

	/**
	 * Returns the award classification reference, which may be null.
	 * 
	 * @return award classification reference
	 */
	public AwardClassification getAwardClassification() {
		return awardClassification;
	}

	// Setters

	/**
	 * Updates the progression reference.
	 * 
	 * @param progression progression reference
	 */
	public void setProgression(Progression progression) {
		this.progression = progression;
	}

	/**
	 * Updates the award classification reference.
	 * 
	 * @param awardClassification award classification reference
	 */
	public void setAwardClassification(
			AwardClassification awardClassification) {

		this.awardClassification = awardClassification;
	}

	// Add semester

	// Member 1: Semester class
	// Adds a semester containing the student’s results.

	/**
	 * Adds a semester to the transcript.
	 * 
	 * @param semester semester to add
	 * @throws IllegalArgumentException if semester is null
	 */
	public void addSemester(Semester semester) {

		if (semester == null) {
			throw new IllegalArgumentException(
					"Semester cannot be null.");
		}

		semesters.add(semester);
	}

	// Generate transcript

	/**
	 * Generates a readable transcript after checking student, programme and module
	 * read permissions. Earned credits remain pending agreed rules.
	 * 
	 * @param permissions caller permissions
	 * @return formatted academic transcript
	 * @throws Exception if a required permission check or called operation fails
	 */
	public String generateTranscript(EnumSet<Permission> permissions) throws Exception {

		Permission.CheckOrFail(permissions, Permission.readStudent);
		Permission.CheckOrFail(permissions, Permission.readProgramme);
		Permission.CheckOrFail(permissions, Permission.readModule);
		StringBuilder transcript = new StringBuilder();

		transcript.append("====================================\n");
		transcript.append("       UNIVERSITY OF LIMERICK\n");
		transcript.append("          ACADEMIC TRANSCRIPT\n");
		transcript.append("====================================\n\n");

		// ------------------------------------------
		// 1. STUDENT PERSONAL INFORMATION
		// Member 1: Student
		// ------------------------------------------

		transcript.append("STUDENT INFORMATION\n");
		transcript.append("-------------------\n");

		transcript.append("Student ID: ")
				.append(student.getStudentID(permissions)).append("\n");

		transcript.append("First Name: ")
				.append(student.getFirstName(permissions)).append("\n");

		transcript.append("Last Name: ")
				.append(student.getLastName(permissions)).append("\n");

		transcript.append("Email: ")
				.append(student.getEmail(permissions)).append("\n");

		transcript.append("Phone Number: ")
				.append(student.getPhoneNumber(permissions)).append("\n");

		transcript.append("Term-Time Address: ")
				.append(student.getTermTimeAddress(permissions)).append("\n");

		transcript.append("Correspondence Address: ")
				.append(student.getCorrespondenceAddress(permissions))
				.append("\n\n");

		// ------------------------------------------
		// 2. PROGRAMME INFORMATION
		// Member 2: Programme
		// ------------------------------------------

		transcript.append("PROGRAMME INFORMATION\n");
		transcript.append("----------------------\n");

		// Programme information is retrieved from
		// Member 2’s Programme class.
		// Programme should override toString() to
		// display its programme details.

		transcript.append(programme.toString()).append("\n\n");

		// ------------------------------------------
		// 3. ACADEMIC RESULTS
		// Member 1: Semester
		// Member 2: Module
		// Member 3: Grades
		// ------------------------------------------

		transcript.append("ACADEMIC RESULTS\n");
		transcript.append("----------------\n");

		for (Semester semester : semesters) {

			transcript.append("\nAcademic Year: ")
					.append(semester.getAcademicYear())
					.append("\n");

			transcript.append("Semester: ")
					.append(semester.getSemesterName())
					.append("\n");

			// Retrieve semester module results.
			// Semester stores Module references and numerical percentage results.

			for (Map.Entry<Module, Double> entry : semester.getModuleResults().entrySet()) {

				transcript.append("\nModule: ")
						.append(entry.getKey().toString());

				transcript.append("\nGrade: ")
						.append(Grades.getLetterGrade(entry.getValue()));

				transcript.append("\n");
			}

			// Semester QCA is calculated by Semester.
			// Transcript only retrieves and displays it.

			transcript.append("Semester QCA: ")
					.append(String.format("%.2f",
							semester.calculateSemesterQCA()))
					.append("\n");
		}

		// ------------------------------------------
		// 4. ACADEMIC PERFORMANCE
		// Member 1: Semester
		// Member 3: Progression
		// ------------------------------------------

		transcript.append("\nACADEMIC PERFORMANCE\n");
		transcript.append("---------------------\n");

		// Overall QCA and attempted credits can be calculated from the
		// semester results already held by this Transcript. Earned credits
		// require the agreed passing/compensation rules from Member 3.
		double weightedPoints = 0.0;
		int attemptedCredits = 0;
		for (Semester semester : semesters) {
			for (Map.Entry<Module, Double> entry : semester.getModuleResults().entrySet()) {
				int credits = entry.getKey().getCredits();
				double points = Grades.getQpv(entry.getValue());
				weightedPoints += credits * points;
				attemptedCredits += credits;
			}
		}
		double overallQCA = attemptedCredits == 0 ? 0.0 : weightedPoints / attemptedCredits;
		transcript.append("Cumulative QCA: ")
				.append(String.format("%.2f", overallQCA)).append("\n");
		transcript.append("Total Credits Attempted: ")
				.append(attemptedCredits).append("\n");
		// Do not infer earned credits from grade points alone: the group's
		// agreed pass/compensation rules determine which credits are earned.
		transcript.append("Total Credits Earned: Pending agreed rules\n");

		// Member 3: Progression
		// Supplies the official progression decision.

		transcript.append("Progression Status: ");

		if (progression != null) {
			transcript.append(progression.toString());
		} else {
			transcript.append("Not yet available");
		}

		transcript.append("\n\n");

		// ------------------------------------------
		// 5. AWARD INFORMATION
		// Member 3: AwardClassification
		// ------------------------------------------

		transcript.append("AWARD INFORMATION\n");
		transcript.append("-----------------\n");

		if (awardClassification != null) {

			// AwardClassification should provide
			// qualification, final classification
			// and completion date through its methods
			// or its toString() implementation.

			transcript.append(
					awardClassification.toString()).append("\n");

		} else {

			transcript.append(
					"Qualification Awarded: Not yet awarded\n");

			transcript.append(
					"Final Award Classification: Pending\n");

			transcript.append(
					"Completion Date: Pending\n");
		}

		transcript.append("\n====================================\n");
		transcript.append("          END OF TRANSCRIPT\n");
		transcript.append("====================================\n");

		return transcript.toString();
	}
}
