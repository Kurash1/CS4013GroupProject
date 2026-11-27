import java.util.ArrayList;

/**
 * A university department. It stores its name plus the codes of its programmes
 * and modules, and can hold a simple exam board decision.
 * Programmes and modules are stored as codes (Strings) so this class does not
 * depend on Programme or Module.
 */
public class Department {
	private String name;
	private ArrayList<String> programmeCodes;
	private ArrayList<String> moduleCodes;

	public Department(String name) {
		this.name = name;
		this.programmeCodes = new ArrayList<>();
		this.moduleCodes = new ArrayList<>();
	}

	// Gets the department name.
	public String getName() {
		return name;
	}

	// Adds a programme code if not already present.
	public void addProgramme(String programmeCode) {
		if (!programmeCodes.contains(programmeCode)) {
			programmeCodes.add(programmeCode);
		}
	}

	// Adds a module code if not already present (same as programme above).
	public void addModule(String moduleCode) {
		if (!moduleCodes.contains(moduleCode)) {
			moduleCodes.add(moduleCode);
		}
	}

	// Following two ArrayList get their respective programme/module:
	public ArrayList<String> getProgrammeCodes() {
		return programmeCodes;
	}

	public ArrayList<String> getModuleCodes() {
		return moduleCodes;
	}

	/**
	 * Holds an exam board decision for one student using the progression rule.
	 * Uses the student's ID, the student's QCA and the number of failed modules.
	 * Returns a text with the board decision.
	 */
	public String holdExamBoard(String studentID, double qca, int failedModules) {
		Progression progression = new Progression(qca, failedModules);
		return "Exam Board - " + name + "\n" + "Student: " + studentID + "\n" + "QCA: "
				+ String.format("%.2f", qca) + "\n" + "Decision: " + progression.getStatus() + "\n"
				+ "Reason: " + progression.getReason();
	}

	@Override
	public String toString() {
		return "Department: " + name + "\n" + "Programmes: " + programmeCodes + "\n" + "Modules: "
				+ moduleCodes;
	}
}
