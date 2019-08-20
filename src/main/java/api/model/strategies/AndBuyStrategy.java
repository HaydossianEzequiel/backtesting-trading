package api.model.strategies;


import api.model.StockContext;

public class AndBuyStrategy implements BuyStrategy {

    public BuyStrategy left;
    public BuyStrategy right;

    public AndBuyStrategy(BuyStrategy left, BuyStrategy right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean shouldBuy(StockContext stockContext) {
        return left.shouldBuy(stockContext) && rightShouldBuy(stockContext);
    }

    private boolean rightShouldBuy(StockContext stockContext) {
        return right == null || right.shouldBuy(stockContext);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return left.hasData(stockContext) && (right == null || right.hasData(stockContext));
    }
}
