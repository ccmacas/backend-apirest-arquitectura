package me.celia.cripto.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Celia Macas
 */
public class HotelServicios {

    private final String API_ENDPOINT = "https://hotels4.p.rapidapi.com/locations/v3/search?q=";

    public ArrayList<Hotel> consultarHotelesPorLugar(String lugar) {
        String endpoint = API_ENDPOINT + lugar;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("X-RapidAPI-Key", "af992d04b9msh8b7b7d4be9d1a07p19064bjsne88e9636695f")
                .header("X-RapidAPI-Host", "hotels4.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            var json = new Gson().fromJson(response.body(), JsonObject.class);

            ArrayList<Hotel> hoteles = new ArrayList<>();

            var places = json.getAsJsonArray("sr");

            places.forEach(p -> {
                var place = p.getAsJsonObject();
                String type = place.get("type").getAsString();

                if (type.equalsIgnoreCase("HOTEL")) {
                    var names = place.getAsJsonObject("regionNames");
                    String name = names.get("primaryDisplayName").getAsString();
                    var address = place.getAsJsonObject("hotelAddress");
                    String street = address.get("street").getAsString();
                    String city = address.get("city").getAsString();
                    String province = address.get("province").getAsString();

                    hoteles.add(new Hotel(name, street, city, province));
                }
            });

            return hoteles;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(HotelServicios.class.getName()).log(Level.SEVERE, "Error al realizar la peticion", ex);
        }

        return null;
    }
}
