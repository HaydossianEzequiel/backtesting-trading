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
        stockContext.profitTargetPrice = 11d;
        stockContext.stopPrice = 9.7d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(stockContext.profitTargetPrice.floatValue() == 11f);
        Assert.assertTrue(stockContext.stopPrice.floatValue() == 9.7f);

    }

    @Test
    public void testShouldSell_notSellWhenPositionPriceGreaterThanActual() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10d;
        stockContext.positionPrice = 11d;
        stockContext.profitTargetPrice = 11d;
        stockContext.stopPrice = 9.7d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(stockContext.profitTargetPrice == 11);
        Assert.assertTrue(stockContext.stopPrice == 9.7);
    }

    @Test
    public void testShouldSell_notSellAndElevateProfitAndStop() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 12d;
        stockContext.positionPrice = 10d;
        stockContext.stopPrice = 9.7d;
        stockContext.profitTargetPrice = 11d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
        Assert.assertTrue(stockContext.profitTargetPrice.floatValue() == 12.1f);
        Assert.assertTrue(stockContext.stopPrice.floatValue() == 10.185f);
    }

    @Test
    public void testShouldSell_SellWhenBreakStop() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9d;
        stockContext.positionPrice = 10d;
        stockContext.stopPrice = 9.7d;
        stockContext.profitTargetPrice = 11d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
        Assert.assertTrue(stockContext.stopPrice == 9.7);
    }

    @Test
    public void testShouldSell_updateProfitTargetPrice() {
        TrailingStopSellStrategy strategy = new TrailingStopSellStrategy(3, 10, 5);
        StockContext stockContext = new StockContext(null, new ArrayList<>());
        stockContext.profitTargetPrice = 90d;
        strategy.updateProfitTargetPrice(stockContext);
        Assert.assertTrue(stockContext.profitTargetPrice.floatValue() == 99f);

    }


}
