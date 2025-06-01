package DAO;

import Model.Schedule;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import Database.Utils;

public class ScheduleDAO implements DAOInterface<Schedule> {
    public static ScheduleDAO instance;

    public static ScheduleDAO getInstance() {
        if (instance == null) {
            instance = new ScheduleDAO();
        }
        return instance;
    }

    @Override
    public int insert(Schedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO LICHTRINH (MaLT, NgayKH, GioKH, TinhTrang, MaTau, MaNV, SoLuongVeDB) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, s.getScheduleID());
            pst.setDate(2, Date.valueOf(s.getDepartureDate()));
            pst.setTime(3, Time.valueOf(s.getDepartureTime()));
            pst.setString(4, s.getStatus());
            pst.setString(5, s.getTrainID());
            pst.setString(6, s.getEmployeeID());
            pst.setInt(7, s.getReservedTicketCount());

            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Schedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE LICHTRINH SET NgayKH=?, GioKH=?, TinhTrang=?, MaTau=?, MaNV=?, SoLuongVeDB=? WHERE MaLT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(s.getDepartureDate()));
            pst.setTime(2, Time.valueOf(s.getDepartureTime()));
            pst.setString(3, s.getStatus());
            pst.setString(4, s.getTrainID());
            pst.setString(5, s.getEmployeeID());
            pst.setInt(6, s.getReservedTicketCount());
            pst.setString(7, s.getScheduleID());

            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Schedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM LICHTRINH WHERE MaLT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, s.getScheduleID());
            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Schedule selectbyId(int id, String m) {
        Schedule schedule = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LICHTRINH WHERE MaLT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                schedule = new Schedule(
                    rs.getString("MaLT"),
                    rs.getDate("NgayKH").toLocalDate(),
                    rs.getTime("GioKH").toLocalTime(),
                    rs.getString("TinhTrang"),
                    rs.getString("MaTau"),
                    rs.getString("MaNV"),
                    rs.getInt("SoLuongVeDB")
                );
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedule;
    }

    @Override
    public ArrayList<Schedule> selectAll() {
        ArrayList<Schedule> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LICHTRINH";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule(
                    rs.getString("MaLT"),
                    rs.getDate("NgayKH").toLocalDate(),
                    rs.getTime("GioKH").toLocalTime(),
                    rs.getString("TinhTrang"),
                    rs.getString("MaTau"),
                    rs.getString("MaNV"),
                    rs.getInt("SoLuongVeDB")
                );
                list.add(schedule);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
} 
