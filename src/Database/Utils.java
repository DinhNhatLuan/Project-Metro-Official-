/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author capta
 */
public class Utils {
    public static Connection Connectdb()
    {
        String driver="com.mysql.cj.jdbc.Driver";
        try{
            Class.forName(driver);
            String url = "jdbc:mysql://localhost:3306/metro";
            String username="metro";
            String password="12345";
            return DriverManager.getConnection(url,username,password);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void Closeconn(Connection c){
        try{
            if(c!=null) c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
