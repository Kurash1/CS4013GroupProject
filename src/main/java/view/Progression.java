package view;

/**
 * Decides whether a student meets the minimum standard to progress.
 * Rule used: QCA of at least 2.00 AND no failed modules (no grade below D2).
 */
public class Progression {
	public static final double MINIMUM_QCA = 2.0;

	private double qca;
	private int failedModules;

	// Creates a progression check.
	public Progression(double qca, int failedModules) {
		this.qca = qca;
		this.failedModules = failedModules;
	}

	// tells if student can progress.
	public boolean canProgress() {
		return qca >= MINIMUM_QCA && failedModules == 0;
	}

	public String getStatus() {
		if (canProgress()) {
			return "PROGRESS";
		}
		return "DOES NOT MEET STANDARD";
	}

	// Short explanation of the status.
	public String getReason() {
		if (canProgress()) {
			return "QCA is at least 2.00 and there are no failed modules.";
		}
		String reason = "";
		if (qca < MINIMUM_QCA) {
			reason += "QCA is below 2.00. ";
		}
		if (failedModules > 0) {
			reason += failedModules + " failed module(s).";
		}
		return reason.trim();
	}

	// Returns QCA used.
	public double getQca() {
		return qca;
	}

	// Returns number of failed modules.
	public int getFailedModules() {
		return failedModules;
	}
}
