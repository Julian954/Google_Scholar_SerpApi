package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    //datos de la base de datos
    String db ="revista";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    
    public Connection Conexion(){
        try {
            Class.forName(driver);
            cx=DriverManager.getConnection(url+db,user,password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error al conectar con la base de datos");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }
    
    public void Desconectar(){
        try{
            cx.close();
        }catch(SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        Conexion conexion = new Conexion();
        conexion.Conexion();
    }
    
    
}
