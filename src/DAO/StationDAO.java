package DAO;
import Model.Station;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StationDAO implements DAOInterface<Station> {
    public static StationDAO instance;
    public static StationDAO getInstance() {
        if (instance == null) {
            instance = new StationDAO();
        }
        return instance;
    }
    @Override
    public int insert(Station s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO GATAU (TenGT, ViTri) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, s.getName());
            pst.setString(2, s.getLocation());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int update(Station s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE GATAU SET TenGT=?, ViTri=? WHERE MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, s.getName());
            pst.setString(2, s.getLocation());
            pst.setInt(3, s.getStationId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int delete(Station s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM GATAU WHERE MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getStationId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public Station selectbyId(int id, String m) {
        Station s = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM GATAU WHERE MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                s = new Station(
                    rs.getInt("MaGT"),
                    rs.getString("TenGT"),
                    rs.getString("ViTri")
                );
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    @Override
    public ArrayList<Station> selectAll() {
        ArrayList<Station> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM GATAU";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Station s = new Station(
                    rs.getInt("MaGT"),
                    rs.getString("TenGT"),
                    rs.getString("ViTri")
                );
                list.add(s);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
