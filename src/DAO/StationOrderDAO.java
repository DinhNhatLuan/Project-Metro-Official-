package DAO;

import Model.StationOrder;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StationOrderDAO implements DAOInterface<StationOrder> {
    public static StationOrderDAO instance;
    public static StationOrderDAO getInstance() {
        if (instance == null) {
            instance = new StationOrderDAO();
        }
        return instance;
    }
    @Override
    public int insert(StationOrder s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO THUTUGA (MaGaDi, MaGaDen, GiaTinhThem) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getDepartureStationId());
            pst.setInt(2, s.getArrivalStationId());
            pst.setInt(3, s.getExtraPrice());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int update(StationOrder s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE THUTUGA SET MaGaDi=?, MaGaDen=?, GiaTinhThem=? WHERE MaTT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getDepartureStationId());
            pst.setInt(2, s.getArrivalStationId());
            pst.setInt(3, s.getExtraPrice());
            pst.setInt(4, s.getStationOrderId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public int delete(StationOrder s) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM THUTUGA WHERE MaTT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, s.getStationOrderId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }
    @Override
    public StationOrder selectbyId(int id, String m) {
        StationOrder s = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM THUTUGA WHERE MaTT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                s = new StationOrder(
                    rs.getInt("MaTT"),
                    rs.getInt("MaGaDi"),
                    rs.getInt("MaGaDen"),
                    rs.getInt("GiaTinhThem")
                );
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    @Override
    public ArrayList<StationOrder> selectAll() {
        ArrayList<StationOrder> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM THUTUGA";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                StationOrder s = new StationOrder(
                    rs.getInt("MaTT"),
                    rs.getInt("MaGaDi"),
                    rs.getInt("MaGaDen"),
                    rs.getInt("GiaTinhThem")
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
