/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Employee;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author capta
 */
public class EmployeeDAO implements DAOInterface<Employee>{
    public static EmployeeDAO instance;
    public static EmployeeDAO getInstance(){
        if(instance==null)
        {
            instance=new EmployeeDAO();
        }
        return instance;
    }
    
    @Override
    public int insert(Employee m)
    {
        int kq=0;
        try 
        {
            Connection con=Utils.Connectdb();
            String sql="Insert into NHANVIEN (HoTen,ChucVu,DiaChi,GioiTinh,Email,SoDT,Luong,NgayVaoLam,CaLam,MaNVQL,NgaySinh) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getName());
            pst.setString(2, m.getRole());
            pst.setString(3,m.getAddress());
            pst.setBoolean(4, m.getGender());
            pst.setString(5,m.getEmail());
            pst.setString(6, m.getPhoneNum());
            pst.setInt(7,m.getSalary());
            pst.setDate(8,Date.valueOf(m.getSWDay()));
            pst.setString(9,m.getPhase());
            pst.setInt(10,m.getManagerID());
            pst.setDate(11,Date.valueOf(m.getBirthDay()));
            kq=pst.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int update(Employee m)
    {
        int kq=0;
        try 
        {
            Connection con=Utils.Connectdb();
            String sql="Update NHANVIEN set HoTen=?,ChucVu=?,DiaChi=?,GioiTinh=?,Email=?,SoDT=?,Luong=?,NgayVaoLam=?,CaLam=?,MaNVQL=?,NgaySinh=? where MaNV=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1,m.getName());
            pst.setString(2, m.getRole());
            pst.setString(3,m.getAddress());
            pst.setBoolean(4, m.getGender());
            pst.setString(5,m.getEmail());
            pst.setString(6, m.getPhoneNum());
            pst.setInt(7,m.getSalary());
            pst.setDate(8,Date.valueOf(m.getSWDay()));
            pst.setString(9,m.getPhase());
            pst.setInt(10,m.getManagerID());
            pst.setDate(11,Date.valueOf(m.getBirthDay()));
            pst.setInt(12, m.getEmpID());
            kq=pst.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int delete(Employee m)
    {
        int kq=0;
        try 
        {
            Connection con=Utils.Connectdb();
            String sql="Delete from NHANVIEN where MaNV=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, m.getEmpID());
            kq=pst.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public Employee selectbyId(int id, String m)
    {
        Employee emp=null;
        try{
            Connection con=Utils.Connectdb();
            String sql="Select * from NHANVIEN where MaNV=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs=pst.executeQuery();
             while(rs.next())
            {
            int EmpID=rs.getInt("MaNV");
            String Name=rs.getString("HoTen");
            String Role=rs.getString("ChucVu");
            String Address=rs.getString("DiaChi");
            boolean Gender=rs.getBoolean("GioiTinh");
            String Email=rs.getString("Email");
            String PhoneNum=rs.getString("SoDT");
            int Salary=rs.getInt("Luong");
            LocalDate SWDay=rs.getDate("NgayVaoLam").toLocalDate();
            String Phase=rs.getString("CaLam");
            int ManagerID=rs.getInt("MaNVQL");
            LocalDate BirthDay=rs.getDate("NgaySinh").toLocalDate();
            emp=new Employee(EmpID,Name,Role,Address,Email,PhoneNum,Phase,ManagerID,Salary,Gender,SWDay,BirthDay);
            }
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return emp;
    }
    @Override
    public ArrayList<Employee> selectAll()
    {
        ArrayList<Employee> group=new ArrayList<>();
        try{
            Connection con=Utils.Connectdb();
            String sql="Select * from NHANVIEN";
            PreparedStatement pst=con.prepareStatement(sql);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
            int EmpID=rs.getInt("MaNV");
            String Name=rs.getString("HoTen");
            String Role=rs.getString("ChucVu");
            String Address=rs.getString("DiaChi");
            boolean Gender=rs.getBoolean("GioiTinh");
            String Email=rs.getString("Email");
            String PhoneNum=rs.getString("SoDT");
            int Salary=rs.getInt("Luong");
            LocalDate SWDay=rs.getDate("NgayVaoLam").toLocalDate();
            String Phase=rs.getString("CaLam");
            int ManagerID=rs.getInt("MaNVQL");
            LocalDate BirthDay=rs.getDate("NgaySinh").toLocalDate();
            Employee emp=new Employee(EmpID,Name,Role,Address,Email,PhoneNum,Phase,ManagerID,Salary,Gender,SWDay,BirthDay);
            group.add(emp);
            }
            Utils.Closeconn(con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return group;
    }
}
