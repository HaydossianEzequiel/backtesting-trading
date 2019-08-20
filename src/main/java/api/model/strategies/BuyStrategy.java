package api.model.strategies;

import api.model.DataStock;
import api.model.StockContext;
import api.services.StockService;

public interface BuyStrategy {

    boolean shouldBuy(StockContext stockContext);

    boolean hasData(StockContext stockContext);

    void updateData(StockService stockService, StockContext stockContext, DataStock actualDataStock);

}
