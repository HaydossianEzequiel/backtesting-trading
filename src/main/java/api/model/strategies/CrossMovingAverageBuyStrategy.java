package api.model.strategies;

import api.model.DataStock;
import api.model.StockContext;
import api.services.StockService;

public class CrossMovingAverageBuyStrategy implements BuyStrategy {

    public final Integer slow;
    public final Integer fast;


    public CrossMovingAverageBuyStrategy(Integer slow, Integer fast) {
        this.slow = slow;
        this.fast = fast;
    }

    public boolean shouldBuy(StockContext stockContext) {
        if (!hasData(stockContext)) {
            return false;
        }
        if (stockContext.positionPrice != null) {
            return false;
        }
        return stockContext.movingAverage.get(fast) > stockContext.movingAverage.get(slow);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.movingAverage.containsKey(fast) && stockContext.movingAverage.containsKey(slow);
    }

    @Override
    public void updateData(StockService stockService, StockContext stockContext, DataStock actualDataStock) {
        stockService.updateMovingAverage(stockContext, fast, actualDataStock);
        stockService.updateMovingAverage(stockContext,slow, actualDataStock);
    }

    @Override
    public String toString() {
        return getClass().getName() + slow.toString() + "_" + fast.toString();
    }


}
