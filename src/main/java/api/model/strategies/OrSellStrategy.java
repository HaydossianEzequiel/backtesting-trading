package api.model.strategies;


import api.model.StockContext;

public class OrSellStrategy implements SellStrategy {

    public SellStrategy left;
    public SellStrategy right;

    public OrSellStrategy(SellStrategy left, SellStrategy right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean shouldSell(StockContext stockContext) {
        return left.shouldSell(stockContext) || rightShouldSell(stockContext);
    }

    private boolean rightShouldSell(StockContext stockContext) {
        return right != null && right.shouldSell(stockContext);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return left.hasData(stockContext) && (right == null || right.hasData(stockContext));
    }
}
