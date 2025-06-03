package DAO;
import Model.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Database.Utils;

public class RouteDAO implements DAOInterface<Route> {

    public static RouteDAO instance;

    public static RouteDAO getInstance() {
        if (instance == null) {
            instance = new RouteDAO();
        }
        return instance;
    }

    @Override
    public int insert(Route r) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO TUYEN (TenTuyen, DoDai, ExtraCost) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, r.getRouteName());
            pst.setInt(2, r.getLength());
            pst.setInt(3, r.getExtraCost());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int update(Route r) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE TUYEN SET TenTuyen=?, DoDai=?, ExtraCost=? WHERE MaTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, r.getRouteName());
            pst.setInt(2, r.getLength());
            pst.setInt(3, r.getExtraCost());
            pst.setInt(4, r.getRouteId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int delete(Route r) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM TUYEN WHERE MaTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, r.getRouteId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public Route selectbyId(int id, String m) {
        Route route = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM TUYEN WHERE MaTuyen=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int routeId = rs.getInt("MaTuyen");
                String routeName = rs.getString("TenTuyen");
                int length = rs.getInt("DoDai");
                int extraCost = rs.getInt("ExtraCost");
                route = new Route(routeId, routeName, length, extraCost);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public ArrayList<Route> selectAll() {
        ArrayList<Route> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM TUYEN";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int routeId = rs.getInt("MaTuyen");
                String routeName = rs.getString("TenTuyen");
                int length = rs.getInt("DoDai");
                int extraCost = rs.getInt("GiaChenhLech");
                list.add(new Route(routeId, routeName, length, extraCost));
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}