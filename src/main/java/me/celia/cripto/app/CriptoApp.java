package me.celia.cripto.app;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

/**
 *
 * @author Celia Macas
 */
public class CriptoApp {

    public final static Logger logger = LoggerFactory.getLogger(CriptoApp.class);

    public static void main(String[] args) {

        Spark.initExceptionHandler(e -> {
            logger.error("[INIT ERROR]", e);
            System.exit(1);
        });

        Spark.port(8000);
        Spark.init();
        // bloque de codigo, extraido de internet para dar solucción a los cors.
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            
            return "OK";
        });
        
        Spark.get("/hotel/:lugar", (request, response) -> {
            HotelServicios hotelServicios = new HotelServicios();

            String lugar = request.params("lugar");

            var hoteles = hotelServicios.consultarHotelesPorLugar(lugar);

            return new Gson().toJson(hoteles);
        });
        
        // 
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });
    }
}
