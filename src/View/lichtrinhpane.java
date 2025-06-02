/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import DAO.RouteDAO;
import Model.Route;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import com.toedter.calendar.JCalendar;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import Database.Utils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import Model.Schedule;



/**
 *
 * @author capta
 */
public class lichtrinhpane extends javax.swing.JPanel {
    private DefaultTableModel model;
    private Route rt;
    /**
     * Creates new form lichtrinhpane
     */
    public lichtrinhpane() {
        initComponents();
        TableLT.setDefaultEditor(Object.class, null); // Không cho phép sửa bất kỳ ô nào
    TableLT.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Chỉ cho phép chọn
        model = new DefaultTableModel(
    new Object[]{"MaLT", "Giờ Khởi Hành", "Tình Trạng", "MaTau", "MaNVLT", "MaNVLL", "SoLuongVeDB", "Tàu", "Lái Tàu", "Lập Lịch"}, 0)
    {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Không cho phép sửa bất kỳ ô nào
    }
};
    TableLT.setModel(model);
            int[] visible = {1, 2, 7, 8, 9}; // index của các cột muốn hiện
        for (int i = 0; i < TableLT.getColumnCount(); i++) {
            boolean show = false;
            for (int v : visible) if (i == v) show = true;
            if (!show) {
                TableLT.getColumnModel().getColumn(i).setMinWidth(0);
                TableLT.getColumnModel().getColumn(i).setMaxWidth(0);
                TableLT.getColumnModel().getColumn(i).setWidth(0);
                TableLT.getColumnModel().getColumn(i).setPreferredWidth(0);
            }
        }
        TableLT.getColumnModel().getColumn(1).setHeaderValue("Giờ Khởi Hành");
        TableLT.getColumnModel().getColumn(2).setHeaderValue("Tình Trạng");
        TableLT.getColumnModel().getColumn(7).setHeaderValue("Tàu");
        TableLT.getColumnModel().getColumn(8).setHeaderValue("Lái Tàu");
        TableLT.getColumnModel().getColumn(9).setHeaderValue("Lập Lịch");
        RoutePanel.setLayout(new GridLayout(0, 1, 10, 10));
        RoutePanel.setBorder(BorderFactory.createEmptyBorder(5, 5,5, 5));
        ArrayList<Route> routes = RouteDAO.getInstance().selectAll();
        for (Route r : routes)
        {
            TuyenCard card = new TuyenCard();
            card.setTuyen(r);
            card.addPropertyChangeListener("routeSelected", evt -> {
         rt = (Route) evt.getNewValue();
        loadScheduleForRoute(rt.getRouteId());
        for (java.awt.Component comp : RoutePanel.getComponents()) {
        if (comp instanceof TuyenCard) {
            TuyenCard otherCard = (TuyenCard) comp;
            if (otherCard != card) {
                otherCard.setBackGroundNormal();
            }
            else {
                otherCard.setBackGroundClicked();
            }
        }
    }
    });
            RoutePanel.add(card);
        }
        RoutePanel.revalidate();
        RoutePanel.repaint();
        ChonNgay.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (rt != null) {
                    loadScheduleForRoute(rt.getRouteId());
                }
            }
        });
    }
    public void loadScheduleForRoute(int id)
    {
        java.util.Date selectedDate = ChonNgay.getDate();
        if (selectedDate == null) {
            // Handle the case where no date is selected
            return;
        }
        String sql="Select MaLT, GioKH, TinhTrang, lt.MaTau, lt.MaNVLT, lt.MaNVLL, SoLuongVeDB, TenTau, nvlt.HoTen as HTLT, nvll.HoTen as HTLL"+
                " from LICHTRINH lt join TAU t on lt.MaTau=t.MaTau "+ 
                "join NHANVIEN nvlt on nvlt.MaNV=lt.MaNVLT "+
                "join NHANVIEN nvll on nvll.MaNV=lt.MaNVLL "+
                "where MaTuyen=? and NgayKH=?";
        Date date = new Date(selectedDate.getTime()); 
        try{
            Connection con= Utils.Connectdb();
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.setDate(2, date);
            ResultSet rs=pst.executeQuery();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while(rs.next())
            {
                Object[] row = new Object[]{
                    rs.getInt("MaLT"),
                    rs.getTime("GioKH"),
                    rs.getString("TinhTrang"),
                    rs.getInt("MaTau"),
                    rs.getInt("MaNVLT"),
                    rs.getInt("MaNVLL"),
                    rs.getInt("SoLuongVeDB"),
                    rs.getString("TenTau"),
                    rs.getString("HTLT"),
                    rs.getString("HTLL")
                };
                model.addRow(row);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ChonNgay = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableLT = new javax.swing.JTable();
        ThemButton = new javax.swing.JButton();
        SuaButton = new javax.swing.JButton();
        XoaButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RoutePanel = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(740, 460));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Chọn ngày:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Chọn tuyến:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Các lịch trình của tuyến");

        TableLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TableLT);

        ThemButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ThemButton.setText("Thêm ");
        ThemButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThemButtonMouseClicked(evt);
            }
        });
        ThemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemButtonActionPerformed(evt);
            }
        });

        SuaButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SuaButton.setText("Sửa");
        SuaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaButtonActionPerformed(evt);
            }
        });

        XoaButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        XoaButton.setText("Xóa");
        XoaButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XoaButtonMouseClicked(evt);
            }
        });
        XoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaButtonActionPerformed(evt);
            }
        });

        RoutePanel.setBackground(new java.awt.Color(255, 255, 255));
        RoutePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                RoutePanelComponentShown(evt);
            }
        });

        javax.swing.GroupLayout RoutePanelLayout = new javax.swing.GroupLayout(RoutePanel);
        RoutePanel.setLayout(RoutePanelLayout);
        RoutePanelLayout.setHorizontalGroup(
            RoutePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        RoutePanelLayout.setVerticalGroup(
            RoutePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(RoutePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChonNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(jLabel3)))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(ThemButton)
                        .addGap(86, 86, 86)
                        .addComponent(SuaButton)
                        .addGap(84, 84, 84)
                        .addComponent(XoaButton)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(ChonNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(SuaButton, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(ThemButton, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(XoaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ThemButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThemButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ThemButtonMouseClicked

    private void ThemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemButtonActionPerformed
        // TODO add your handling code here:
    if (rt == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một tuyến trước khi thêm lịch trình!");
        return;
    }
    if (ChonNgay.getDate() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trước khi thêm lịch trình!");
        return;
    }
        AddLichTrinh addFrame = new AddLichTrinh(ChonNgay.getDate(), rt);
        addFrame.setVisible(true);
        addFrame.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        // Reload lại bảng khi cửa sổ AddLichTrinh đóng
        loadScheduleForRoute(rt.getRouteId());
    }
});
    }//GEN-LAST:event_ThemButtonActionPerformed

    private void SuaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaButtonActionPerformed
        // TODO add your handling code here:
    int selectedRow = TableLT.getSelectedRow();
    if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch trình để sửa!");
        return;
    }
    // Lấy dữ liệu từ model (dù cột bị ẩn vẫn lấy được)
    DefaultTableModel model = (DefaultTableModel) TableLT.getModel();
    int scheduleID = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
    java.sql.Time gioKH = (java.sql.Time) model.getValueAt(selectedRow, 1);
    String tinhTrang = model.getValueAt(selectedRow, 2).toString();
    int trainID = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());
    int driverID = Integer.parseInt(model.getValueAt(selectedRow, 4).toString());
    int scMakerID = Integer.parseInt(model.getValueAt(selectedRow, 5).toString());
    int reservedTicketCount = Integer.parseInt(model.getValueAt(selectedRow, 6).toString());
    int routeId = rt != null ? rt.getRouteId() : -1;
    java.util.Date selectedDate = ChonNgay.getDate();
    java.time.LocalDate departureDate = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    java.time.LocalTime departureTime = gioKH.toLocalTime();

    Schedule schedule = new Schedule(
        scheduleID,
        departureDate,
        departureTime,
        tinhTrang,
        trainID,
        driverID,
        scMakerID,
        reservedTicketCount,
        routeId
    );

    AddLichTrinh addFrame = new AddLichTrinh(schedule, rt);
    addFrame.setVisible(true);
    addFrame.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        // Reload lại bảng khi cửa sổ AddLichTrinh đóng
        loadScheduleForRoute(rt.getRouteId());
    }
});

    }//GEN-LAST:event_SuaButtonActionPerformed

    private void XoaButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XoaButtonMouseClicked
        // TODO add your handling code here:
        int selectedRow = TableLT.getSelectedRow();
    if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch trình để xóa!");
        return;
    }
    Object maLTObj = TableLT.getModel().getValueAt(selectedRow, 0);
    if (maLTObj == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Không lấy được mã lịch trình!");
        return;
    }
    int maLT = Integer.parseInt(maLTObj.toString());
    int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lịch trình này?", "Xác nhận xóa", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    try {
        Connection con = Utils.Connectdb();
        String sql1 = "DELETE FROM LICHDUNG WHERE MaLT = ?";
        PreparedStatement pst1 = con.prepareStatement(sql1);
        pst1.setInt(1, maLT);
        pst1.executeUpdate();

        String sql2 = "DELETE FROM LICHTRINH WHERE MaLT = ?";
        PreparedStatement pst2 = con.prepareStatement(sql2);
        pst2.setInt(1, maLT);
        pst2.executeUpdate();

        Utils.Closeconn(con);

        // Xóa dòng khỏi bảng giao diện
        ((DefaultTableModel) TableLT.getModel()).removeRow(selectedRow);

        javax.swing.JOptionPane.showMessageDialog(this, "Xóa lịch trình thành công!");
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi xóa lịch trình!");
    }
    }//GEN-LAST:event_XoaButtonMouseClicked

    private void XoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaButtonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_XoaButtonActionPerformed

    private void RoutePanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_RoutePanelComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_RoutePanelComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser ChonNgay;
    private javax.swing.JPanel RoutePanel;
    private javax.swing.JButton SuaButton;
    private javax.swing.JTable TableLT;
    private javax.swing.JButton ThemButton;
    private javax.swing.JButton XoaButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
