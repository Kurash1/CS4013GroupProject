package uni.programmes;

import uni.*;

/**
 * A bachelor's degree programme.
 *
 * @author Mursal Karimzada
 */
public class UndergraduateProgramme extends Programme {

	/**
	 * Creates an undergraduate programme.
	 *
	 * @param code          programme code
	 * @param title         title
	 * @param department    department
	 * @param durationYears length in years
	 * @throws IllegalArgumentException if a value is invalid
	 */
	public UndergraduateProgramme(String code, String title, Department department,
			int durationYears) {
		super(code, title, "UNDERGRADUATE", department, durationYears);
	}

	@Override
	public String getAwardType() {
		return "Bachelor's Degree";
	}

	/** Uses the honours bands 3.30 / 2.90 / 2.50 / 2.00 (Handbook 2.6.2). */
	@Override
	public String classifyAward(double qca) {
		return classifyHonours(qca);
	}
}
