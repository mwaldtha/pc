package personal.capital.interview.portfolio;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Unit test class.
 */
@PrepareForTest(PortfolioUtil.class)
public class PortfolioEvaluationTest extends PowerMockTestCase {

    private static final double SMALL_CONSTANT = 0.0000001;

    /**
     * data provider for data driven tests each entry contains: - an array of
     * data values - the expected median value of the array - the expected 90th
     * percentile value - the expected 10th percentile value
     */
    @DataProvider(name = "ArrayData")
    public Object[][] createArrayData() {
        double[] a = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };
        double[] b = { 2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0 };
        double[] c = { 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 2.0 };
        double[] d = { 2.0, 2.0, 2.0, 2.0, 2.0 };
        double[] e = { 1.0, 3.0, 5.0, 7.0, 9.0, 10.0, 8.0, 6.0, 4.0, 2.0 };
        double[] f = { 0.0 };

        Object[][] arrayData = { { a, 5.5, 9.0, 1.0 }, { b, 11.0, 18.0, 2.0 },
                { c, 1.5, 2.0, 1.0 }, { d, 2.0, 2.0, 2.0 },
                { e, 5.5, 9.0, 1.0 }, { f, 0.0, 0.0, 0.0 } };
        return arrayData;
    }

    /**
     * data provider for data driven tests each entry contains: - the initial
     * portfolio value - the annual return rate - the inflation rate - the
     * number of years to perform evaluations - the expected end value of the
     * portfolio
     * 
     * fixed rates of return are used instead of normally distributed random
     * values to allow for expected/actual comparisons during testing
     */
    @DataProvider(name = "AnnualData")
    public Object[][] createAnnualData() {
        Object[][] annualData = { { 0.0, 0.0, 0.0, 10, 0.0 },
                { 1.0, 100, 1.0, 1, 2.0 }, { 1000.0, 0.0, 0.0, 1, 1000.0 },
                { 1000.0, 1.0, 0.0, 1, 1010.0 },
                { 1000.0, 10.0, 0.0, 1, 1100.0 },
                { 1000.0, 0.0, 1.0, 1, 990.0 },
                { 1000.0, 0.0, 10.0, 1, 900.0 },
                { 1000.0, 10.0, 0.0, 10, 2593.74 },
                { 100000.0, 9.4324, 3.5, 20, 316647.63 },
                { 100000.0, 6.189, 3.5, 20, 170011.58 } };
        return annualData;
    }

    /**
     * data provider for data driven tests each entry contains: - the simulation
     * count - the number of years to perform evaluations - the portfolio name -
     * the initial portfolio value - the inflation rate - the expected end value
     * of the portfolio
     */
    @DataProvider(name = "EvaluationData")
    public Object[][] createEvaluationData() {
        Object[][] evaluationData = { { 10, 10, 100, 0.0, 110.46 },
                { 10, 10, 100, 1.0, 100.00 }, { 100, 100, 1000, 0.0, 2704.81 },
                { 100, 100, 1000, 1.0, 1000.00 }, { 0, 0, 1000, 1.0, 1000.00 },
                { 1, 1, 1, 1.0, 1.00 } };
        return evaluationData;
    }

    /**
     * Tests the PortfolioUtil.evaluatePortfolio method using the data supplied
     * by the specified data provider.
     * 
     * @param simulationCount
     * @param evaluationLength
     * @param initialInvestment
     * @param inflationRate
     * @param expectedValue
     * @throws Exception
     */
    @Test(dataProvider = "EvaluationData")
    public void testEvaluatePortfolio(int simulationCount,
            int evaluationLength, double initialInvestment,
            double inflationRate, double expectedValue) throws Exception {

        // set dummy values for returnRate and risk as they are not used, since
        // the call to getAnnualReturnRate is mocked, in order to remove the
        // random number generation and allow expected results to be compared
        // with actual results
        double returnRate = 0.0;
        double risk = 0.0;

        spy(PortfolioUtil.class);
        doReturn(1.0).when(PortfolioUtil.class, "getAnnualReturnRate",
                returnRate, risk);

        double[] portfolioValues = PortfolioUtil.evaluatePortfolio(
                simulationCount, evaluationLength, initialInvestment,
                returnRate, risk, inflationRate);

        Assert.assertEquals(portfolioValues.length,
                (simulationCount != 0 ? simulationCount : 1));

        // each value in the portfolioValues array should be the same since we
        // are suing the mocked call to getAnnualReturnRate, so just verify the
        // first element in the array
        Assert.assertTrue(Math.round(portfolioValues[0] * 100) / 100.0
                - expectedValue < SMALL_CONSTANT);
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
    public void testGetAdjustedPortfolioValue(double portfolioValue,
            double annualReturnRate, double inflationRate, int years,
            double expectedValue) {
        double value = portfolioValue;

        for (int y = 0; y < years; y++) {
            value = PortfolioUtil.getAdjustedPortfolioValue(value,
                    annualReturnRate, inflationRate);
        }

        Assert.assertTrue(Math.round(value * 100) / 100.0 - expectedValue < SMALL_CONSTANT);
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
    public void testGetMedian(double[] values, double median, double ninetieth,
            double tenth) {
        Assert.assertTrue(PortfolioUtil.getMedian(values) - median < SMALL_CONSTANT);
        Assert.assertTrue(PortfolioUtil.getPercentile(values, 0.9) - ninetieth < SMALL_CONSTANT);
        Assert.assertTrue(PortfolioUtil.getPercentile(values, 0.1) - tenth < SMALL_CONSTANT);
    }
}
