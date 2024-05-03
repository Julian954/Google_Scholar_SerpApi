package model;

import Mysql.Conexion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                for (int i = 0; i < Math.min(10, profiles.length()); i++) {
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
                
                //database insertion
                
                Connection cx = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try{
                    Conexion conexion = new Conexion();
                    cx = conexion.Conexion();

                    // Primero, verifica si el autor ya existe en la base de datos
                    String query = "SELECT * FROM autor WHERE Author_Id = ?";
                    pstmt = cx.prepareStatement(query);
                    pstmt.setString(1, Auth_Id);
                    rs = pstmt.executeQuery();

                    // Si el autor ya existe, no es necesario agregarlo nuevamente
                    if (rs.next()) {
                        System.out.println("El autor ya existe en la base de datos.");
                    } else {
                        // Si el autor no existe, procede a insertarlo
                        String sql = "INSERT INTO autor (Author_Id, Author_Name, Author_Afila, Author_Link) VALUES (?, ?, ?, ?)";
                        pstmt = cx.prepareStatement(sql);
                        pstmt.setString(1, Auth_Id);
                        pstmt.setString(2, author_name);
                        pstmt.setString(3, author_affiliations);
                        pstmt.setString(4, "https://scholar.google.com/citations?&user=" + Auth_Id);
                        pstmt.executeUpdate();
                        System.out.println("Autor agregado correctamente.");
                    }
                } catch (SQLException ex) {
                Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    if (pstmt != null){
                        try{
                            pstmt.close();
                        }catch (SQLException ex) {
                            Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (cx != null){
                        try {
                            cx.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            } else {
                System.out.println("No se encontraron datos del autor.");
            }

            // Verificar si la respuesta contiene la clave "articles"
            if (jsonResponse.has("articles") && jsonResponse.get("articles") instanceof JSONArray) {
                JSONArray articlesArray = jsonResponse.getJSONArray("articles");

                System.out.println("Ultimos 3 articulos: ");
                for (int j = 0; j < Math.min(5, articlesArray.length()); j++) {
                    JSONObject article = articlesArray.getJSONObject(j);
                    int No = j;
                    No++;
                    //System.out.println(profile.toString(4));
                    String articles_title = article.getString("title");
                    String articles_link = article.getString("link"); 
                    String articles_authors = article.getString("authors");
                    String articles_year = article.getString("year");
                    
                    
                    //wait for the moment to send this data into a database and show it in the interface
                    System.out.println("Articulo No: " + No); //useless
                    System.out.println("Titulo: " + articles_title); //use
                    System.out.println("Link: " + articles_link);//use
                    System.out.println("Autores: " + articles_authors);//
                    System.out.println("Year: " + articles_year);//
                    System.out.println("                                                                      ");
                    
                    //database insertion
                    
                    Connection cx = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try{
                        Conexion conexion = new Conexion();
                        cx = conexion.Conexion();

                        // Primero, verifica si el autor ya existe en la base de datos
                        String query = "SELECT * FROM articulos WHERE Article_Link = ? AND Article_AutorId = ?";
                        pstmt = cx.prepareStatement(query);
                        pstmt.setString(1, articles_link);
                        pstmt.setString(2, Auth_Id);
                        rs = pstmt.executeQuery();
                        
                        if(rs.next()){
                        }else{
                            String sql = "INSERT INTO articulos (Article_Title, Article_Autors, Article_Link, Article_AutorId, Article_Year) VALUES (?, ?, ?, ?, ?)";
                            pstmt = cx.prepareStatement(sql);
                            pstmt.setString(1, articles_title);
                            pstmt.setString(2, articles_authors);
                            pstmt.setString(3, articles_link);
                            pstmt.setString(4, Auth_Id);
                            pstmt.setString(5, articles_year);
                            pstmt.executeUpdate();
                        }

                    } catch (SQLException ex) {
                    Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                    } finally{
                        if (pstmt != null){
                            try{
                                pstmt.close();
                            }catch (SQLException ex) {
                                Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (cx != null){
                            try {
                                cx.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(Cliente_Model.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return message;
    }
}