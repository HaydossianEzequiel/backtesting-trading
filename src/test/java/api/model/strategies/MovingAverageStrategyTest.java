package api.model.strategies;

import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class MovingAverageStrategyTest {

    @Test
    public void testShouldBuy_whenAlreadyBuy() {
        MovingAverageBuyStrategy strategy = new MovingAverageBuyStrategy(20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(20, 21d);
        stockContext.positionPrice = 1d;

        Assert.assertFalse(strategy.shouldBuy(stockContext));
    }

    @Test
    public void testShouldBuy_whenActualPriceBelowMovingAverage() {
        MovingAverageBuyStrategy strategy = new MovingAverageBuyStrategy(20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(20, 21d);
        stockContext.actualPrice = 20d;

        Assert.assertFalse(strategy.shouldBuy(stockContext));
    }

    @Test
    public void testShouldBuy_whenActualPriceAboveMovingAverage() {
        MovingAverageBuyStrategy strategy = new MovingAverageBuyStrategy(20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(20, 21d);
        stockContext.actualPrice = 23d;

        Assert.assertTrue(strategy.shouldBuy(stockContext));
    }


}
