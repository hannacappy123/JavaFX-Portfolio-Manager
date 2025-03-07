package portfolio_Project;


public class FundsDef {
	
	//ticker, averageHoldingSize, minimumInvestmentRequirement, valueMeasure, MARKET 
	public static final MutualFund[] INITIAL_FUNDS = {
			new MutualFund("VIOV",   1500.0,  143,    .4,  MutualFund.MARKET.DOMESTIC),
			new MutualFund("VT",    38300.0,   75,  -.02,  MutualFund.MARKET.GLOBAL),
			new MutualFund("DFEOX", 32400.0,    0,   .07,  MutualFund.MARKET.DOMESTIC),
			new MutualFund("VWIGX", 56000.0, 3000,  -.09,  MutualFund.MARKET.INTERNATIONAL),
			new MutualFund("DFISX",  1700.0,    0,   .33,  MutualFund.MARKET.INTERNATIONAL),
			new MutualFund("BOSVX",   700.0,    0,   .68,  MutualFund.MARKET.DOMESTIC),
			new MutualFund("VTSMX", 56000.0, 3000,   .02,  MutualFund.MARKET.DOMESTIC),
	};

}
