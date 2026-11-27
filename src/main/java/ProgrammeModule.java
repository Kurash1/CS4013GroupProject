package src.main.java;

/**
 * Represents a module added to a programme, including the year and semester
 * when it is taken. The same module can be used in different years on
 * different programmes.
 *
 * @author Mursal Karimzada
 */
public class ProgrammeModule {

    private final Module module;
    private final int yearOfStudy;
    private final Term term;

    /**
     * Creates an entry.
     *
     * @param module      the module
     * @param yearOfStudy year of study, starting at 1
     * @param term        semester
     * @throws IllegalArgumentException if a value is missing or the year is
     *                                  less than 1
     */
    public ProgrammeModule(Module module, int yearOfStudy, Term term) {
        if (module == null || term == null) {
            throw new IllegalArgumentException("Module and semester are required");
        }
        if (yearOfStudy < 1) {
            throw new IllegalArgumentException("Year of study starts at 1");
        }
        this.module = module;
        this.yearOfStudy = yearOfStudy;
        this.term = term;
    }

    /** @return the module */
    public Module getModule() {
        return module;
    }

    /** @return the year of study */
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    /** @return the semester */
    public Term getTerm() {
        return term;
    }

    /** @return e.g. "Year 2 AUTUMN: CS4013 Object Oriented Development (6 credits)" */
    @Override
    public String toString() {
        return "Year " + yearOfStudy + " " + term + ": " + module;
    }
}
