package api.controller;

import api.model.DataStock;
import api.services.StockService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DefaultController {

    private static Gson gson = new Gson();
    private static StockService stockService = new StockService();

    public static Object getData(Request request, Response response) throws IOException, ParseException {

        List<DataStock> stocks = stockService.getStocks();
        return gson.toJson(stocks);
    }

}
