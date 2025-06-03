package DAO;
import Model.Ticket;
import java.util.ArrayList;
import java.sql.Connection;
import Database.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class TicketDAO implements DAOInterface<Ticket> {
    public static TicketDAO instance;
    public static TicketDAO getInstance() {
        if (instance == null) {
            instance = new TicketDAO();
        }
        return instance;
    }

    @Override
    public int insert(Ticket t) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "INSERT INTO VE (Gia, MaLoaiVe, ThoiDiemMua, MaLT) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getPrice());
            pst.setInt(2, t.getTicketTypeId());
            pst.setTimestamp(3, Timestamp.valueOf(t.getPurchaseTime()));
            pst.setInt(4, t.getScheduleId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int update(Ticket t) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "UPDATE VE SET Gia=?, MaLoaiVe=?, ThoiDiemMua=?, MaLT=? WHERE MaVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getPrice());
            pst.setInt(2, t.getTicketTypeId());
            pst.setTimestamp(3, Timestamp.valueOf(t.getPurchaseTime()));
            pst.setInt(4, t.getScheduleId());
            pst.setInt(5, t.getTicketId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public int delete(Ticket t) {
        int kq = 0;
        try {
            Connection con = Utils.Connectdb();
            String sql = "DELETE FROM VE WHERE MaVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getTicketId());
            kq = pst.executeUpdate();
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kq;
    }

    @Override
    public Ticket selectbyId(int id, String m) {
        Ticket t = null;
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM VE WHERE MaVe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                t = new Ticket(
                    rs.getInt("MaVe"),
                    rs.getInt("Gia"),
                    rs.getInt("MaLoaiVe"),
                    rs.getTimestamp("ThoiDiemMua").toLocalDateTime(),
                    rs.getInt("MaLT")
                );
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public ArrayList<Ticket> selectAll() {
        ArrayList<Ticket> list = new ArrayList<>();
        try {
            Connection con = Utils.Connectdb();
            String sql = "SELECT * FROM VE";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(
                    rs.getInt("MaVe"),
                    rs.getInt("Gia"),
                    rs.getInt("MaLoaiVe"),
                    rs.getTimestamp("ThoiDiemMua").toLocalDateTime(),
                    rs.getInt("MaLT")
                );
                list.add(t);
            }
            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}