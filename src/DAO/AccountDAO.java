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
public class AccountDAO implements DAOInterface<Account>{
    public static AccountDAO accdao;
    public static AccountDAO getInstance()
    {
        if(accdao==null)
        {
            accdao=new AccountDAO();
        }
        return accdao;
    }
    @Override
    public int insert(Account m)
    {
        int kq=0;
        try 
        {
            Connection con=Utils.Connectdb();
            String sql="Insert into TAKHOAN (TenTK,MatKhau,MaNV) values (?,?,?)";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getUsername());
            pst.setString(2,m.getPassword());
            pst.setString(3,m.getEmpID());
            
            kq=pst.executeUpdate();
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int update(Account m)
    {
        int kq=0;
        try
        {
            Connection con=Utils.Connectdb();
            String sql="Update Account set MatKhau=?, MaNV=? where TenTK=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getPassword());
            pst.setString(2,m.getEmpID());
            pst.setString(3,m.getUsername());
            kq=pst.executeUpdate();
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return kq;
    }
    
    public int delete(Account m)
    {
        int kq=0;
        try
        {
            Connection con=Utils.Connectdb();
            String sql="Delete from Account where TenTK=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getUsername());
            kq=pst.executeUpdate();
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return kq;
    }
    public Account selectbyId(String m){
        Account kq=null;
        try
        {
            Connection con=Utils.Connectdb();
            String sql="select * from Account where TenTK=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String UserName = rs.getString("TenTK");
                String PassWord = rs.getString("MatKhau");
                String EmpID = rs.getString("MaNV");
                kq = new Account(UserName,PassWord,EmpID);
            }
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return kq;
    }
    public ArrayList<Account> selectAll(){
        ArrayList<Account> kq = new ArrayList<Account>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM Account";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String UserName = rs.getString("TenTK");
                String PassWord = rs.getString("MatKhau");
                String EmpID = rs.getString("MaNV");

                Account acc = new Account(UserName, PassWord, EmpID);
                kq.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
}
