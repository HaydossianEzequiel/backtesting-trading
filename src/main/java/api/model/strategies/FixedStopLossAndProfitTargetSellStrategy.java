package api.model.strategies;

import api.model.StockContext;

public class FixedStopLossAndProfitTargetSellStrategy implements SellStrategy {

    public final Integer stopPercentage;
    public final Integer profitTargetPercentage;


    public FixedStopLossAndProfitTargetSellStrategy(Integer initialStop, Integer profitTargetPercentage) {
        if (initialStop < 1 && initialStop > 70) {
            throw new RuntimeException("Fixed Stop Loss invalid percentage");
        }

        if (profitTargetPercentage < 1 && profitTargetPercentage > 70) {
            throw new RuntimeException("Fixed Profit target invalid percentage");
        }

        this.stopPercentage = initialStop;
        this.profitTargetPercentage = profitTargetPercentage;
    }

    public boolean shouldSell(StockContext stockContext) {
        return breakStopLoss(stockContext) || breakProfitTarget(stockContext);
    }

    private boolean breakProfitTarget(StockContext stockContext) {
        return (((double) stockContext.actualPrice / stockContext.positionPrice) * 100) > (100 + profitTargetPercentage);
    }

    private boolean breakStopLoss(StockContext stockContext) {
        return (((double) stockContext.actualPrice / stockContext.positionPrice) * 100) < (100 - stopPercentage);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.positionPrice != null && stockContext.actualPrice != null;
    }


    @Override
    public String toString() {
        return getClass().getName() + stopPercentage.toString();
    }

}
