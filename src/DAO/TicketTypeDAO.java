package DAO;

import Model.TicketType;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TicketTypeDAO implements DAOInterface<TicketType> {
    private static TicketTypeDAO instance;

    public static TicketTypeDAO getInstance() {
        if (instance == null) {
            instance = new TicketTypeDAO();
        }
        return instance;
    }

    @Override
    public int insert(TicketType t) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO LOAIVE (MaLoaiVe, MoTa, GiaGoc) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTicketTypeID());
            pst.setString(2, t.getDescription());
            pst.setInt(3, t.getBasePrice());

            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(TicketType t) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE LOAIVE SET MoTa=?, GiaGoc=? WHERE MaLoaiVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getDescription());
            pst.setInt(2, t.getBasePrice());
            pst.setString(3, t.getTicketTypeID());

            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(TicketType t) {
        int result = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM LOAIVE WHERE MaLoaiVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getTicketTypeID());

            result = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public TicketType selectbyId(int id, String m) {
        TicketType tt = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LOAIVE WHERE MaLoaiVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String description = rs.getString("MoTa");
                int basePrice = rs.getInt("GiaGoc");
                tt = new TicketType(id, description, basePrice);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tt;
    }

    @Override
    public ArrayList<TicketType> selectAll() {
        ArrayList<TicketType> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM LOAIVE";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String ticketTypeId = rs.getString("MaLoaiVe");
                String description = rs.getString("MoTa");
                int basePrice = rs.getInt("GiaGoc");

                list.add(new TicketType(ticketTypeId, description, basePrice));
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
