package view;

import java.util.ArrayList;

/**
 * Converts percentages to letter grades and quality point values (QPV) and
 * calculates QCA. All methods are static, that way no object is needed.
 *
 * Scale used (general UL scheme of grade descriptions):
 * A1 75+ (4.00), A2 70 (3.60), B1 65 (3.20), B2 60 (3.00), B3 55 (2.80),
 * C1 50 (2.60), C2 45 (2.40), C3 40 (2.00), D1 35 (1.60), D2 30 (1.20), F below
 * 30 (0.00)
 */
public class Grades {

	// Checks that a percentage is between 0 and 100.
	public static boolean isValidPercentage(double percentage) {
		return percentage >= 0 && percentage <= 100;
	}

	// Converts a percentage to a letter grade.
	public static String getLetterGrade(double percentage) {
		if (percentage >= 75) {
			return "A1";
		} else if (percentage >= 70) {
			return "A2";
		} else if (percentage >= 65) {
			return "B1";
		} else if (percentage >= 60) {
			return "B2";
		} else if (percentage >= 55) {
			return "B3";
		} else if (percentage >= 50) {
			return "C1";
		} else if (percentage >= 45) {
			return "C2";
		} else if (percentage >= 40) {
			return "C3";
		} else if (percentage >= 35) {
			return "D1";
		} else if (percentage >= 30) {
			return "D2";
		} else {
			return "F";
		}
	}

	// Converts a percentage (00 to 100) to its point value (0.00 to 4.00).
	public static double getQpv(double percentage) {
		if (percentage >= 75) {
			return 4.00;
		} else if (percentage >= 70) {
			return 3.60;
		} else if (percentage >= 65) {
			return 3.20;
		} else if (percentage >= 60) {
			return 3.00;
		} else if (percentage >= 55) {
			return 2.80;
		} else if (percentage >= 50) {
			return 2.60;
		} else if (percentage >= 45) {
			return 2.40;
		} else if (percentage >= 40) {
			return 2.00;
		} else if (percentage >= 35) {
			return 1.60;
		} else if (percentage >= 30) {
			return 1.20;
		} else {
			return 0.00;
		}
	}

	// Calculates QCA = sum(QPV x credits) / sum(credits). To avoid dividing by 0,
	// there's an if-statement for 0 totalCredits.
	public static double calculateQCA(ArrayList<Double> percentages, ArrayList<Integer> credits) {
		double totalPoints = 0;
		int totalCredits = 0;
		for (int i = 0; i < percentages.size(); i++) {
			totalPoints += getQpv(percentages.get(i)) * credits.get(i);
			totalCredits += credits.get(i);
		}
		if (totalCredits == 0) {
			return 0;
		}
		return totalPoints / totalCredits;
	}

	// Counts how many modules were failed (grade F, below 30%).
	public static int countFails(ArrayList<Double> percentages) {
		int fails = 0;
		for (double p : percentages) {
			if (p < 30) {
				fails++;
			}
		}
		return fails;
	}
}
