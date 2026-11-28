package ui;

import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import uni.*;
import view.*;

/**
 * Command-line menu for faculty staff. Lets them enter module results and look
 * up semester QCA, progression, award classification and exam board decisions.
 * Results are kept in memory for now; but can be replaced by member 1 or 4 :).
 */
public class FacultyMenu {

	// One module result (private helper, so it is not an extra project class).
	private static class Result {
		String studentID;
		String moduleCode;
		int semester;
		double percentage;
		int credits;
	}

	private Department department;
	private ArrayList<Result> results;

	/*
	 * Creates the menu.
	 * Uses as parameter the department that holds the exam boards.
	 */
	public FacultyMenu(Department department) {
		this.department = department;
		this.results = new ArrayList<>();
	}

	// Runs the menu until the user chooses to exit.
	public void run() {
		boolean running = true;
		while (running) {
			System.out.println("\n=== Faculty Menu ===");
			System.out.println("1. Record module result");
			System.out.println("2. Show semester QCA");
			System.out.println("3. Show progression status");
			System.out.println("4. Show award classification");
			System.out.println("5. Hold exam board");
			System.out.println("0. Exit");
			System.out.print("Choice: ");
			String choice = App.input.nextLine().trim();

			if (choice.equals("1")) {
				recordResult();
			} else if (choice.equals("2")) {
				showSemesterQca();
			} else if (choice.equals("3")) {
				showProgression();
			} else if (choice.equals("4")) {
				showAward();
			} else if (choice.equals("5")) {
				holdExamBoard();
			} else if (choice.equals("0")) {
				running = false;
			} else {
				System.out.println("Invalid choice.");
			}
		}
	}

	// Reads a number from the user, asking again if the input is not a number
	private double readNumber(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Double.parseDouble(App.input.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
			}
		}
	}

	private String readText(String prompt) {
		System.out.print(prompt);
		return App.input.nextLine().trim();
	}

	private void recordResult() {
		Result r = new Result();
		r.studentID = readText("Student ID: ");
		r.moduleCode = readText("Module code: ");
		r.semester = (int) readNumber("Semester (1 or 2): ");
		r.credits = (int) readNumber("Module credits: ");
		r.percentage = readNumber("Percentage (0-100): ");

		if (!Grades.isValidPercentage(r.percentage) || r.credits <= 0) {
			System.out.println("Invalid percentage or credits. Result not saved.");
			return;
		}
		results.add(r);
		System.out.println("Saved: " + r.moduleCode + " -> " + Grades.getLetterGrade(r.percentage) + " (QPV "
				+ Grades.getQpv(r.percentage) + ")");
	}

	// Collects marks and credits of a student. semester 0 = all semesters.
	private void collect(String studentID, int semester, ArrayList<Double> marks, ArrayList<Integer> credits) {
		for (Result r : results) {
			if (r.studentID.equals(studentID) && (semester == 0 || r.semester == semester)) {
				marks.add(r.percentage);
				credits.add(r.credits);
			}
		}
	}

	private void showSemesterQca() {
		String id = readText("Student ID: ");
		int semester = (int) readNumber("Semester: ");
		ArrayList<Double> marks = new ArrayList<>();
		ArrayList<Integer> credits = new ArrayList<>();
		collect(id, semester, marks, credits);
		if (marks.isEmpty()) {
			System.out.println("No results found.");
			return;
		}
		System.out.println("Semester QCA: " + String.format("%.2f", Grades.calculateQCA(marks, credits)));
	}

	private void showProgression() {
		String id = readText("Student ID: ");
		ArrayList<Double> marks = new ArrayList<>();
		ArrayList<Integer> credits = new ArrayList<>();
		collect(id, 0, marks, credits);
		if (marks.isEmpty()) {
			System.out.println("No results found.");
			return;
		}
		double qca = Grades.calculateQCA(marks, credits);
		Progression p = new Progression(qca, Grades.countFails(marks));
		System.out.println("QCA: " + String.format("%.2f", qca));
		System.out.println("Status: " + p.getStatus());
		System.out.println(p.getReason());
	}

	private void showAward() {
		String id = readText("Student ID: ");
		ArrayList<Double> marks = new ArrayList<>();
		ArrayList<Integer> credits = new ArrayList<>();
		collect(id, 0, marks, credits);
		if (marks.isEmpty()) {
			System.out.println("No results found.");
			return;
		}
		AwardClassification award = new AwardClassification(Grades.calculateQCA(marks, credits));
		System.out.println(award);
	}

	private void holdExamBoard() {
		String id = readText("Student ID: ");
		ArrayList<Double> marks = new ArrayList<>();
		ArrayList<Integer> credits = new ArrayList<>();
		collect(id, 0, marks, credits);
		if (marks.isEmpty()) {
			System.out.println("No results found.");
			return;
		}
		double qca = Grades.calculateQCA(marks, credits);
		System.out.println(department.holdExamBoard(id, qca, Grades.countFails(marks)));
	}

	/**
	 * Temporary entry point to test this menu alone. Remove it when the group's
	 * main class (StudentRecordsSimulation) is ready.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Department dept = new Department("Computer Science and Information Systems");
		new FacultyMenu(dept).run();
	}
}
