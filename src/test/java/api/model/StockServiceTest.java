package api.model;

import api.model.strategies.*;
import api.services.StockService;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class StockServiceTest {

    @Test
    public void testUpdateMovingAverage_sixData() {
        StockService stockService = new StockService();
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        DataStock dataStock = new DataStock("01-04-2000", "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-05-2000", "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-06-2000", "11");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-07-2000", "12");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-08-2000", "13");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-09-2000", "14");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        Assert.assertEquals(12, stockContext.movingAverage.get(5).intValue());
    }

    @Test
    public void testUpdateMovingAverage_nullWhenFiveData() {
        StockService stockService = new StockService();
        StockContext stockContext = new StockContext("ggal", new ArrayList<>());
        DataStock dataStock = new DataStock("01-04-2000", "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-05-2000", "10");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-06-2000", "11");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-07-2000", "12");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        dataStock = new DataStock("01-08-2000", "13");
        stockService.updateMovingAverage(stockContext, 5, dataStock);

        Assert.assertTrue(stockContext.movingAverage.isEmpty());
    }
    /*
    @Test
    public void testGetOperationResult_MMM_Cross20_5_trailingStop() {
        StockService stockService = new StockService();

        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new TrailingStopSellStrategy(8, 15, 8));
        List<OperationResult> operationResults = stockService.getOperationResults(strategy);

        Assert.assertEquals(7, operationResults.size());
        Assert.assertEquals("MMM", operationResults.get(0).ticker);
        Assert.assertEquals("10/04/00", operationResults.get(0).buyDate);
        Assert.assertEquals("10/04/01", operationResults.get(1).buyDate);
        Assert.assertEquals("10/29/08", operationResults.get(2).buyDate);
        Assert.assertEquals("12/01/08", operationResults.get(3).buyDate);
        Assert.assertEquals("12/04/08", operationResults.get(4).buyDate);
        Assert.assertEquals("01/05/09", operationResults.get(5).buyDate);
        Assert.assertEquals("03/16/09", operationResults.get(6).buyDate);
        Assert.assertEquals("2.344163596341758", operationResults.get(0).movement.toString());
        Assert.assertEquals("19.409579275027625", operationResults.get(1).movement.toString());
        Assert.assertEquals("-9.380833851897947", operationResults.get(2).movement.toString());
        Assert.assertEquals("-8.144726712856055", operationResults.get(3).movement.toString());
        Assert.assertEquals("-9.745551305504733", operationResults.get(4).movement.toString());
        Assert.assertEquals("-8.74298350059533", operationResults.get(5).movement.toString());
        Assert.assertEquals("257.36386138613864", operationResults.get(6).movement.toString());

    }*/

    @Test
    public void testGetOperationResult_GeneralElectric_Cross20_5_trailingStop() throws ParseException {
        StockService stockService = new StockService();

        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new TrailingStopSellStrategy(8, 15, 8));
        List<OperationResult> operationResults = stockService.getOperationResults(strategy);

        Assert.assertEquals(25, operationResults.size());
        Assert.assertEquals("GE", operationResults.get(0).ticker);
        Assert.assertEquals("10/04/00", operationResults.get(0).buyDate);
        Assert.assertEquals("-8.704855840844488", operationResults.get(0).movement.toString());
        Assert.assertEquals("11/08/00", operationResults.get(1).buyDate);
        Assert.assertEquals("-8.770073954429208", operationResults.get(1).movement.toString());
        Assert.assertEquals("12/07/00", operationResults.get(2).buyDate);
        Assert.assertEquals("-8.588213408007206", operationResults.get(2).movement.toString());
        Assert.assertEquals("01/22/01", operationResults.get(3).buyDate);
        Assert.assertEquals("-8.26677201073268", operationResults.get(3).movement.toString());
        Assert.assertEquals("04/05/01", operationResults.get(4).buyDate);
        Assert.assertEquals("-1.0976547335408782", operationResults.get(4).movement.toString());
        Assert.assertEquals("12/24/18", operationResults.get(24).buyDate);
        Assert.assertEquals("51.18472238594836", operationResults.get(24).movement.toString());


    }

    @Test
    public void testGetOperationResult_GeneralElectric_Cross20_5_trailingStop_AndBuy() throws ParseException {
        StockService stockService = new StockService();
        AndBuyStrategy buyStrategy = new AndBuyStrategy(new CrossMovingAverageBuyStrategy(20, 5), null);
        Strategy strategy = new Strategy(buyStrategy, new TrailingStopSellStrategy(8, 15, 8));
        List<OperationResult> operationResults = stockService.getOperationResults(strategy);

        Assert.assertEquals(25, operationResults.size());
        Assert.assertEquals("GE", operationResults.get(0).ticker);
        Assert.assertEquals("10/04/00", operationResults.get(0).buyDate);
        Assert.assertEquals("-8.704855840844488", operationResults.get(0).movement.toString());
        Assert.assertEquals("11/08/00", operationResults.get(1).buyDate);
        Assert.assertEquals("-8.770073954429208", operationResults.get(1).movement.toString());
        Assert.assertEquals("12/07/00", operationResults.get(2).buyDate);
        Assert.assertEquals("-8.588213408007206", operationResults.get(2).movement.toString());
        Assert.assertEquals("01/22/01", operationResults.get(3).buyDate);
        Assert.assertEquals("-8.26677201073268", operationResults.get(3).movement.toString());
        Assert.assertEquals("04/05/01", operationResults.get(4).buyDate);
        Assert.assertEquals("-1.0976547335408782", operationResults.get(4).movement.toString());
        Assert.assertEquals("12/24/18", operationResults.get(24).buyDate);
        Assert.assertEquals("51.18472238594836", operationResults.get(24).movement.toString());



    }


}
