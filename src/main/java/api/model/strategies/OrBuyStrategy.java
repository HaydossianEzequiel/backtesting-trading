package api.model.strategies;


import api.model.DataStock;
import api.model.StockContext;
import api.services.StockService;

public class OrBuyStrategy implements BuyStrategy {
    //Regla de negocio. No se pueden concatenar medias de mismo valor
    //El updateData no soporta misma operacion dos veces

    public BuyStrategy left;
    public BuyStrategy right;

    public OrBuyStrategy(BuyStrategy left, BuyStrategy right){
        this.left = left;
        this.right = right;
    }
    @Override
    public boolean shouldBuy(StockContext stockContext) {
        return left.shouldBuy(stockContext) || rightShouldBuy(stockContext);
    }

    private boolean rightShouldBuy(StockContext stockContext) {
        return right != null && right.shouldBuy(stockContext);
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return left.hasData(stockContext) && (right == null || right.hasData(stockContext));
    }

    @Override
    public void updateData(StockService stockService, StockContext stockContext, DataStock actualDataStock) {
        left.updateData(stockService, stockContext, actualDataStock);
        if (right != null) {
            right.updateData(stockService, stockContext, actualDataStock);
        }
    }
}
