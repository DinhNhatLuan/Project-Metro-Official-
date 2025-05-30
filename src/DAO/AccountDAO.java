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
    public static AccountDAO instance;
    public static AccountDAO getInstance()
    {
        if(instance==null)
        {
            instance=new AccountDAO();
        }
        return instance;
    }
    @Override
    public int insert(Account m)
    {
        int kq=0;
        try
        {
            Connection con=Utils.Connectdb();
            String sql="Insert into TAIKHOAN (TenTK,MatKhau,MaNV) values (?,?,?)";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getUsername());
            pst.setString(2,m.getPassword());
            pst.setInt(3,m.getEmpID());
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
            String sql="Update TAIKHOAN set MatKhau=?, EmpID=? where TenTK=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getPassword());
            pst.setString(2,m.getUsername());
            pst.setInt(3,m.getEmpID());
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
            String sql="Delete from TAIKHOAN where TenTK=?";
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
    public Account selectbyId(int id, String m){
        Account kq=null;
        try
        {
            Connection con=Utils.Connectdb();
            String sql="select * from TAIKHOAN where TenTK=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String UserName = rs.getString("TenTK");
                String PassWord = rs.getString("MatKhau");
                int EmpID = rs.getInt("MaNV");
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
            String sql = "SELECT * FROM TAIKHOAN";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String UserName = rs.getString("TenTK");
                String PassWord = rs.getString("MatKhau");
                int EmpID = rs.getInt("MaNV");
                Account acc = new Account(UserName, PassWord, EmpID);
                kq.add(acc);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
}
