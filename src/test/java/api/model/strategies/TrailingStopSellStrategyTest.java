package api.model.strategies;

import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TrailingStopSellStrategyTest {


    @Test
    public void testShouldSell_actualPricesEqualsPositionPrice() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(strategy.profitTargetPrice.intValue() == 11);
        Assert.assertTrue(strategy.stopPrice == 9.7);

    }

    @Test
    public void testShouldSell_notSellWhenPositionPriceGreaterThanActual() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10d;
        stockContext.positionPrice = 11d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(strategy.profitTargetPrice.intValue() == 11);
        Assert.assertTrue(strategy.stopPrice == 9.7);
    }

    @Test
    public void testShouldSell_notSellAndElevateProfitAndStop() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 12d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(strategy.profitTargetPrice.floatValue() == 12.1f);
        Assert.assertTrue(strategy.stopPrice == 10.185);
    }

    @Test
    public void testShouldSell_SellWhenBreakStop() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9d;
        stockContext.positionPrice = 10d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
        Assert.assertTrue(strategy.stopPrice == 9.7);
    }



}
