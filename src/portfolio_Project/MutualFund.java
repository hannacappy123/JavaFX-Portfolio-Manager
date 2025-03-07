package portfolio_Project;


/**
 * Data describing a mutual fund or ETF
 * 
 * <p>

 * </p>
 * 
 * 
 *
 */
public class MutualFund {
	
	private String ticker;
	private double avgHoldingSize;
	private double minimumInvestment;
	private double valueMeasure;
	private MARKET domicile;
	public enum MARKET {DOMESTIC, INTERNATIONAL, GLOBAL};
	
	
	public MutualFund(String ticker, double averageHoldingSize,
			double minimumInvestmentRequirement, double valueMeasure,
			MARKET location) {
		this.ticker = ticker;
		avgHoldingSize = averageHoldingSize;
		minimumInvestment = minimumInvestmentRequirement;
		this.valueMeasure = valueMeasure;
		domicile = location;
	}

	public double getAvgHoldingSize() {
		return avgHoldingSize;
	}

	public void setAvgHoldingSize(double avgHoldingSize) {
		this.avgHoldingSize = avgHoldingSize;
	}

	public double getMinimumInvestment() {
		return minimumInvestment;
	}

	public void setMinimumInvestment(double minimumInvestment) {
		this.minimumInvestment = minimumInvestment;
	}

	public double getValueMeasure() {
		return valueMeasure;
	}

	public void setValueMeasure(double valueMeasure) {
		this.valueMeasure = valueMeasure;
	}

	public String getTicker() {
		return ticker;
	}

	public MARKET getDomicile() {
		return domicile;
	}

	@Override
	public String toString() {
		return "MutualFund [ticker=" + ticker + ", avgHoldingSize=" + avgHoldingSize + ", minimumInvestment="
				+ minimumInvestment + ", valueMeasure=" + valueMeasure + ", domicile=" + domicile + "]";
	}

}
