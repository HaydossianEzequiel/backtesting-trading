package api.model.strategies;

import api.model.StockContext;

interface BuyStrategy {

    boolean shouldBuy(StockContext stockContext);

    boolean hasData(StockContext stockContext);

}
