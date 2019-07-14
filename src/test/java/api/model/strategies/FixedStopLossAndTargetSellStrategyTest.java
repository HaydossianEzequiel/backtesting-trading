package api.model.strategies;

import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FixedStopLossAndTargetSellStrategyTest {

    @Test
    public void testShouldSell_actualPricesEqualsPositionPrice() {
        FixedStopLossAndProfitTargetSellStrategy strategy = new FixedStopLossAndProfitTargetSellStrategy(3, 10);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_profitLessThanTarget() {
        FixedStopLossAndProfitTargetSellStrategy strategy = new FixedStopLossAndProfitTargetSellStrategy(3, 10);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 20d;
        stockContext.positionPrice = 10d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_profitGreaterThanTarget() {
        FixedStopLossAndProfitTargetSellStrategy strategy = new FixedStopLossAndProfitTargetSellStrategy(3, 10);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10.5d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_actualPriceLessThanPositionPrice() {
        FixedStopLossAndProfitTargetSellStrategy strategy = new FixedStopLossAndProfitTargetSellStrategy(3, 10);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9d;
        stockContext.positionPrice = 10d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_actualPriceBitLessThanPositionPrice() {
        FixedStopLossAndProfitTargetSellStrategy strategy = new FixedStopLossAndProfitTargetSellStrategy(3, 10);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9.7d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
    }


}
