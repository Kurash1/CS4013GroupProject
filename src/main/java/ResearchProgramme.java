package src.main.java;

/**
 * A programme by research and thesis: a research master's or a PhD. These
 * awards are not classified, and are decided by examining the thesis rather
 * than by QCA.
 *
 * @author Mursal Karimzada
 */
public class ResearchProgramme extends Programme {

    /** True for a structured PhD, which also has taught modules. */
    private boolean structured;

    /**
     * Creates a research programme.
     *
     * @param code          programme code
     * @param title         title
     * @param type          RESEARCH_MASTERS or PHD
     * @param department    department
     * @param durationYears length in years
     * @param structured    true for a structured PhD
     * @throws IllegalArgumentException if a value is invalid, or a research
     *                                  master's is marked as structured
     */
    public ResearchProgramme(String code, String title, String type, Department department,
                             int durationYears, boolean structured) {
        super(code, title, checkType(type, structured), department, durationYears);
        this.structured = structured;
    }

    // static, because it is called inside super(...) before the object exists.
    // This stops a programme with a bad type being added to the list.
    private static String checkType(String type, boolean structured) {
        if (!"RESEARCH_MASTERS".equals(type) && !"PHD".equals(type)) {
            throw new IllegalArgumentException("Not a research programme type: " + type);
        }
        if (structured && !"PHD".equals(type)) {
            throw new IllegalArgumentException("Only a PhD can be structured");
        }
        return type;
    }

    @Override
    public String getAwardType() {
        if (getType().equals("RESEARCH_MASTERS")) {
            return "Master's Degree (by Research)";
        }
        if (structured) {
            return "Doctor of Philosophy (Structured)";
        }
        return "Doctor of Philosophy";
    }

    /** Research awards are always unclassified, so the QCA is ignored. */
    @Override
    public String classifyAward(double qca) {
        return UNCLASSIFIED;
    }

    @Override
    public boolean isResearch() {
        return true;
    }

    @Override
    public boolean isStructured() {
        return structured;
    }
}
