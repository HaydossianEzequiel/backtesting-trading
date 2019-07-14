package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CrossMovingAverageStrategyTest {

    @Test
    public void testGetOperation_buy()  {
        CrossMovingAverageStrategy crossMovingAverageStrategy = new CrossMovingAverageStrategy(5, 20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 20d);
        stockContext.movingAverage.put(20, 21d);

        Operation operation = crossMovingAverageStrategy.getOperation(stockContext);
        Assert.assertEquals(Operation.BUY, operation);
    }

    @Test
    public void testGetOperation_sell()  {
        CrossMovingAverageStrategy crossMovingAverageStrategy = new CrossMovingAverageStrategy(5, 20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 23d);
        stockContext.movingAverage.put(20, 21d);
        stockContext.positionPrice = 50d;

        Operation operation = crossMovingAverageStrategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SELL, operation);
    }

    @Test
    public void testGetOperation_skipWithoutData()  {
        CrossMovingAverageStrategy crossMovingAverageStrategy = new CrossMovingAverageStrategy(5, 20);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 23d);
        stockContext.movingAverage.put(20, 21d);

        Operation operation = crossMovingAverageStrategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SKIP, operation);
    }

}
