package api.controller;

import api.model.Example;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class DefaultController {

    private static Gson gson = new Gson();

    public static Object getData(Request request, Response response) {
        return gson.toJson(new Example("Hello, World!"));
    }

}
