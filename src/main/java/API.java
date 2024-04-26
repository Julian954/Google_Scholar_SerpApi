/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
/**
 *
 * @author Usuario
 */
public class API {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String apiKey = "8d042808b84c320303f9f065804a23d4b989f593ffe7a86d752162576ee12a7d";
        String College = "Universidad de Colima";
            // Construir la URL de la solicitud
        String urlString = "https://serpapi.com/search.json?engine=google_scholar_profiles&mauthors="+College+"&hl=es&api_key="+apiKey;
        // Solicitar al usuario que ingrese la consulta de búsqueda
        //System.out.print("Ingrese la consulta de búsqueda en Google Scholar: ");
        // Construir la URL de la solicitud GET
        //String urlString = "https://serpapi.com/search?engine=google_scholar&q=" + query + "&author_id=GrBYQocAAAAJ" +"&api_key=" + API_KEY;
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

            // Imprimir la respuesta JSON
            System.out.println("Respuesta de la API:");
            System.out.println(response.toString());

            // Cerrar la conexión
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
