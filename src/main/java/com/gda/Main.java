package com.gda;

import com.gda.config.Config;
import com.gda.router.Router;
import spark.Spark;


public class Main {
    public static void main (String[] args){
        Spark.port(Config.getSparkPort());
        new Router().init();
        System.out.println("Listening on http://localhost:" + Config.getSparkPort());
    }
}
