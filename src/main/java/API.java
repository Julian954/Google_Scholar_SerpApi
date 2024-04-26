import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String apiKey = "8d042808b84c320303f9f065804a23d4b989f593ffe7a86d752162576ee12a7d";
        String College = "Universidad de Colima";
        College = College.replace(" ", "+");
        // Construir la URL de la solicitud
        String urlString = "https://serpapi.com/search.json?engine=google_scholar_profiles&mauthors=" + College + "&hl=es&api_key=" + apiKey;

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

                System.out.println("Top 10 Investigadores: ");
                for (int i = 0; i < Math.min(10, profiles.length()); i++) {
                    JSONObject profile = profiles.getJSONObject(i);
                    //System.out.println(profile.toString(4));
                    String authors = profile.getString("author_id");
                    // Cerrar la conexión
                    connection.disconnect();

                    API api = new API();
                    api.author_data(authors, apiKey, i);
                    
                }
            } else {
                System.out.println("No se encontraron perfiles.");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void author_data(String authors,String apiKey,int num){
        // Abrir una nueva conexión HTTP
        try {
            // Construir la URL de la solicitud
            String urlString = "https://serpapi.com/search.json?engine=google_scholar_author&author_id=" + authors + "&sort=pubdate&api_key=" + apiKey;

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

            // Verificar si la respuesta contiene la clave "author"
            if (jsonResponse.has("author") && jsonResponse.get("author") instanceof JSONObject) {
                JSONObject authorObject = jsonResponse.getJSONObject("author");

                String author_name = authorObject.getString("name");
                String author_affiliations = authorObject.getString("affiliations");

                //wait for the moment to send this data into a database and show it in the interface
                System.out.println("Data del autor " + num + ":");
                System.out.println("Nombre: " + author_name);
                System.out.println("Email: " + authors);
                System.out.println("Afiliaciones: " + author_affiliations);
                System.out.println("-------------------------------------------------------------------");
            } else {
                System.out.println("No se encontraron datos del autor.");
            }

            // Cerrar la conexión
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
