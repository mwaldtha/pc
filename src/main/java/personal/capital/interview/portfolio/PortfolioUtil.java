package personal.capital.interview.portfolio;

import java.util.Arrays;
import java.util.Random;

public final class PortfolioUtil {

    private static final Random r = new Random();

    /**
     * Evaluates the future value of a portfolio, given the passed in parameter
     * values.
     * 
     * @param simulationCount
     *            Number of simulations to run
     * @param evaluationLength
     *            Length of time of each simulation
     * @param initialInvestment
     *            Initial amount of the portfolio
     * @param returnRate
     *            Given return rate of investment strategy
     * @param risk
     *            Given amount of risk (standard deviation) of investment
     *            strategy
     * @param inflationRate
     *            Estimated rate of inflation for the simulation periods
     * @return an array containing the portfolio values after each simulation
     */
    public static final double[] evaluatePortfolio(int simulationCount,
            int evaluationLength, double initialInvestment, double returnRate,
            double risk, double inflationRate) {

        double[] portfolioValues = new double[(simulationCount != 0) ? simulationCount
                : 1];

        if (simulationCount == 0 || evaluationLength == 0) {
            portfolioValues[0] = initialInvestment;
        } else {
            // perform simulationCount simulations
            for (int x = 0; x < simulationCount; x++) {
                double portfolioValue = initialInvestment;

                // collect data in evaluationLength sized 'chunks'
                for (int y = 0; y < evaluationLength; y++) {
                    double annualReturnRate = getAnnualReturnRate(returnRate,
                            risk);
                    portfolioValue = getAdjustedPortfolioValue(portfolioValue,
                            annualReturnRate, inflationRate);
                }
                portfolioValues[x] = portfolioValue;
            }
        }

        return portfolioValues;
    }

    /**
     * Returns the value of a portfolio after adding the amount earned (+/-)
     * based on the annual return rate and adjusting for inflation.
     * 
     * @param portfolioValue
     *            the initial value of the portfolio before adjustments are made
     * @param annualReturnRate
     *            the rate of return being earned
     * @param inflationRate
     *            the rate of inflation
     * @return the adjusted value of the portfolio
     */
    public static final double getAdjustedPortfolioValue(double portfolioValue,
            double annualReturnRate, double inflationRate) {
        double annualReturn = portfolioValue * (annualReturnRate / 100.0);
        double inflationAdjustment = portfolioValue * (inflationRate / 100.0);

        return portfolioValue + annualReturn - inflationAdjustment;
    }

    /**
     * Scales a Gaussian (normal distribution) random number around the mean and
     * deviation supplied
     * 
     * @param mean
     *            average return of the portfolio
     * @param deviation
     *            standard deviation (risk) of the portfolio
     * @return a decimal percentage of the calculated return rate
     */
    private static final double getAnnualReturnRate(double mean,
            double deviation) {
        return ((r.nextGaussian() * deviation) + mean);
    }

    /**
     * Finds the median value of the specified values.
     * 
     * @param values
     *            an array of values
     * @return the median value of the supplied values
     */
    public static final double getMedian(double[] values) {

        // sort the array to ensure a correct return value
        Arrays.sort(values);

        if (values.length % 2 == 0) {
            int leftIndex = (values.length - 1) / 2;
            int rightIndex = values.length / 2;

            return (values[leftIndex] + values[rightIndex]) / 2.0;
        } else {
            return values[(values.length - 1) / 2];
        }
    }

    /**
     * Returns the value, from the array of values specified, located at the
     * percent specified.
     * 
     * @param values
     *            an array of values
     * @param percent
     *            percentile value to locate
     * @return value found in the array at the specified percentile
     */
    public static final double getPercentile(double[] values, double percent) {

        // sort the array to ensure a correct return value
        Arrays.sort(values);

        return values[(int) Math.ceil(percent * values.length) - 1];
    }
}
