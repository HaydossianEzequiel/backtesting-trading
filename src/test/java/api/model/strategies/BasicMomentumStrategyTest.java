package api.model.strategies;

import api.model.StockContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BasicMomentumStrategyTest {

    @Test
    public void testShouldBuy() {
        BasicMomentumBuyStrategy strategy = new BasicMomentumBuyStrategy(20, 0.5d, 0.75d, 0.75d);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.minLastDays.put(20, 10d);
        stockContext.maxLastDays.put(20, 20d);
        stockContext.actualPrice = 11.5d;

        Assert.assertTrue(strategy.shouldBuy(stockContext));
    }

    @Test
    public void testShouldBuy_returnFalse_min() {
        BasicMomentumBuyStrategy strategy = new BasicMomentumBuyStrategy(20, 0.1d, 0.75d, 0.75d);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.minLastDays.put(20, 10d);
        stockContext.maxLastDays.put(20, 20d);
        stockContext.actualPrice = 11.5d;

        Assert.assertFalse(strategy.shouldBuy(stockContext));
    }

    @Test
    public void testShouldBuy_returnFalse_max(){
        BasicMomentumBuyStrategy strategy = new BasicMomentumBuyStrategy(20, 0.5d, 0.75d, 0.75d);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.minLastDays.put(20, 10d);
        stockContext.maxLastDays.put(20, 20d);
        stockContext.actualPrice = 11.5d;

        Assert.assertFalse(strategy.shouldBuy(stockContext));
    }


    @Test
    public void testShouldBuy_returnFalse_litleRange(){
        BasicMomentumBuyStrategy strategy = new BasicMomentumBuyStrategy(20, 0.5d, 0.75d, 0.75d);
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        stockContext.minLastDays.put(20, 11d);
        stockContext.maxLastDays.put(20, 12d);
        stockContext.actualPrice = 11.5d;

        Assert.assertFalse(strategy.shouldBuy(stockContext));
    }



}
