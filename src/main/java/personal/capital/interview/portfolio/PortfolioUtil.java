package personal.capital.interview.portfolio;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public final class PortfolioUtil {

    private static final Random r = new Random();

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
    public static final BigDecimal getAdjustedPortfolioValue(
            BigDecimal portfolioValue, BigDecimal annualReturnRate,
            BigDecimal inflationRate) {
        BigDecimal annualReturn = portfolioValue.multiply(annualReturnRate);
        portfolioValue = portfolioValue.add(annualReturn);

        BigDecimal inflationAdjustment = portfolioValue.multiply(inflationRate);
        return portfolioValue.subtract(inflationAdjustment);
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
    public static final BigDecimal getAnnualReturnRate(BigDecimal mean,
            BigDecimal deviation) {
        return ((BigDecimal.valueOf(r.nextGaussian()).multiply(deviation))
                .add(mean)).divide(new BigDecimal("100.0"));
    }

    /**
     * Finds the median value of the specified values.
     * 
     * @param values
     *            an array of values
     * @return the median value of the supplied values
     */
    public static final BigDecimal getMedian(BigDecimal[] values) {

        // sort the array to ensure a correct return value
        Arrays.sort(values);

        if (values.length % 2 == 0) {
            int leftIndex = (values.length - 1) / 2;
            int rightIndex = values.length / 2;

            return (values[leftIndex].add(values[rightIndex]))
                    .divide(new BigDecimal("2.0"));
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
    public static final BigDecimal getPercentile(BigDecimal[] values,
            double percent) {

        // sort the array to ensure a correct return value
        Arrays.sort(values);

        return values[(int) Math.ceil(percent * values.length) - 1];
    }

}
