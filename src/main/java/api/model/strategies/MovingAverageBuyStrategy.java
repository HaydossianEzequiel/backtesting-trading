package api.model.strategies;

import api.model.StockContext;

public class MovingAverageBuyStrategy implements BuyStrategy {

    public final Integer average;


    public MovingAverageBuyStrategy(Integer average) {
        this.average = average;
    }

    public boolean shouldBuy(StockContext stockContext) {
        if (!hasData(stockContext)) {
            return false;
        }
        if (stockContext.positionPrice != null) {
            return false;
        }
        return stockContext.movingAverage.get(average) < stockContext.actualPrice;
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.movingAverage.containsKey(average);
    }

    @Override
    public String toString() {
        return getClass().getName() + average.toString();
    }


}
