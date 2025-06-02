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
            String sql = "INSERT INTO LICHTRINH (NgayKH, GioKH, TinhTrang, MaTau, MaNVLT, MaNVLL, SoLuongVeDB, MaTuyen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            pst.setDate(1, Date.valueOf(s.getDepartureDate()));
            pst.setTime(2, Time.valueOf(s.getDepartureTime()));
            pst.setString(3, s.getStatus());
            pst.setInt(4, s.getTrainID());
            pst.setInt(5, s.getDriverID());
            pst.setInt(6, s.getScMakerID());
            pst.setInt(7, s.getReservedTicketCount());
            pst.setInt(8, s.getRouteId());

            int mm = pst.executeUpdate();
        if (mm > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }
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
            String sql = "UPDATE LICHTRINH SET NgayKH=?, GioKH=?, TinhTrang=?, MaTau=?, MaNVLT=?, MaNVLL=?, SoLuongVeDB=?, MaTuyen=? WHERE MaLT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(s.getDepartureDate()));
            pst.setTime(2, Time.valueOf(s.getDepartureTime()));
            pst.setString(3, s.getStatus());
            pst.setInt(4, s.getTrainID());
            pst.setInt(5, s.getDriverID());
            pst.setInt(6, s.getScMakerID());
            pst.setInt(7, s.getReservedTicketCount());
            pst.setInt(8, s.getRouteId());
            pst.setInt(9, s.getScheduleID());

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
            pst.setInt(1, s.getScheduleID());
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
                    rs.getInt("MaLT"),
                    rs.getDate("NgayKH").toLocalDate(),
                    rs.getTime("GioKH").toLocalTime(),
                    rs.getString("TinhTrang"),
                    rs.getInt("MaTau"),
                    rs.getInt("MaNVLT"),
                    rs.getInt("MaNVLL"),
                    rs.getInt("SoLuongVeDB"),
                    rs.getInt("MaTuyen")
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
                    rs.getInt("MaLT"),
                    rs.getDate("NgayKH").toLocalDate(),
                    rs.getTime("GioKH").toLocalTime(),
                    rs.getString("TinhTrang"),
                    rs.getInt("MaTau"),
                    rs.getInt("MaNVLT"),
                    rs.getInt("MaNVLL"),
                    rs.getInt("SoLuongVeDB"),
                    rs.getInt("MaTuyen")
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