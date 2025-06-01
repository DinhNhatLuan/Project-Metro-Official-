package DAO;

// import Model.Train; // Commented out because the class cannot be resolved
// Please ensure the Train class exists and update the import accordingly, for example:
// import your.actual.package.path.Train;
// import Model.Train; // Update this import to the correct package path for Train
import Model.Train;
import java.sql.*;
import java.util.ArrayList;
import Database.Utils;

public class TrainDAO implements DAOInterface<Train> {
    public static TrainDAO instance;

    public static TrainDAO getInstance() {
        if (instance == null) {
            instance = new TrainDAO();
        }
        return instance;
    }

    @Override
    public int insert(Train train) {
        int result = 0;
        try (Connection con = Utils.Connectdb()) {
            String sql = "INSERT INTO TAU (MaTau, TenTau, SucChua) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, train.getTrainID());
            pst.setString(2, train.getTrainName());
            pst.setInt(3, train.getCapacity());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Train train) {
        int result = 0;
        try (Connection con = Utils.Connectdb()) {
            String sql = "UPDATE TAU SET TenTau = ?, SucChua = ? WHERE MaTau = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, train.getTrainName());
            pst.setInt(2, train.getCapacity());
            pst.setInt(3, train.getTrainID());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Train train) {
        int result = 0;
        try (Connection con = Utils.Connectdb()) {
            String sql = "DELETE FROM TAU WHERE MaTau = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, train.getTrainID());
            result = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Train selectbyId(int id, String m) {
        Train train = null;
        try (Connection con = Utils.Connectdb()) {
            String sql = "SELECT * FROM TAU WHERE MaTau = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int trainID = rs.getInt("MaTau");
                String trainName = rs.getString("TenTau");
                int capacity = rs.getInt("SucChua");
                train = new Train(trainID, trainName, capacity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return train;
    }

    @Override
    public ArrayList<Train> selectAll() {
        ArrayList<Train> list = new ArrayList<>();
        try (Connection con = Utils.Connectdb()) {
            String sql = "SELECT * FROM TAU";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int trainID = rs.getInt("MaTau");
                String trainName = rs.getString("TenTau");
                int capacity = rs.getInt("SucChua");
                list.add(new Train(trainID, trainName, capacity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
