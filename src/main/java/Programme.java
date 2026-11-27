import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a programme of study, for example LM051 Computer Systems
 *
 * <p>
 * This class is abstract, which means we cannot create an object
 * directly from it. Each type of programme (Undergraduate,
 * Postgraduate or Research) has its own way of deciding the final award.
 * </p>
 * 
 * <p>
 * Award rules are from the UL Handbook of Academic Regulations 2026/27.
 * </p>
 *
 * @author Mursal Karimzada
 */
public abstract class Programme {

	/** Award result: QCA 3.30 or more (Handbook 2.6.2). */
	public static final String FIRST_CLASS = "First class honours";
	/** Award result: QCA 2.90 or more. */
	public static final String SECOND_CLASS_1 = "Second class honours grade 1 (2.1)";
	/** Award result: QCA 2.50 or more. */
	public static final String SECOND_CLASS_2 = "Second class honours grade 2 (2.2)";
	/** Award result: QCA 2.00 or more. */
	public static final String THIRD_CLASS = "Third class honours";
	/** Award result for research degrees and professional doctorates. */
	public static final String UNCLASSIFIED = "Awarded without classification";
	/** Award result when the QCA is too low. */
	public static final String NOT_ELIGIBLE = "Not eligible for award";

	// Shared by all programmes (static).
	private static ArrayList<Programme> programmes = new ArrayList<>();

	private String code;
	private String title;
	private String type;
	private Department department;
	private int durationYears;
	private ArrayList<ProgrammeModule> modules = new ArrayList<>();

	/**
	 * Creates a programme without any modules. This is only used by subclasses.
	 *
	 * @param code          programme code
	 * @param title         title
	 * @param type          kind of programme, e.g. "UNDERGRADUATE"
	 * @param department    the department responsible for the programme
	 * @param durationYears How many years the programme lasts, from 1 to 8
	 * @throws IllegalArgumentException if a value is invalid or the code is
	 *                                  already used
	 */
	protected Programme(String code, String title, String type, Department department,
			int durationYears) {
		if (code == null || code.trim().isEmpty() || title == null || title.trim().isEmpty()) {
			throw new IllegalArgumentException("Programme code and title are required");
		}
		if (department == null) {
			throw new IllegalArgumentException("Department is required");
		}
		if (durationYears < 1 || durationYears > 8) {
			throw new IllegalArgumentException("Duration must be between 1 and 8 years");
		}
		if (find(code) != null) {
			throw new IllegalArgumentException("Programme already exists: " + code);
		}
		this.code = code.trim().toUpperCase();
		this.title = title.trim();
		this.type = type;
		this.department = department;
		this.durationYears = durationYears;
		programmes.add(this);
	}

	/** @return the name of the award, e.g. "Bachelor's Degree" */
	public abstract String getAwardType();

	/**
	 * Decide which award is given based on final cumulative QCA.
	 *
	 * @param qca the final cumulative QCA
	 * @return one of the award constants, e.g. FIRST_CLASS
	 */
	public abstract String classifyAward(double qca);

	/**
	 * The honours grades user bachelor's and taught master's degrees.
	 *
	 * @param qca the final cumulative QCA, 0.00 to 4.00
	 * @return the award result
	 */
	protected static String classifyHonours(double qca) {
		double rounded = roundQca(qca);
		if (rounded >= 3.30) {
			return FIRST_CLASS;
		} else if (rounded >= 2.90) {
			return SECOND_CLASS_1;
		} else if (rounded >= 2.50) {
			return SECOND_CLASS_2;
		} else if (rounded >= 2.00) {
			return THIRD_CLASS;
		}
		return NOT_ELIGIBLE;
	}

	/**
	 * Checks if the QCA is valid and rounds it to 2 decimal places.
	 *
	 * @param qca the QCA value
	 * @return the rounded QCA value
	 * @throws IllegalArgumentException if the value is not between 0.00 and 4.00
	 */
	protected static double roundQca(double qca) {
		if (Double.isNaN(qca) || qca < 0 || qca > 4) {
			throw new IllegalArgumentException("QCA must be between 0.00 and 4.00: " + qca);
		}
		return Math.round(qca * 100) / 100.0;
	}

	/** @return false; ResearchProgramme returns true */
	public boolean isResearch() {
		return false;
	}

	/** @return false; a structured PhD returns true */
	public boolean isStructured() {
		return false;
	}

	/**
	 * Adds a module to the programme.
	 *
	 * @param module      the module
	 * @param yearOfStudy the year the module is taken
	 * @param term        the semester the module is taken
	 * @throws IllegalArgumentException if a value is missing, the year is invalid
	 *                                  or the module is already added
	 */
	public void addModule(Module module, int yearOfStudy, Term term) {
		// The ProgrammeModule constructor checks for missing values.
		ProgrammeModule entry = new ProgrammeModule(module, yearOfStudy, term);
		if (yearOfStudy > durationYears) {
			throw new IllegalArgumentException("Year of study must be between 1 and "
					+ durationYears);
		}
		if (findModule(module.getCode()) != null) {
			throw new IllegalArgumentException(module.getCode() + " is already in " + code);
		}
		modules.add(entry);
	}

	/**
	 * Search for a module in this programme, ignoring case.
	 *
	 * @param moduleCode module code
	 * @return the entry, or null if the module is not on this programme
	 */
	public ProgrammeModule findModule(String moduleCode) {
		for (ProgrammeModule pm : modules) {
			if (pm.getModule().getCode().equalsIgnoreCase(moduleCode)) {
				return pm;
			}
		}
		return null;
	}

	/** @return a copy of the list of module entries */
	public ArrayList<ProgrammeModule> getModules() {
		return new ArrayList<>(modules);
	}

	/** @return the programme code */
	public String getCode() {
		return code;
	}

	/** @return the title */
	public String getTitle() {
		return title;
	}

	/** @return the type of programme, e.g. "TAUGHT_MASTERS" */
	public String getType() {
		return type;
	}

	/** @return the department */
	public Department getDepartment() {
		return department;
	}

	/** @return how many years the programe lasts */
	public int getDurationYears() {
		return durationYears;
	}

	/**
	 * Search for a programme by its code, ignoring case.
	 *
	 * @param code programme code
	 * @return the programme, or null if it does not exist
	 */
	public static Programme find(String code) {
		if (code == null) {
			return null;
		}
		for (Programme p : programmes) {
			if (p.code.equalsIgnoreCase(code.trim())) {
				return p;
			}
		}
		return null;
	}

	/** @return a copy of all the programmes in the lists */
	public static ArrayList<Programme> all() {
		return new ArrayList<>(programmes);
	}

	/** Removes all programmes. Used before reloading and by the tests. */
	public static void clearAll() {
		programmes.clear();
	}

	/**
	 * Saved all programmes along with their module entries.
	 *
	 * @param writer the group's CSV writer
	 */
	public static void serialize(CSVWriter writer) {
		for (Programme p : programmes) {
			writer.startObject("programme");
			writer.writeString(p.type);
			writer.writeString(p.code);
			writer.writeString(p.title);
			writer.writeString(p.department.getName());
			writer.writeInt(p.durationYears);
			writer.writeString(String.valueOf(p.isStructured()));
			writer.endObject();
			for (ProgrammeModule pm : p.modules) {
				writer.startObject("programmeModule");
				writer.writeString(p.code);
				writer.writeString(pm.getModule().getCode());
				writer.writeInt(pm.getYearOfStudy());
				writer.writeString(pm.getTerm().name());
				writer.endObject();
			}
		}
	}

	/**
	 * Reads one line from the file and skiped records that are not programmes.
	 * Modules must be
	 * loaded first before loading the programmes that use them.
	 *
	 * @param field       one line of the data file
	 * @param departments departments stord by name; if one doesn not exist,
	 *                    it will be created and added to the map.
	 * 
	 * @throws IllegalArgumentException if a module entry referes to a programme
	 *                                  or module that does not exist
	 *                                  programme or module
	 */
	public static void deserialize(CSVField field, HashMap<String, Department> departments) {
		if (field.getType().equals("programme")) {
			loadProgramme(field, departments);
		} else if (field.getType().equals("programmeModule")) {
			loadEntry(field);
		}
	}

	private static void loadProgramme(CSVField field,
			HashMap<String, Department> departments) {
		String type = field.getString(0);
		String code = field.getString(1);
		String departmentName = field.getString(3);

		Department department = departments.get(departmentName);
		if (department == null) {
			department = new Department(departmentName);
			departments.put(departmentName, department);
		}

		programmes.remove(find(code)); // replace it if already loaded
		create(type, code, field.getString(2), department, field.getInt(4),
				Boolean.parseBoolean(field.getString(5)));
		department.addProgramme(code);
	}

	private static void loadEntry(CSVField field) {
		Programme programme = find(field.getString(0));
		Module module = Module.find(field.getString(1));
		if (programme == null || module == null) {
			throw new IllegalArgumentException("Unknown programme or module: "
					+ field.getString(0) + " " + field.getString(1));
		}
		programme.addModule(module, field.getInt(2), Term.valueOf(field.getString(3)));
	}

	/**
	 * Creates the right subclass for a type name.
	 *
	 * @param type          UNDERGRADUATE, TAUGHT_MASTERS,
	 *                      PROFESSIONAL_DOCTORATE, RESEARCH_MASTERS or PHD
	 * @param code          programme code
	 * @param title         title
	 * @param department    department
	 * @param durationYears length in years
	 * @param structured    true for a structured PhD
	 * @return the new programme
	 * @throws IllegalArgumentException if the type or a value is invalid
	 */
	public static Programme create(String type, String code, String title,
			Department department, int durationYears,
			boolean structured) {
		if ("UNDERGRADUATE".equals(type)) {
			return new UndergraduateProgramme(code, title, department, durationYears);
		}
		if ("RESEARCH_MASTERS".equals(type) || "PHD".equals(type)) {
			return new ResearchProgramme(code, title, type, department, durationYears,
					structured);
		}
		return new PostgraduateProgramme(code, title, type, department, durationYears);
	}

	/**
	 * @return details, e.g. "LM051 Computer Systems (Bachelor's Degree, 4 years)"
	 */
	@Override
	public String toString() {
		String years = " years)";
		if (durationYears == 1) {
			years = " year)";
		}
		return code + " " + title + " (" + getAwardType() + ", " + durationYears + years;
	}
}
