package DAO;

import Model.Route_Details;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Database.Utils;

public class Route_DetailsDAO implements DAOInterface<Route_Details> {

    public static Route_DetailsDAO instance;

    public static Route_DetailsDAO getInstance() {
        if (instance == null) {
            instance = new Route_DetailsDAO();
        }
        return instance;
    }

    @Override
    public int insert(Route_Details rd) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO CT_TUYEN (MaTuyen, MaGT, ThuTu) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, rd.getRouteId());
            pst.setInt(2, rd.getStationId());
            pst.setInt(3, rd.getStationOrder());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int update(Route_Details rd) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE CT_TUYEN SET ThuTu=? WHERE MaTuyen=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, rd.getStationOrder());
            pst.setInt(2, rd.getRouteId());
            pst.setInt(3, rd.getStationId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int delete(Route_Details rd) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM CT_TUYEN WHERE MaTuyen=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, rd.getRouteId());
            pst.setInt(2, rd.getStationId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public Route_Details selectbyId(int routeId, String stationIdStr) {
        Route_Details rd = null;
        try {
            int stationId = Integer.parseInt(stationIdStr);
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM CT_TUYEN WHERE MaTuyen=? AND MaGT=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, routeId);
            pst.setInt(2, stationId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int maTuyen = rs.getInt("MaTuyen");
                int maGa = rs.getInt("MaGa");
                int ThuTu = rs.getInt("ThuTu");
                rd = new Route_Details(maTuyen, maGa, ThuTu);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rd;
    }

    @Override
    public ArrayList<Route_Details> selectAll() {
        ArrayList<Route_Details> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM CT_TUYEN";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maTuyen = rs.getInt("MaTuyen");
                int maGa = rs.getInt("MaGT");
                int ThuTu = rs.getInt("MaThuTu");
                list.add(new Route_Details(maTuyen, maGa, ThuTu));
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}