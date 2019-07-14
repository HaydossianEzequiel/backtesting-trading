package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CrossMovingAverageStrategyTest {

    @Test
    public void testGetOperation_buy() {
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new CrossMovingAverageSellStrategy(20, 5));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 22d);
        stockContext.movingAverage.put(20, 21d);

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.BUY, operation);
    }

    @Test
    public void testGetOperation_sell() {
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new CrossMovingAverageSellStrategy(20, 5));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 20d);
        stockContext.movingAverage.put(20, 21d);
        stockContext.positionPrice = 50d;

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SELL, operation);
    }

    @Test
    public void testGetOperation_skipWithoutData() {
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new CrossMovingAverageSellStrategy(20, 5));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SKIP, operation);
    }

}
