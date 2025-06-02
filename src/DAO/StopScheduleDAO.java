package DAO;

import Model.StopSchedule;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import Database.Utils;
import java.sql.Time;

public class StopScheduleDAO implements DAOInterface<StopSchedule> {

    public static StopScheduleDAO instance;

    public static StopScheduleDAO getInstance() {
        if (instance == null) {
            instance = new StopScheduleDAO();
        }
        return instance;
    }

    @Override
    public int insert(StopSchedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO LICHDUNG (MaLT, MaGT, GioDen, GioDi, ThuTu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getScheduleId());
            pst.setInt(2, s.getStationId());
            pst.setTime(3, Time.valueOf(s.getArrivalTime()));
            pst.setTime(4, Time.valueOf(s.getDepartureTime()));
            pst.setInt(5, s.getOrderNumber());
            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(StopSchedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE LICHDUNG SET GioDen=?, GioDi=? WHERE MaLT=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTime(1, Time.valueOf(s.getArrivalTime()));
            pst.setTime(2, Time.valueOf(s.getDepartureTime()));
            pst.setInt(3, s.getScheduleId());
            pst.setInt(4, s.getStationId());
            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(StopSchedule s) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM LICHDUNG WHERE MaLT=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getScheduleId());
            pst.setInt(2, s.getStationId());
            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<StopSchedule> selectAll() {
        ArrayList<StopSchedule> result = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LICHDUNG";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                StopSchedule s = new StopSchedule(
                    rs.getInt("MaLT"),
                    rs.getInt("MaGT"),
                    rs.getTime("GioDen").toLocalTime(),
                    rs.getTime("GioDi").toLocalTime(),
                    rs.getInt("ThuTu")
                );
                result.add(s);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public StopSchedule selectbyId(int id, String m) {
        StopSchedule s = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LICHDUNG WHERE MaLT=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setInt(2, Integer.parseInt(m));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                s = new StopSchedule(
                    rs.getInt("MaLT"),
                    rs.getInt("MaGT"),
                    rs.getTime("GioDen").toLocalTime(),
                    rs.getTime("GioDi").toLocalTime(),
                    rs.getInt("ThuTu")
                );
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}