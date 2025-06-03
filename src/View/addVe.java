/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import java.util.ArrayList;

import DAO.TicketTypeDAO;
import Model.Route;
import Model.Ticket;
import Model.TicketType;
import DAO.RouteDAO;
import Database.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
/**
 *
 * @author COMPUTER
 */
public class addVe extends javax.swing.JPanel {
    private ArrayList<TicketType> LoaiVe;
    private ArrayList<Route> Tuyen;
    private ArrayList<Integer> Ga;
    private int Malichtrinh;
    private Time GoTime;
    /**
     * Creates new form addVe
     */
    public addVe() {
        initComponents();
        KhongLich.setVisible(false);
        Malichtrinh=0;
        LoaiVe = new ArrayList<>();
        Tuyen = new ArrayList<>();
        Ga = new ArrayList<>();
        LoaiVeCombo.removeAllItems();
        TuyenCombo.removeAllItems();
        GaDiCombo.removeAllItems();
        GaDenCombo.removeAllItems();
        LoaiVe = TicketTypeDAO.getInstance().selectAll();
        if (LoaiVe != null) {
            for (TicketType ticketType : LoaiVe) {
                LoaiVeCombo.addItem(ticketType.getDescription());
            }
        }
        Tuyen = RouteDAO.getInstance().selectAll();
        if (Tuyen != null) {
            for (Route route : Tuyen) {
                TuyenCombo.addItem(route.getRouteName());
            }
        }

    }
    private void addGa() {
    GaDiCombo.removeAllItems();
    GaDenCombo.removeAllItems();
    int tuyenIndex = TuyenCombo.getSelectedIndex();
    if (tuyenIndex < 0 || tuyenIndex >= Tuyen.size()) return; // Không làm gì nếu chưa chọn tuyến hợp lệ

    String sql = "select c.MaGT, TenGT from CT_TUYEN c join GATAU g on c.MaGT=g.MaGT where c.MaTuyen=?";
    try {
        Connection con = Utils.Connectdb();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, Tuyen.get(tuyenIndex).getRouteId());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Ga.add(rs.getInt("MaGT"));
            GaDiCombo.addItem(rs.getString("TenGT"));
            GaDenCombo.addItem(rs.getString("TenGT"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void TimVe()
{
    int gaDiIndex = GaDiCombo.getSelectedIndex();
    int gaDenIndex = GaDenCombo.getSelectedIndex();
    if (gaDiIndex == gaDenIndex) return;
    String sql="SELECT l.MaLT, ld.GioDi FROM LICHTRINH l JOIN LICHDUNG ld ON l.MaLT = ld.MaLT "+
    "WHERE l.MaTuyen = ? AND l.NgayKH = CURDATE() AND l.GioKH > CURTIME() AND ld.MaGT = ? "+
    "AND EXISTS (SELECT 1 FROM LICHDUNG ld2 WHERE ld2.MaLT = l.MaLT AND ld2.MaGT = ? AND ld2.ThuTu > ld.ThuTu)";
    try {
        Connection con = Utils.Connectdb();
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, Tuyen.get(TuyenCombo.getSelectedIndex()).getRouteId());
        pst.setInt(2, Ga.get(GaDiCombo.getSelectedIndex()));
        pst.setInt(3, Ga.get(GaDenCombo.getSelectedIndex()));
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Malichtrinh = rs.getInt("MaLT");
            GoTime = rs.getTime("GioDi");
            GioDi.setText(GoTime.toString());
            KhongLich.setVisible(false);
        } else {
            Malichtrinh = 0;
            GoTime = null;
            GioDi.setText("");
            KhongLich.setVisible(true);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void TinhGia() {
    int loaiIndex = LoaiVeCombo.getSelectedIndex();
    int tuyenIndex = TuyenCombo.getSelectedIndex();
    int gaDiIndex = GaDiCombo.getSelectedIndex();
    int gaDenIndex = GaDenCombo.getSelectedIndex();


    if (loaiIndex < 0 || loaiIndex >= LoaiVe.size()) return;
    if (tuyenIndex < 0 || tuyenIndex >= Tuyen.size()) return;

    String tenLoai = LoaiVeCombo.getSelectedItem().toString();
    int giaGoc = LoaiVe.get(loaiIndex).getBasePrice();
    // Nếu là vé theo tháng hoặc vé theo ngày
    if (tenLoai.equalsIgnoreCase("Vé theo tháng") || tenLoai.equalsIgnoreCase("Vé theo ngày")) {
        Gia.setText(String.valueOf(giaGoc));
        KhongLich.setVisible(false);
        return;
    }
    
    // Nếu là vé theo lượt
    if (tenLoai.equalsIgnoreCase("Vé theo lượt")) {
        if (gaDiIndex < 0 || gaDenIndex < 0 || gaDiIndex == gaDenIndex) return;
        if (Malichtrinh == 0) return;
        int khoangCach = 0;
        try {
            // Lấy thứ tự ga đi và ga đến từ LICHDUNG
            String sql = "SELECT ThuTu FROM LICHDUNG WHERE MaLT=? AND MaGT=?";
            Connection con = Utils.Connectdb();

            PreparedStatement pst1 = con.prepareStatement(sql);
            pst1.setInt(1, Malichtrinh);
            pst1.setInt(2, Ga.get(gaDiIndex));
            ResultSet rs1 = pst1.executeQuery();
            int thuTuDi = -1;
            if (rs1.next()) thuTuDi = rs1.getInt("ThuTu");

            PreparedStatement pst2 = con.prepareStatement(sql);
            pst2.setInt(1, Malichtrinh);
            pst2.setInt(2, Ga.get(gaDenIndex));
            ResultSet rs2 = pst2.executeQuery();
            int thuTuDen = -1;
            if (rs2.next()) thuTuDen = rs2.getInt("ThuTu");

            if (thuTuDi != -1 && thuTuDen != -1) {
                khoangCach = Math.abs(thuTuDen - thuTuDi);
            }

            int giaChenhLech = Tuyen.get(tuyenIndex).getExtraCost();

            int giaXuat = Math.round(giaGoc + khoangCach * giaChenhLech);
            Gia.setText(String.valueOf(giaXuat));

            Utils.Closeconn(con);
        } catch (Exception e) {
            e.printStackTrace();
            Gia.setText("0");
        }
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

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LoaiVeCombo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        GaDiCombo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        GaDenCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TuyenCombo = new javax.swing.JComboBox<>();
        XuatButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        Gia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        GioDi = new javax.swing.JTextField();
        KhongLich = new javax.swing.JLabel();

        jLabel6.setText("jLabel6");

        jPanel1.setBackground(new java.awt.Color(53, 106, 252));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bán vé");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Loại vé:");

        LoaiVeCombo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        LoaiVeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vé theo lượt", "vé theo ngày", "vé theo tháng" }));
        LoaiVeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoaiVeComboActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Ga đến:");

        GaDiCombo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        GaDiCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        GaDiCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaDiComboActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Ga đi: ");

        GaDenCombo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        GaDenCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        GaDenCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaDenComboActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setText("Tuyến:");

        TuyenCombo.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        TuyenCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        TuyenCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TuyenComboActionPerformed(evt);
            }
        });

        XuatButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        XuatButton.setText("Xuất Vé");
        XuatButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        XuatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XuatButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Giá:");

        Gia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Giờ đi:");

        GioDi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        GioDi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GioDiActionPerformed(evt);
            }
        });

        KhongLich.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        KhongLich.setForeground(new java.awt.Color(204, 0, 51));
        KhongLich.setText("Không tìm thấy Lịch phù hợp trong ngày!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GioDi, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TuyenCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LoaiVeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)))
                            .addComponent(Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(XuatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(114, 114, 114))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(KhongLich, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(GaDiCombo, 0, 296, Short.MAX_VALUE)
                                            .addComponent(GaDenCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(21, 21, 21))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(LoaiVeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GaDiCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GaDenCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(TuyenCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(GioDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KhongLich, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Gia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(XuatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void XuatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XuatButtonActionPerformed
        // TODO add your handling code here:
    // Kiểm tra ga đi và ga đến
    int gaDiIndex = GaDiCombo.getSelectedIndex();
    int gaDenIndex = GaDenCombo.getSelectedIndex();
    if (gaDiIndex == gaDenIndex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ga đi và ga đến không được trùng nhau. Vui lòng chọn lại!");
        return;
    }
    if (Malichtrinh == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Không có lịch chạy phù hợp trong ngày!");
        return;
    }
    try {
        int price = Integer.parseInt(Gia.getText().trim());
        int ticketTypeId = LoaiVe.get(LoaiVeCombo.getSelectedIndex()).getTicketTypeID();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        int scheduleId = Malichtrinh;

        // Tạo đối tượng Ticket
        Ticket ticket = new Ticket(0, price, ticketTypeId, now, scheduleId);

        // Insert vào CSDL
        int result = DAO.TicketDAO.getInstance().insert(ticket);
        if (result > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Xuất vé thành công!");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Xuất vé thất bại!");
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ hoặc lỗi hệ thống!");
        e.printStackTrace();
    }
    }//GEN-LAST:event_XuatButtonActionPerformed

    private void GioDiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GioDiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GioDiActionPerformed

    private void LoaiVeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoaiVeComboActionPerformed
        // TODO add your handling code here:
        TinhGia();
    }//GEN-LAST:event_LoaiVeComboActionPerformed

    private void TuyenComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TuyenComboActionPerformed
        // TODO add your handling code here:
        addGa();
    }//GEN-LAST:event_TuyenComboActionPerformed

    private void GaDiComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaDiComboActionPerformed
        // TODO add your handling code here:
        TimVe();
        TinhGia();
    }//GEN-LAST:event_GaDiComboActionPerformed

    private void GaDenComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaDenComboActionPerformed
        // TODO add your handling code here:
        TimVe();
        TinhGia();
    }//GEN-LAST:event_GaDenComboActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> GaDenCombo;
    private javax.swing.JComboBox<String> GaDiCombo;
    private javax.swing.JTextField Gia;
    private javax.swing.JTextField GioDi;
    private javax.swing.JLabel KhongLich;
    private javax.swing.JComboBox<String> LoaiVeCombo;
    private javax.swing.JComboBox<String> TuyenCombo;
    private javax.swing.JButton XuatButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
