package api.model;

import api.services.StockService;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;


public class StockServiceTest {

    @Test
    public void testUpdateMovingAverage_sixData() throws ParseException {
        StockService stockService = new StockService();
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        DataStock dataStock = new DataStock("01-04-2000", null, null, null, "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-05-2000", null, null, null, "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-06-2000", null, null, null, "11");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-07-2000", null, null, null, "12");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-08-2000", null, null, null, "13");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-09-2000", null, null, null, "14");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        Assert.assertEquals(12, stockContext.movingAverage.get(5).intValue());
    }

    @Test
    public void testUpdateMovingAverage_nullWhenFiveData() throws ParseException {
        StockService stockService = new StockService();
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        DataStock dataStock = new DataStock("01-04-2000", null, null, null, "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-05-2000", null, null, null, "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-06-2000", null, null, null, "11");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-07-2000", null, null, null, "12");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-08-2000", null, null, null, "13");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        Assert.assertNull(stockContext.movingAverage);
    }


}
