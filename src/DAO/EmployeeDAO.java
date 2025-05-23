/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Account;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author capta
 */
public class EmployeeDAO implements DAOInterface<Employee>{
    public static EmployeeDAO empdao;
    public static EmployeeDAO getInstance(){
        if(empdao==null)
        {
            empdao=new EmployeeDAO();
        }
        return empdao;
    }
    
    public int insert(Employee m)
    {
        int kq=0;
        try 
        {
            Connection con=Utils.Connectdb();
            
        }
    }
}
