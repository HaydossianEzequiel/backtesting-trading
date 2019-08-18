package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class OperatorStrategyTest {

    @Test
    public void testGetOperation_AndStrategy_returnSkip() {

        SellStrategy left = new CrossMovingAverageSellStrategy(20, 5);
        SellStrategy right = new CrossMovingAverageSellStrategy(20, 5);

        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new AndSellStrategy(left, right));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SKIP, operation);
    }


    @Test
    public void testGetOperation_AndStrategy_returnSell() {

        SellStrategy left = new CrossMovingAverageSellStrategy(20, 5);
        SellStrategy right = new CrossMovingAverageSellStrategy(20, 5);

        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new AndSellStrategy(left, right));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 20d);
        stockContext.movingAverage.put(20, 21d);
        stockContext.positionPrice = 50d;

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.SELL, operation);
    }


    @Test
    public void testGetOperation_AndStrategy_returnBuy() {
        BuyStrategy left = new CrossMovingAverageBuyStrategy(20, 5);
        BuyStrategy right = new CrossMovingAverageBuyStrategy(20, 5);

        Strategy strategy = new Strategy(new AndBuyStrategy(left, right), new CrossMovingAverageSellStrategy(20, 5));
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 22d);
        stockContext.movingAverage.put(20, 21d);

        Operation operation = strategy.getOperation(stockContext);
        Assert.assertEquals(Operation.BUY, operation);
    }

    @Test
    public void testShouldBuy_AndStrategy_returnFalse_RightFalse() {
        BuyStrategy left = new CrossMovingAverageBuyStrategy(20, 5);
        BuyStrategy right = new CrossMovingAverageBuyStrategy(200, 5);

        BuyStrategy andBuyStrategy = new AndBuyStrategy(left, right);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 22d);
        stockContext.movingAverage.put(20, 21d);


        Assert.assertFalse(andBuyStrategy.shouldBuy(stockContext));
    }

    @Test
    public void testShouldBuy_AndStrategy_returnFalse_rightNull() {
        BuyStrategy left = new CrossMovingAverageBuyStrategy(20, 5);

        BuyStrategy andBuyStrategy = new AndBuyStrategy(left, null);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 22d);
        stockContext.movingAverage.put(20, 21d);


        Assert.assertTrue(andBuyStrategy.shouldBuy(stockContext));
    }



    @Test
    public void testShouldSell_AndStrategy_returnFalse_RightFalse() {
        SellStrategy left = new CrossMovingAverageSellStrategy(20, 5);
        SellStrategy right = new CrossMovingAverageSellStrategy(200, 5);

        SellStrategy andSellStrategy = new AndSellStrategy(left, right);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 22d);
        stockContext.movingAverage.put(20, 21d);


        Assert.assertFalse(andSellStrategy.shouldSell(stockContext));
    }

    @Test
    public void testShouldSell_AndStrategy_returnFalse_rightNull() {
        SellStrategy left = new CrossMovingAverageSellStrategy(20, 5);

        SellStrategy andSellStrategy = new AndSellStrategy(left, null);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.movingAverage.put(5, 20d);
        stockContext.movingAverage.put(20, 21d);
        stockContext.positionPrice = 50d;


        Assert.assertTrue(andSellStrategy.shouldSell(stockContext));
    }


}
