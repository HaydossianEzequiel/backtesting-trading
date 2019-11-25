package api.controller;

import api.model.DataStock;
import api.model.Metric;
import api.model.OperationResult;
import api.model.strategies.CrossMovingAverageBuyStrategy;
import api.model.strategies.MovingAverageBuyStrategy;
import api.model.strategies.Strategy;
import api.model.strategies.TrailingStopSellStrategy;
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

    public static Object getFrontend(Request request, Response response) throws IOException, ParseException {

        String html = getHtml();
        return html;
    }

    private static String getHtml() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<title>Page Title</title>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<style>\n" +
                "* {\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "/* Style the body */\n" +
                "body {\n" +
                "  font-family: Arial, Helvetica, sans-serif;\n" +
                "  margin: 0;\n" +
                "}\n" +
                "\n" +
                "/* Header/logo Title */\n" +
                ".header {\n" +
                "  padding: 80px;\n" +
                "  text-align: center;\n" +
                "  background: #1abc9c;\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                "/* Increase the font size of the heading */\n" +
                ".header h1 {\n" +
                "  font-size: 40px;\n" +
                "}\n" +
                "\n" +
                "/* Sticky navbar - toggles between relative and fixed, depending on the scroll position. It is positioned relative until a given offset position is met in the viewport - then it \"sticks\" in place (like position:fixed). The sticky value is not supported in IE or Edge 15 and earlier versions. However, for these versions the navbar will inherit default position */\n" +
                ".navbar {\n" +
                "  overflow: hidden;\n" +
                "  background-color: #333;\n" +
                "  position: sticky;\n" +
                "  position: -webkit-sticky;\n" +
                "  top: 0;\n" +
                "}\n" +
                "\n" +
                "/* Style the navigation bar links */\n" +
                ".navbar a {\n" +
                "  float: left;\n" +
                "  display: block;\n" +
                "  color: white;\n" +
                "  text-align: center;\n" +
                "  padding: 14px 20px;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "/* Right-aligned link */\n" +
                ".navbar a.right {\n" +
                "  float: right;\n" +
                "}\n" +
                "\n" +
                "/* Change color on hover */\n" +
                ".navbar a:hover {\n" +
                "  background-color: #ddd;\n" +
                "  color: black;\n" +
                "}\n" +
                "\n" +
                "/* Active/current link */\n" +
                ".navbar a.active {\n" +
                "  background-color: #666;\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                "/* Column container */\n" +
                ".row {  \n" +
                "  display: -ms-flexbox; /* IE10 */\n" +
                "  display: flex;\n" +
                "  -ms-flex-wrap: wrap; /* IE10 */\n" +
                "  flex-wrap: wrap;\n" +
                "}\n" +
                "\n" +
                "/* Create two unequal columns that sits next to each other */\n" +
                "/* Sidebar/left column */\n" +
                ".side {\n" +
                "  -ms-flex: 30%; /* IE10 */\n" +
                "  flex: 30%;\n" +
                "  background-color: #f1f1f1;\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "\n" +
                "/* Main column */\n" +
                ".main {   \n" +
                "  -ms-flex: 70%; /* IE10 */\n" +
                "  flex: 70%;\n" +
                "  background-color: white;\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "\n" +
                "/* Fake image, just for this example */\n" +
                ".fakeimg {\n" +
                "  background-color: #aaa;\n" +
                "  width: 100%;\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "\n" +
                "/* Footer */\n" +
                ".footer {\n" +
                "  padding: 20px;\n" +
                "  text-align: center;\n" +
                "  background: #ddd;\n" +
                "}\n" +
                "\n" +
                "/* Responsive layout - when the screen is less than 700px wide, make the two columns stack on top of each other instead of next to each other */\n" +
                "@media screen and (max-width: 700px) {\n" +
                "  .row {   \n" +
                "    flex-direction: column;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "/* Responsive layout - when the screen is less than 400px wide, make the navigation links stack on top of each other instead of next to each other */\n" +
                "@media screen and (max-width: 400px) {\n" +
                "  .navbar a {\n" +
                "    float: none;\n" +
                "    width: 100%;\n" +
                "  }\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"header\">\n" +
                "  <h1>My Website</h1>\n" +
                "  <p>A <b>responsive</b> website created by me.</p>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"navbar\">\n" +
                "  <a href=\"#\" class=\"active\">Home</a>\n" +
                "  <a href=\"#\">Link</a>\n" +
                "  <a href=\"#\">Link</a>\n" +
                "  <a href=\"#\" class=\"right\">Link</a>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"row\">\n" +
                "  <div class=\"side\">\n" +
                "    <h2>About Me</h2>\n" +
                "    <h5>Photo of me:</h5>\n" +
                "    <div class=\"fakeimg\" style=\"height:200px;\">Image</div>\n" +
                "    <p>Some text about me in culpa qui officia deserunt mollit anim..</p>\n" +
                "    <h3>More Text</h3>\n" +
                "    <p>Lorem ipsum dolor sit ame.</p>\n" +
                "    <div class=\"fakeimg\" style=\"height:60px;\">Image</div><br>\n" +
                "    <div class=\"fakeimg\" style=\"height:60px;\">Image</div><br>\n" +
                "    <div class=\"fakeimg\" style=\"height:60px;\">Image</div>\n" +
                "  </div>\n" +
                "  <div class=\"main\">\n" +
                "    <h2>TITLE HEADING</h2>\n" +
                "    <h5>Title description, Dec 7, 2017</h5>\n" +
                "    <div class=\"fakeimg\" style=\"height:200px;\">Image</div>\n" +
                "    <p>Some text..</p>\n" +
                "    <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>\n" +
                "    <br>\n" +
                "    <h2>TITLE HEADING</h2>\n" +
                "    <h5>Title description, Sep 2, 2017</h5>\n" +
                "    <div class=\"fakeimg\" style=\"height:200px;\">Image</div>\n" +
                "    <p>Some text..</p>\n" +
                "    <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>\n" +
                "  </div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"footer\">\n" +
                "  <h2>Footer</h2>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }

    public static Object getData(Request request, Response response) throws IOException, ParseException {

        List<DataStock> stocks = stockService.getStocks();
        return gson.toJson(stocks);
    }

    public static Object getOperationResults(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(slow, fast), new TrailingStopSellStrategy(8, 15, 8));
        //Strategy strategy = new Strategy(new MovingAverageBuyStrategy(slow), new TrailingStopSellStrategy(8, 15, 10));

        List<OperationResult> stocks = stockService.getOperationResults(strategy);
        return gson.toJson(stocks);
    }

    public static Object getMetrics(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(slow, fast), new TrailingStopSellStrategy(8, 15, 8));
        //Strategy strategy = new Strategy(new MovingAverageBuyStrategy(slow), new TrailingStopSellStrategy(8, 15, 10));
        Metric metric = stockService.getMetrics(strategy);
        return gson.toJson(metric);
    }

    public static Object getMovingAverageMetrics(Request request, Response response) throws IOException, ParseException {

        List<Metric> metrics = stockService.getBestMovingAverageMetrics();
        return gson.toJson(metrics);
    }

}
