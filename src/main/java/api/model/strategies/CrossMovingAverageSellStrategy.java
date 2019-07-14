package api.model.strategies;

import api.model.StockContext;

public class CrossMovingAverageSellStrategy implements SellStrategy {

    public final Integer slow;
    public final Integer fast;


    public CrossMovingAverageSellStrategy(Integer slow, Integer fast) {
        this.slow = slow;
        this.fast = fast;
    }

    public boolean shouldSell(StockContext stockContext) {
        if (stockContext.positionPrice == null) {
            return false;
        }
        return stockContext.movingAverage.get(fast) < stockContext.movingAverage.get(slow);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.movingAverage.containsKey(fast) && stockContext.movingAverage.containsKey(slow);
    }


    @Override
    public String toString() {
        return getClass().getName() + slow.toString() + "_" + fast.toString();
    }

}
