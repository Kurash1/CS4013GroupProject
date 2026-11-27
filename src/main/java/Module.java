import java.util.ArrayList;

/**
 * Represents a module, for example CS4013 Object Oriented Development.
 *
 * <p>
 * Java already has a class called java.lang.Module, but this project
 * uses its own Module class.
 * </p>
 *
 * <p>
 * Every new module is added to a static list so it can be searched
 * using its code and saved to the data file.
 * </p>
 *
 * @author Mursal Karimzada
 */
public class Module {

	// Shared by all modules (static).
	private static ArrayList<Module> modules = new ArrayList<>();

	private String code;
	private String title;
	private int credits;
	private boolean passFail;
	private Integer lecturerId; // null means no lecturer yet

	/**
	 * Creates a module with no lecturer.
	 *
	 * @param code     2 to 4 letters then 4 digits, e.g. "CS4013"
	 * @param title    title
	 * @param credits  credits, 1 to 60
	 * @param passFail true if the module is graded pass/fail
	 * @throws IllegalArgumentException if a value is invalid or the code is
	 *                                  already used
	 */
	public Module(String code, String title, int credits, boolean passFail) {
		if (code == null || !isValidCode(code.trim())) {
			throw new IllegalArgumentException("Module code is not valid: " + code);
		}
		if (title == null || title.trim().isEmpty()) {
			throw new IllegalArgumentException("Module title cannot be empty");
		}
		if (credits < 1 || credits > 60) {
			throw new IllegalArgumentException("Credits must be between 1 and 60");
		}
		if (find(code) != null) {
			throw new IllegalArgumentException("Module already exists: " + code);
		}
		this.code = code.trim().toUpperCase();
		this.title = title.trim();
		this.credits = credits;
		this.passFail = passFail;
		modules.add(this);
	}

	// A valid code is 2 to 4 letters followed by 4 digits.
	private static boolean isValidCode(String code) {
		int letters = code.length() - 4;
		if (letters < 2 || letters > 4) {
			return false;
		}
		for (int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			if (i < letters && !Character.isLetter(c)) {
				return false;
			}
			if (i >= letters && !Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/** @return the module code */
	public String getCode() {
		return code;
	}

	/** @return the title */
	public String getTitle() {
		return title;
	}

	/** @return the number of credits */
	public int getCredits() {
		return credits;
	}

	/** @return true if the module is graded pass/fail */
	public boolean isPassFail() {
		return passFail;
	}

	/** @return the lecturer's user ID, or null if none is assigned */
	public Integer getLecturerId() {
		return lecturerId;
	}

	/**
	 * Assigns or removes the lecturer.
	 *
	 * @param lecturerId the lecturer's user ID, or null to remove
	 */
	public void setLecturerId(Integer lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
	 * Says whether a user teaches this module.
	 *
	 * @param userId a user ID
	 * @return true if that user is the lecturer
	 */
	public boolean isTaughtBy(int userId) {
		return lecturerId != null && lecturerId == userId;
	}

	/**
	 * Finds a module by code, ignoring case.
	 *
	 * @param code module code
	 * @return the module, or null if there is none
	 */
	public static Module find(String code) {
		if (code == null) {
			return null;
		}
		for (Module m : modules) {
			if (m.code.equalsIgnoreCase(code.trim())) {
				return m;
			}
		}
		return null;
	}

	/** @return a copy of the list of all modules */
	public static ArrayList<Module> all() {
		return new ArrayList<>(modules);
	}

	/** Forgets all modules. Used before reloading and by the tests. */
	public static void clearAll() {
		modules.clear();
	}

	/**
	 * Writes every module, one record each.
	 *
	 * @param writer the group's CSV writer
	 */
	public static void serialize(CSVWriter writer) {
		for (Module m : modules) {
			writer.startObject("module");
			writer.writeString(m.code);
			writer.writeString(m.title);
			writer.writeInt(m.credits);
			writer.writeString(String.valueOf(m.passFail));
			if (m.lecturerId == null) {
				writer.writeString("");
			} else {
				writer.writeString(m.lecturerId.toString());
			}
			writer.endObject();
		}
	}

	/**
	 * Reads one record. Records of other types are ignored. A module that is
	 * already loaded is replaced.
	 *
	 * @param field one line of the data file
	 */
	public static void deserialize(CSVField field) {
		if (!field.getType().equals("module")) {
			return;
		}
		String code = field.getString(0);
		modules.remove(find(code));
		Module module = new Module(code, field.getString(1), field.getInt(2),
				Boolean.parseBoolean(field.getString(3)));
		String lecturer = field.getString(4);
		if (!lecturer.isEmpty()) {
			module.lecturerId = Integer.parseInt(lecturer);
		}
	}

	/** @return e.g. "CS4013 Object Oriented Development (6 credits)" */
	@Override
	public String toString() {
		return code + " " + title + " (" + credits + " credits)";
	}
}
