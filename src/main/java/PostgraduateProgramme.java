package src.main.java;

/**
 * Represents a postgraduate programme, such as a taught master's degree
 * or a professional doctorate.
 *
 * @author Mursal Karimzada
 */
public class PostgraduateProgramme extends Programme {

    /**
     * Creates a taught postgraduate programme.
     *
     * @param code          programme code
     * @param title         title
     * @param type          TAUGHT_MASTERS or PROFESSIONAL_DOCTORATE
     * @param department    department
     * @param durationYears length in years
     * @throws IllegalArgumentException if a value is invalid
     */
    public PostgraduateProgramme(String code, String title, String type,
                                 Department department, int durationYears) {
        super(code, title, checkType(type), department, durationYears);
    }

    // static, because it is called inside super(...) before the object exists.
    // This stops a programme with a bad type being added to the list.
    private static String checkType(String type) {
        if (!"TAUGHT_MASTERS".equals(type) && !"PROFESSIONAL_DOCTORATE".equals(type)) {
            throw new IllegalArgumentException("Not a taught postgraduate type: " + type);
        }
        return type;
    }

    @Override
    public String getAwardType() {
        if (getType().equals("TAUGHT_MASTERS")) {
            return "Taught Master's Degree";
        }
        return "Professional Doctorate";
    }

    /**
     * A taught master's follows the honours grade rules (Handbook 2.7).
     * A professional doctorate has no classification and requires a QCA
     * of at least 3.00 (2.9.3).
     */
    @Override
    public String classifyAward(double qca) {
        if (getType().equals("PROFESSIONAL_DOCTORATE")) {
            if (roundQca(qca) >= 3.00) {
                return UNCLASSIFIED;
            }
            return NOT_ELIGIBLE;
        }
        return classifyHonours(qca);
    }
}
