package view;

/**
 * Works out the final award classification from the final cumulative QCA.
 * Bands: First 3.40, 2.1 at 3.00, 2.2 at 2.60, Third 2.00, otherwise no
 * honours.
 */
public class AwardClassification {
	private double finalQca;

	public AwardClassification(double finalQca) {
		this.finalQca = finalQca;
	}

	// Returns the classification name
	public String getClassification() {
		if (finalQca >= 3.30) {
			return "First Class Honours";
		} else if (finalQca >= 2.90) {
			return "Second Class Honours, Grade 1 (2.1)";
		} else if (finalQca >= 2.50) {
			return "Second Class Honours, Grade 2 (2.2)";
		} else if (finalQca >= 2.00) {
			return "Third Class Honours";
		} else {
			return "No honours classification (QCA below 2.00)";
		}
	}

	// Checks if the QCA is within 0.10 below a higher classification (never applies
	// to Third Class).
	public boolean isInDiscretionBand() {
		return (finalQca >= 3.20 && finalQca < 3.30) || (finalQca >= 2.80 && finalQca < 2.90)
				|| (finalQca >= 2.40 && finalQca < 2.50);
	}

	// Final QCA used.
	public double getFinalQca() {
		return finalQca;
	}

	@Override
	public String toString() {
		String text = "Final QCA: " + String.format("%.2f", finalQca) + "\n" + "Classification: "
				+ getClassification();
		if (isInDiscretionBand()) {
			text += "\n(Within the discretionary band: the exam board may consider the higher award.)";
		}
		return text;
	}
}
