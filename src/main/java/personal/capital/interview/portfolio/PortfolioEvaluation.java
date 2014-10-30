package personal.capital.interview.portfolio;

import java.math.BigDecimal;

public class PortfolioEvaluation {
    private static final int SIMULATION_COUNT = 10000;
    private static final BigDecimal INITIAL_INVESTMENT = new BigDecimal(
            "100000.00");
    private static final BigDecimal INFLATION_RATE = new BigDecimal("0.035"); // 3.5%
    private static final int EVALUATION_YEARS = 20;

    // define the return/risk of each portfolio to be evaluated
    private enum Portfolio {
        AGGRESSIVE("9.4324", "15.675"), CONSERVATIVE("6.189", "6.3438");

        private final BigDecimal mean; // return
        private final BigDecimal deviation; // risk

        Portfolio(String mean, String deviation) {
            this.mean = new BigDecimal(mean);
            this.deviation = new BigDecimal(deviation);
        }
    }

    /**
     * Execution of this main method will perform a number of simulations
     * (defined in SIMULATION_COUNT) to evaluate the value of each portfolio
     * (defined in the Portfolio enum) over a number of years (defined in
     * EVALUATION_YEARS) using the given INFLATION_RATE and the return/risk
     * values for each portfolio.
     * 
     * @param args
     *            Required by the main method signature, but unused
     */
    public static void main(String[] args) {
        System.out.println("Running " + SIMULATION_COUNT + " simulations...");

        // loop through every portfolio
        for (Portfolio p : Portfolio.values()) {
            BigDecimal[] portfolioValues = new BigDecimal[SIMULATION_COUNT];

            // perform SIMULATION_COUNT simulations
            for (int x = 0; x < SIMULATION_COUNT; x++) {
                BigDecimal portfolioValue = INITIAL_INVESTMENT;

                // collect data in EVALUATION_YEARS sized 'chunks'
                for (int y = 0; y < EVALUATION_YEARS; y++) {
                    BigDecimal annualReturnRate = PortfolioUtil
                            .getAnnualReturnRate(p.mean, p.deviation);
                    portfolioValue = PortfolioUtil.getAdjustedPortfolioValue(
                            portfolioValue, annualReturnRate, INFLATION_RATE);
                }
                portfolioValues[x] = portfolioValue;
            }

            System.out.printf("%s median: %.2f%n", p.name(),
                    PortfolioUtil.getMedian(portfolioValues));
            System.out.printf("%s 10%% best case: %.2f%n", p.name(),
                    PortfolioUtil.getPercentile(portfolioValues, .90));
            System.out.printf("%s 10%% worst case: %.2f%n", p.name(),
                    PortfolioUtil.getPercentile(portfolioValues, .10));
        }
    }

}
