/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import Model.StopSchedule;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 *
 * @author capta
 */
public class StopScheduleCard extends javax.swing.JPanel {
    private StopSchedule stopsc;
    private String GaName;
    /**
     * Creates new form StopScheduleCard
     */
    public StopScheduleCard() {
        initComponents();
        SpinnerModel model = new SpinnerDateModel();
        GioDen = new JSpinner(model);
        GioDi = new JSpinner(model);
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(GioDen, " HH ':' mm ");
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(GioDi, " HH ':' mm ");
        GioDen.setEditor(editor1);
        GioDi.setEditor(editor2);

        // Đặt giá trị mặc định là 00:00
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        GioDen.setValue(cal.getTime());
        GioDi.setValue(cal.getTime());
    }
    public StopScheduleCard(StopSchedule m, String ganame)
    {
        stopsc=m;
        GaName=ganame;
        initComponents();
        ThuTu.setText(String.valueOf(stopsc.getOrderNumber()));
        ThuTu.setOpaque(true); // Dòng này là bắt buộc để màu nền hiện ra!
        ThuTu.setBackground(new Color(52,106,252));
        MaGa.setText("Mã ga: " + String.valueOf(stopsc.getStationId()));
        TenGa.setText("Tên ga: " + ganame);
        // Sau initComponents()
        SpinnerModel modelDen = new SpinnerDateModel();
        SpinnerModel modelDi = new SpinnerDateModel();
        GioDen.setModel(modelDen);
        GioDi.setModel(modelDi);

        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(GioDen, "HH:mm");
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(GioDi, "HH:mm");
        GioDen.setEditor(editor1);
        GioDi.setEditor(editor2);
        // Đặt giá trị mặc định là giờ đến và giờ đi từ đối tượng StopSchedule
        GioDen.setValue(localTimeToDate(stopsc.getArrivalTime()));
        GioDi.setValue(localTimeToDate(stopsc.getDepartureTime()));
    }

        private Date localTimeToDate(LocalTime lt) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, lt.getHour());
        cal.set(Calendar.MINUTE, lt.getMinute());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // Đặt ngày về một giá trị mặc định, ví dụ 1970-01-01
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public void insertStopSchedule(int Malichtrinh)
    {
        try {
        // Lấy giá trị từ spinner và chuyển về LocalTime
        Date gioDenDate = (Date) GioDen.getValue();
        Date gioDiDate = (Date) GioDi.getValue();
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTime(gioDenDate);
        LocalTime gioDen = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        cal.setTime(gioDiDate);
        LocalTime gioDi = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

        stopsc.setScheduleId(Malichtrinh);
        stopsc.setArrivalTime(gioDen);
        stopsc.setDepartureTime(gioDi);

        // Gọi DAO để insert vào CSDL
        DAO.StopScheduleDAO.getInstance().insert(stopsc);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void updateStopSchedule()
    {
        try {
            // Lấy giá trị từ spinner và chuyển về LocalTime
            Date gioDenDate = (Date) GioDen.getValue();
            Date gioDiDate = (Date) GioDi.getValue();
            java.util.Calendar cal = java.util.Calendar.getInstance();

            cal.setTime(gioDenDate);
            LocalTime gioDen = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
            cal.setTime(gioDiDate);
            LocalTime gioDi = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

            stopsc.setArrivalTime(gioDen);
            stopsc.setDepartureTime(gioDi);

            // Gọi DAO để update vào CSDL
            DAO.StopScheduleDAO.getInstance().update(stopsc);
        } catch (Exception e) {
            e.printStackTrace();
    }
    }
    public LocalTime getGioDi() {
        Date gioDiDate = (Date) GioDi.getValue();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(gioDiDate);
        return LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }
    public int getThuTu() {
        return stopsc.getOrderNumber();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ThuTu = new javax.swing.JLabel();
        MaGa = new javax.swing.JLabel();
        TenGa = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        GioDen = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        GioDi = new javax.swing.JSpinner();

        ThuTu.setBackground(new java.awt.Color(53, 106, 252));
        ThuTu.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        ThuTu.setForeground(new java.awt.Color(255, 255, 255));
        ThuTu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ThuTu.setText("1");

        MaGa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        MaGa.setText("Mã ga:");

        TenGa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        TenGa.setText("Tên ga:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Giờ đến:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Giờ đi:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ThuTu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MaGa)
                    .addComponent(TenGa))
                .addGap(247, 247, 247)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(GioDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(GioDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ThuTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MaGa)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TenGa)
                    .addComponent(GioDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GioDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner GioDen;
    private javax.swing.JSpinner GioDi;
    private javax.swing.JLabel MaGa;
    private javax.swing.JLabel TenGa;
    private javax.swing.JLabel ThuTu;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
