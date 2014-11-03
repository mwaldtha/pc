package personal.capital.interview.portfolio;

public class PortfolioEvaluation {
    private static final int DEFAULT_SIMULATION_COUNT = 10000;
    private static final int DEFAULT_EVALUATION_LENGTH = 20;
    private static final double DEFAULT_INITIAL_INVESTMENT = 100000.00;
    private static final double DEFAULT_INFLATION_RATE = 3.5;

    /**
     * Execution of this main method will perform a number of simulations to
     * evaluate the value of a portfolio over a number of years using the given
     * inflation rate, initial value, and the return/risk values for each
     * portfolio. Prints values of the median, 90th percentile, and 10th
     * percentile values to standard out along with the parameter values used.
     * 
     * @param args
     *            array of values to initialize parameters
     */
    public static void main(String[] args) {

        String portfolioName = "";
        double returnRate = 0.0;
        double risk = 0.0;
        double initialInvestment = DEFAULT_INITIAL_INVESTMENT;
        double inflationRate = DEFAULT_INFLATION_RATE;
        int evaluationLength = DEFAULT_EVALUATION_LENGTH;
        int simulationCount = DEFAULT_SIMULATION_COUNT;

        // process command line argumments
        if (args.length < 3) {
            printUsage();
            System.exit(1);
        } else {
            try {
                portfolioName = args[0];
                returnRate = Double.parseDouble(args[1]);
                risk = Double.parseDouble(args[2]);

                if (args.length >= 4)
                    initialInvestment = Double.parseDouble(args[3]);
                if (args.length >= 5)
                    inflationRate = Double.parseDouble(args[4]);
                if (args.length >= 6)
                    evaluationLength = Integer.parseInt(args[5]);
                if (args.length >= 7)
                    simulationCount = Integer.parseInt(args[6]);
            } catch (NumberFormatException nfe) {
                System.out
                        .println("At least one of the required values was invalid.");
                nfe.printStackTrace();
                printUsage();
                System.exit(1);
            }
        }

        System.out.printf("Running %d simulations for the '%s' portfolio.%n",
                simulationCount, portfolioName);
        System.out.printf("Using the following values:%n");
        System.out.printf("%-20s%.2f%n", "Initial investment:",
                initialInvestment);
        System.out.printf("%-20s%d%n", "Evaluation length:", evaluationLength);
        System.out.printf("%-20s%.4f%n", "Inflation rate:", inflationRate);
        System.out.printf("%-20s%.4f%n", "Return:", returnRate);
        System.out.printf("%-20s%.4f%n----------%n", "Risk:", risk);

        // do actual portfolio evaluation simulations
        double[] portfolioValues = PortfolioUtil.evaluatePortfolio(
                simulationCount, evaluationLength, initialInvestment,
                returnRate, risk, inflationRate);

        System.out.printf("%s median: %.2f%n", portfolioName,
                PortfolioUtil.getMedian(portfolioValues));
        System.out.printf("%s 10%% best case: %.2f%n", portfolioName,
                PortfolioUtil.getPercentile(portfolioValues, .90));
        System.out.printf("%s 10%% worst case: %.2f%n", portfolioName,
                PortfolioUtil.getPercentile(portfolioValues, .10));
    }

    /**
     * Utility method to print command line usage information.
     */
    private static void printUsage() {
        System.out
                .println("USAGE: java PortfolioEvaluation portfolioName returnRate risk [initialInvestment] [inflationRate] [evaluationLength] [simulationCount]");
        System.out.println("");
        System.out
                .println("Percentages should not be converted to their decimal representations (i.e. enter 3.5 for 3.5% not 0.035");
        System.out
                .println("The following arguments are all optional and default to the following values:");
        System.out.println("initialInvestment = " + DEFAULT_INITIAL_INVESTMENT);
        System.out.println("inflationRate = " + DEFAULT_INFLATION_RATE);
        System.out.println("evaluationLength = " + DEFAULT_EVALUATION_LENGTH);
        System.out.println("simulationCount = " + DEFAULT_SIMULATION_COUNT);
        System.out.println("");
        System.out.println("java PortfolioEvaluation Aggressive 9.4324 15.675");
        System.out
                .println("java PortfolioEvaluation Conservative 6.189 6.3438 1000 3.2 50 100000");
    }

}
