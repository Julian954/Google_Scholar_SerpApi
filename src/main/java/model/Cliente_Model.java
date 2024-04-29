/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import mysql.Conexion;
/**
 *
 * @author Usuario
 */
public class Cliente_Model {
    private String College;
    private String apiKey;
    private String Auth_Id;

    public String getAuth_Id() {
        return Auth_Id;
    }

    public void setAuth_Id(String Auth_Id) {
        this.Auth_Id = Auth_Id;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String College) {
        this.College = College;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public void Autor_id(){
        String Uni = this.College.replace(" ", "+");
        // Construir la URL de la solicitud
        String urlString = "https://serpapi.com/search.json?engine=google_scholar_profiles&mauthors=" + Uni + "&hl=es&api_key=" + this.apiKey;

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
                for (int i = 0; i < Math.min(3, profiles.length()); i++) {
                    JSONObject profile = profiles.getJSONObject(i);
                    //System.out.println(profile.toString(4));
                    this.Auth_Id = profile.getString("author_id");
                    // Cerrar la conexión
                    Author_Data(this.Auth_Id,this.apiKey);
                    connection.disconnect();
                    
                }
            } else {
                System.out.println("No se encontraron perfiles.");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
    public String Author_Data(String Auth_Id, String apiKey){

        String message = "hello";
        try{
            String urlString = "https://serpapi.com/search.json?engine=google_scholar_author&author_id=" + Auth_Id + "&sort=pubdate&api_key=" + apiKey;
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
                System.out.println(".........................................................");       //use
                System.out.println("Nombre: " + author_name);       //use
                System.out.println("Google Scholar Id: " + Auth_Id);//use
                System.out.println("Afiliaciones: " + author_affiliations);//use
                System.out.println("Profile Link: " + "https://scholar.google.com/citations?&user="+ Auth_Id );//use
                System.out.println("        ");
            } else {
                System.out.println("No se encontraron datos del autor.");
            }

            // Verificar si la respuesta contiene la clave "articles"
            if (jsonResponse.has("articles") && jsonResponse.get("articles") instanceof JSONArray) {
                JSONArray articlesArray = jsonResponse.getJSONArray("articles");

                System.out.println("Ultimos 3 articulos: ");
                for (int j = 0; j < Math.min(2, articlesArray.length()); j++) {
                    JSONObject article = articlesArray.getJSONObject(j);
                    int No = j;
                    No++;
                    //System.out.println(profile.toString(4));
                    String articles_title = article.getString("title");
                    String articles_link = article.getString("link"); 
                    String articles_authors = article.getString("authors");
                    
                    //wait for the moment to send this data into a database and show it in the interface
                    System.out.println("Articulo No: " + No); //useless
                    System.out.println("Titulo: " + articles_title); //use
                    System.out.println("Link: " + articles_link);//use
                    System.out.println("Autores: " + articles_authors);//
                    System.out.println("                                                                      ");
                }
            }
        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return message;
    }
}

