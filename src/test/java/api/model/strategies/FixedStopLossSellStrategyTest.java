package api.model.strategies;

import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class FixedStopLossSellStrategyTest {

    @Test
    public void testShouldSell_actualPricesEqualsPositionPrice() {
        FixedStopLossSellStrategy strategy = new FixedStopLossSellStrategy(3);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 10d;
        stockContext.positionPrice = 10d;


        Assert.assertFalse(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_actualPriceGreatherThanPositionPrice() {
        FixedStopLossSellStrategy strategy = new FixedStopLossSellStrategy(3);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 20d;
        stockContext.positionPrice = 10d;

        Assert.assertFalse(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_actualPriceLessThanPositionPrice() {
        FixedStopLossSellStrategy strategy = new FixedStopLossSellStrategy(3);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9d;
        stockContext.positionPrice = 10d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_actualPriceBitLessThanPositionPrice() {
        FixedStopLossSellStrategy strategy = new FixedStopLossSellStrategy(3);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.actualPrice = 9.7d;
        stockContext.positionPrice = 10d;

        Assert.assertTrue(strategy.shouldSell(stockContext));
    }


}
