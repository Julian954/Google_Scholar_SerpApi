/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Usuario
 */
public class API {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String apiKey = "8d042808b84c320303f9f065804a23d4b989f593ffe7a86d752162576ee12a7d";
        String College = "Universidad de Colima";
        College = College.replace(" ", "+");
            // Construir la URL de la solicitud
        String urlString = "https://serpapi.com/search.json?engine=google_scholar_profiles&mauthors="+College+"&hl=es&api_key="+apiKey;
        // Solicitar al usuario que ingrese la consulta de búsqueda
        System.out.println("URL de la solicitud: " + urlString);

        try {
            // Crear una conexión HTTP
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Analizar la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            if (jsonResponse.has("profiles") && jsonResponse.get("profiles") instanceof JSONArray) {
                JSONArray profiles = jsonResponse.getJSONArray("profiles");

                // Imprimir el array "profiles" de manera formateada
                System.out.println("Profiles:");
                System.out.println(profiles.toString(4)); // El valor 4 indica la cantidad de espacios de indentación
            } else {
                System.out.println("No se encontraron perfiles.");
            }
            
            // Imprimir la respuesta JSON de manera formateada
            //System.out.println("Respuesta de la API:");
            //System.out.println(profiles.toString(4)); // El valor 4 indica la cantidad de espacios de indentación

            // Cerrar la conexión
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}