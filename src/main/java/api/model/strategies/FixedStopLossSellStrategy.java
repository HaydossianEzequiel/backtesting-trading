package api.model.strategies;

import api.model.StockContext;

public class FixedStopLossSellStrategy implements SellStrategy {

    public final Integer percentage;

    public FixedStopLossSellStrategy(Integer percentage) {
        if (percentage < 1 && percentage > 70) {
            throw new RuntimeException("Fixed Stop Loss invalid percentage");
        }
        this.percentage = percentage;
    }

    public boolean shouldSell(StockContext stockContext) {
        return (((double) stockContext.actualPrice / stockContext.positionPrice) * 100) < (100-percentage);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.positionPrice != null && stockContext.actualPrice != null;
    }


    @Override
    public String toString() {
        return "FixedStopLossSellStrategy" + percentage.toString();
    }

}
