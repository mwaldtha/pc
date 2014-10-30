package personal.capital.interview.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Unit test class.
 */
public class PortfolioEvaluationTest {

    /**
     * data provider for data driven tests each entry contains: - an array of
     * data values - the expected median value of the array - the expected 90th
     * percentile value - the expected 10th percentile value
     */
    @DataProvider(name = "ArrayData")
    public Object[][] createArrayData() {
        String[] a = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        String[] b = { "2", "4", "6", "8", "10", "12", "14", "16", "18", "20" };
        String[] c = { "1", "1", "1", "1", "1", "2", "2", "2", "2", "2" };
        String[] d = { "2", "2", "2", "2", "2" };
        String[] e = { "1", "3", "5", "7", "9", "10", "8", "6", "4", "2" };
        String[] f = { "0" };

        Object[][] arrayData = { { createBigDecimalArray(a), "5.5", "9", "1" },
                { createBigDecimalArray(b), "11", "18", "2" },
                { createBigDecimalArray(c), "1.5", "2", "1" },
                { createBigDecimalArray(d), "2", "2", "2" },
                { createBigDecimalArray(e), "5.5", "9", "1" },
                { createBigDecimalArray(f), "0", "0", "0" }, };
        return arrayData;
    }

    /**
     * data provider for data driven tests each entry contains: - the initial
     * portfolio value - the annual return rate - the inflation rate - the
     * number of years to perform evaluations - the expected end value of the
     * portfolio
     * 
     * fixed rates of return are used instead of normally distributed randomm
     * values to allow for expected/actual comparisons during testing
     */
    @DataProvider(name = "AnnualData")
    public Object[][] createAnnualData() {
        Object[][] annualData = { { "0", "0", "0", 10, "0" },
                { "1", "1.0", "1.0", 1, "0" },
                { "1000", "0.0", "0.0", 1, "1000" },
                { "1000", "0.01", "0.0", 1, "1010" },
                { "1000", "0.1", "0.0", 1, "1100" },
                { "1000", "0.0", "0.01", 1, "990" },
                { "1000", "0.0", "0.1", 1, "900" },
                { "1000", "0.1", "0.0", 10, "2593.74" },
                { "100000", "0.094324", "0.035", 20, "297484.78" },
                { "100000", "0.06189", "0.035", 20, "162980.97" } };
        return annualData;
    }

    /**
     * Utility method just to simplify the creation of each array of BigDecimal
     * objects
     * 
     * @param values
     *            string values used for creating the BigDecimmal objects
     * @return an array of BigDecimal objects corresponding to the supplied
     *         strings
     */
    private BigDecimal[] createBigDecimalArray(String[] values) {
        int x = 0;
        BigDecimal[] returnValues = new BigDecimal[values.length];

        for (String s : values) {
            returnValues[x++] = new BigDecimal(s);
        }

        return returnValues;
    }

    /**
     * Tests the PortfolioUtil.getAdjustedPortfolioValue method using the data
     * supplied by the specified data provider.
     * 
     * @param portfolioValue
     * @param annualReturnRate
     * @param inflationRate
     * @param years
     * @param expectedValue
     */
    @Test(dataProvider = "AnnualData")
    public void testGetAdjustedPortfolioValue(String portfolioValue,
            String annualReturnRate, String inflationRate, int years,
            String expectedValue) {
        BigDecimal value = new BigDecimal(portfolioValue);

        for (int y = 0; y < years; y++) {
            value = PortfolioUtil.getAdjustedPortfolioValue(value,
                    new BigDecimal(annualReturnRate), new BigDecimal(
                            inflationRate));
        }

        Assert.assertTrue(value.setScale(2, RoundingMode.HALF_EVEN).compareTo(
                new BigDecimal(expectedValue)) == 0);
    }

    /**
     * Tests the PortfolioUtil.getMedian/getPercentile methods using the data
     * supplied by the specified data provider.
     * 
     * @param values
     * @param medianString
     * @param ninetiethString
     * @param tenthString
     */
    @Test(dataProvider = "ArrayData")
    public void testGetMedian(BigDecimal[] values, String medianString,
            String ninetiethString, String tenthString) {
        Assert.assertTrue(PortfolioUtil.getMedian(values).compareTo(
                new BigDecimal(medianString)) == 0);
        Assert.assertTrue(PortfolioUtil.getPercentile(values, 0.9).compareTo(
                new BigDecimal(ninetiethString)) == 0);
        Assert.assertTrue(PortfolioUtil.getPercentile(values, 0.1).compareTo(
                new BigDecimal(tenthString)) == 0);
    }
}
