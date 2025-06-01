package DAO;

import Connection.ConnectionUtils;
import DTO.Report.ThongKeNhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {
    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }

    public static ArrayList<ThongKeNhaCungCapDTO> getThongKeNCC(String text, Date timeStart, Date timeEnd) throws ClassNotFoundException {
        ArrayList<ThongKeNhaCungCapDTO> result = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeEnd);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);

            Connection con = ConnectionUtils.getMyConnection();  // sửa thành kết nối Oracle
            String sql = 
                "WITH ncc AS ( " +
                "  SELECT nhacungcap.manhacungcap, nhacungcap.tennhacungcap, " +
                "         COUNT(phieunhap.maphieunhap) AS tongsophieu, " +
                "         SUM(phieunhap.tongtien) AS tongsotien " +
                "  FROM nhacungcap " +
                "  JOIN phieunhap ON nhacungcap.manhacungcap = phieunhap.manhacungcap " +
                "  WHERE phieunhap.thoigian BETWEEN ? AND ? " +
                "  GROUP BY nhacungcap.manhacungcap, nhacungcap.tennhacungcap " +
                ") " +
                "SELECT manhacungcap, tennhacungcap, " +
                "       NVL(tongsophieu, 0) AS soluong, " +
                "       NVL(tongsotien, 0) AS total " +
                "FROM ncc " +
                "WHERE tennhacungcap LIKE ? OR TO_CHAR(manhacungcap) LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, new Timestamp(timeStart.getTime()));
            pst.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
            pst.setString(3, "%" + text + "%");
            pst.setString(4, "%" + text + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int mancc = rs.getInt("manhacungcap");
                String tenncc = rs.getString("tennhacungcap");
                int soluong = rs.getInt("soluong");
                long tongtien = rs.getLong("total");
                ThongKeNhaCungCapDTO x = new ThongKeNhaCungCapDTO(mancc, tenncc, soluong, tongtien);
                result.add(x);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
