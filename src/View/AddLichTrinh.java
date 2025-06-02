/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;
import Model.Schedule;
import java.util.Date;

import Model.CurrentUser;
import Model.Route;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import java.time.LocalDate;
import Model.Train;
import java.util.ArrayList;
import DAO.TrainDAO;
import Database.Utils;

import java.awt.GridLayout;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import Model.CurrentUser;
import Model.StopSchedule;

/**
 *
 * @author capta
 */
public class AddLichTrinh extends javax.swing.JFrame {
    private Schedule lichtrinh;
    private ArrayList<Integer> trainid;
    private ArrayList<Integer> LTid;
    int mode;
    /**
     * Creates new form AddLichTrinh
     */
    public AddLichTrinh() {
        initComponents();
        trainid = new ArrayList<>();
        LTid = new ArrayList<>();
        loadTrainCombo();
    }
    public AddLichTrinh(Date ngay, Route rt) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        mode=1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String ngayStr = sdf.format(ngay);
        Title.setText("Lịch trình Tuyến: " + rt.getRouteName() + " / Ngày " + ngayStr);;
        LichDung.setLayout(new GridLayout(0, 1,10,10));
        LichDung.setBorder(BorderFactory.createEmptyBorder(5, 5,5, 5));
         // Thiết lập layout cho LichDung
        lichtrinh = new Schedule();
        lichtrinh.setScheduleID(0); // Giả sử ID sẽ được tự động tăng
        lichtrinh.setDepartureTime(LocalTime.now()); // Giả sử thời gian khởi hành là hiện tại
        lichtrinh.setStatus("Chưa duyệt"); // Trạng thái ban đầu
        lichtrinh.setTrainID(0); // ID tàu sẽ được chọn từ ComboBox
        lichtrinh.setDriverID(0); // ID lái tàu sẽ được chọn từ ComboBox
        lichtrinh.setScMakerID(0); // ID người tạo lịch trình sẽ được xác định sau
        lichtrinh.setReservedTicketCount(0); // Số vé đã đặt ban đầu là 0   
        lichtrinh.setRouteId(rt.getRouteId());
        LocalDate localDate = ngay.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        lichtrinh.setDepartureDate(localDate);
        trainid = new ArrayList<>();
        LTid = new ArrayList<>();
        loadTrainCombo();
        LoadLTCombo();
        addNewCard();
    }
       public AddLichTrinh(Schedule sc, Route rt) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        mode=2;
        trainid = new ArrayList<>();
        LTid = new ArrayList<>();

        // Gán lịch trình hiện tại
        lichtrinh = sc;

        // Thiết lập layout cho LichDung
        LichDung.setLayout(new GridLayout(0, 1, 10, 10));
        LichDung.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ngayStr = sc.getDepartureDate().format(dtf);
        Title.setText("Lịch trình Tuyến: " + rt.getRouteName() + " / Ngày " + ngayStr);

        // Load dữ liệu tàu vào combobox và chọn đúng tàu
        loadTrainCombo();
        // Chọn đúng tàu theo sc
        for (int i = 0; i < trainid.size(); i++) {
            if (trainid.get(i) == sc.getTrainID()) {
                Traincombo.setSelectedIndex(i);
                break;
            }
        }

        // Load dữ liệu lái tàu vào combobox và chọn đúng lái tàu
        LoadLTCombo();
        for (int i = 0; i < LTid.size(); i++) {
            if (LTid.get(i) == sc.getDriverID()) {
                LTCombo.setSelectedIndex(i);
                break;
            }
        }

    selectOldCard();

        // Nếu muốn, có thể set các trường khác của lichtrinh vào các control khác trên frame ở đây
    }
    // Hàm load dữ liệu vào ComboBox tàu
    private void loadTrainCombo() {
        ArrayList<Train> trains = TrainDAO.getInstance().selectAll();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Train t : trains) {
            // Thêm theo dạng "ID - Tên Tàu"
            model.addElement(t.getTrainID() + " - " + t.getTrainName());
            trainid.add(t.getTrainID());
        }
        Traincombo.setModel(model);
    }
    private void LoadLTCombo()  {
        String sql="Select * from NHANVIEN where ChucVu='Lái Tàu'";
        try {
            Connection conn = Utils.Connectdb();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while(rs.next()) {
                int empId = rs.getInt("MaNV");
                String empName = rs.getString("HoTen");
                model.addElement(empId + " - " + empName);
                LTid.add(empId);
            }
            LTCombo.setModel(model);
            Utils.Closeconn(conn);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void addNewCard()
    {
        String sql="select T.MaGT, ThuTu, T.TenGT from GATAU T JOIN CT_TUYEN CT ON T.MaGT=CT.MaGT where CT.MaTuyen=? order by ThuTu";
        try {
            Connection conn = Utils.Connectdb();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, lichtrinh.getRouteId());
            java.sql.ResultSet rs = pst.executeQuery();
            LichDung.removeAll();
//            int y = 10;
            while(rs.next()) {
                int scheduleId = -1;
                int stationId = rs.getInt("MaGT");
                String stationName = rs.getString("TenGT");
                int order = rs.getInt("ThuTu");
                LocalTime gioDen = LocalTime.of(0, 0, 0);
                LocalTime gioDi = LocalTime.of(0, 0, 0);
                // Assuming the correct constructor is (int scheduleId, int stationId, String stationName, int order, LocalDate departureDate, LocalTime departureTime)
                StopSchedule stopsc = new StopSchedule(scheduleId, stationId, gioDen, gioDi, order);
                System.out.println("Adding StopSchedule: " + stopsc.getScheduleId() + ", " + stopsc.getStationId() + ", " + stopsc.getOrderNumber() + ", " + stopsc.getArrivalTime() + ", " + stopsc.getDepartureTime());
                StopScheduleCard card = new StopScheduleCard(stopsc, stationName);
                LichDung.add(card);
                // y += 110; // Khoảng cách giữa các thẻ
            }
            LichDung.revalidate();
            LichDung.repaint();
            Utils.Closeconn(conn);
        } catch (Exception e) {
            e.printStackTrace();}
    }
    private void selectOldCard()
    {
        String sql="select l.MaGT, TenGT, GioDen, GioDi, ThuTu from LICHDUNG l JOIN GATAU g ON l.MaGT=g.MaGT where MaLT=? order by ThuTu";
        try {
            int m=0;
            Connection conn = Utils.Connectdb();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, lichtrinh.getScheduleID());
            java.sql.ResultSet rs = pst.executeQuery();
            LichDung.removeAll();
            while(rs.next()) {
                m++;
                int scheduleId = lichtrinh.getScheduleID();
                int stationId = rs.getInt("MaGT");
                String stationName = rs.getString("TenGT");
                LocalTime gioDen = rs.getTime("GioDen").toLocalTime();
                LocalTime gioDi = rs.getTime("GioDi").toLocalTime();
                int order = rs.getInt("ThuTu");
                StopSchedule stopsc = new StopSchedule(scheduleId, stationId, gioDen, gioDi, order);
                StopScheduleCard card = new StopScheduleCard(stopsc, stationName);
                LichDung.add(card);
            }
            LichDung.revalidate();
            LichDung.repaint();
            Utils.Closeconn(conn);
            if (m == 0) {
                addNewCard();
                mode=3;
            }
        } catch (Exception e) {
            e.printStackTrace();}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        Traincombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        LTCombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        LichDung = new javax.swing.JPanel();
        ThoatButton = new javax.swing.JButton();
        LuuButton = new javax.swing.JButton();
        Title = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Tàu: ");

        Traincombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Nhân viên Lái Tàu:");

        LTCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        LichDung.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout LichDungLayout = new javax.swing.GroupLayout(LichDung);
        LichDung.setLayout(LichDungLayout);
        LichDungLayout.setHorizontalGroup(
            LichDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 736, Short.MAX_VALUE)
        );
        LichDungLayout.setVerticalGroup(
            LichDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(LichDung);

        ThoatButton.setText("Thoát");
        ThoatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThoatButtonActionPerformed(evt);
            }
        });

        LuuButton.setText("Lưu");
        LuuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LuuButtonActionPerformed(evt);
            }
        });

        Title.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Title.setText("Lịch trình Tuyến Ngày");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LuuButton)
                .addGap(38, 38, 38)
                .addComponent(ThoatButton)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(Traincombo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(LTCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LTCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Traincombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ThoatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LuuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ThoatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThoatButtonActionPerformed
        // TODO add your handling code here:
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy?", "Xác nhận hủy", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm != javax.swing.JOptionPane.YES_OPTION) this.dispose();
    }//GEN-LAST:event_ThoatButtonActionPerformed

    private void LuuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LuuButtonActionPerformed
        // TODO add your handling code here:
        // Lấy DriverID từ combobox
    int driverIndex = LTCombo.getSelectedIndex();
    int driverID = (driverIndex >= 0 && driverIndex < LTid.size()) ? LTid.get(driverIndex) : -1;

    // Lấy TrainID từ combobox
    int trainIndex = Traincombo.getSelectedIndex();
    int trainID = (trainIndex >= 0 && trainIndex < trainid.size()) ? trainid.get(trainIndex) : -1;

    // Lấy ScMakerID từ CurrentUser (giả sử có class CurrentUser với getEmpID())
    // int scMakerID = CurrentUser.EmpID;
        int scMakerID = 1; // Lấy ID của người tạo lịch trình từ CurrentUser
    if (mode == 1) {
        // Tìm Card có ThuTu == 1 để lấy DepartureTime
        LocalTime departureTime = null;
        for (java.awt.Component comp : LichDung.getComponents()) {
            if (comp instanceof StopScheduleCard) {
                StopScheduleCard card = (StopScheduleCard) comp;
                if (card.getThuTu() == 1) {
                    departureTime = card.getGioDi(); // getGioDi trả về LocalTime
                    break;
                }
            }
        }
        if (departureTime == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy giờ khởi hành của ga đầu tiên!");
            return;
        }

        // Cập nhật các trường cho lichtrinh
        lichtrinh.setDepartureTime(departureTime);
        lichtrinh.setDriverID(driverID);
        lichtrinh.setTrainID(trainID);
        lichtrinh.setScMakerID(scMakerID);

        // Thêm vào CSDL
        int id = DAO.ScheduleDAO.getInstance().insert(lichtrinh);
        if (id > 0) {
            for (java.awt.Component comp : LichDung.getComponents()) {
                if (comp instanceof StopScheduleCard) {
                    ((StopScheduleCard) comp).insertStopSchedule(id);
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm lịch trình thành công!");
            this.dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm lịch trình thất bại!");
        }
    } else if (mode == 2) {
        // mode != 1: update lịch trình
        // Tìm Card có ThuTu == 1 để lấy DepartureTime
        LocalTime departureTime = null;
        for (java.awt.Component comp : LichDung.getComponents()) {
            if (comp instanceof StopScheduleCard) {
                StopScheduleCard card = (StopScheduleCard) comp;
                if (card.getThuTu() == 1) {
                    departureTime = card.getGioDi();
                    break;
                }
            }
        }
        if (departureTime == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy giờ khởi hành của ga đầu tiên!");
            return;
        }

        lichtrinh.setDepartureTime(departureTime);
        lichtrinh.setDriverID(driverID);
        lichtrinh.setTrainID(trainID);
        lichtrinh.setScMakerID(scMakerID);

        int result = DAO.ScheduleDAO.getInstance().update(lichtrinh);
        if (result > 0) {
            for (java.awt.Component comp : LichDung.getComponents()) {
                if (comp instanceof StopScheduleCard) {
                    ((StopScheduleCard) comp).updateStopSchedule();
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật lịch trình thành công!");
            this.dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật lịch trình thất bại!");
        }
    }
    else
    {
        LocalTime departureTime = null;
        for (java.awt.Component comp : LichDung.getComponents()) {
            if (comp instanceof StopScheduleCard) {
                StopScheduleCard card = (StopScheduleCard) comp;
                if (card.getThuTu() == 1) {
                    departureTime = card.getGioDi();
                    break;
                }
            }
        }
        if (departureTime == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy giờ khởi hành của ga đầu tiên!");
            return;
        }

        lichtrinh.setDepartureTime(departureTime);
        lichtrinh.setDriverID(driverID);
        lichtrinh.setTrainID(trainID);
        lichtrinh.setScMakerID(scMakerID);

        int result = DAO.ScheduleDAO.getInstance().update(lichtrinh);
       if (result > 0) {
            for (java.awt.Component comp : LichDung.getComponents()) {
                if (comp instanceof StopScheduleCard) {
                    ((StopScheduleCard) comp).insertStopSchedule(lichtrinh.getScheduleID());
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm lịch trình thành công!");
            this.dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm lịch trình thất bại!");
        }
    }
    }//GEN-LAST:event_LuuButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddLichTrinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> LTCombo;
    private javax.swing.JPanel LichDung;
    private javax.swing.JButton LuuButton;
    private javax.swing.JButton ThoatButton;
    private javax.swing.JLabel Title;
    private javax.swing.JComboBox<String> Traincombo;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
