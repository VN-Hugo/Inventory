
package DAO;

import DTO.LichSuKiemKeDTO;
import java.sql.*;
import Connection.ConnectionUtils;
import java.util.ArrayList;
import java.util.List;

public class LichSuKiemKeDAO {
    public boolean insert(LichSuKiemKeDTO dto) {
        String sql = "INSERT INTO LICHSUKIEMKE (ID, MASP, MAKHUVUC, SOLUONG_HT, SOLUONG_TT, CHENHLECH, NGAY_KIEMKE, NGUOIKIEMKE) "
                   + "VALUES (SEQ_LSKK.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dto.getMaSanPham());
            ps.setString(2, dto.getMaKhuVuc());
            ps.setInt(3, dto.getSoLuongHeThong());
            ps.setInt(4, dto.getSoLuongThucTe());
            ps.setInt(5, dto.getChenhLech());
            ps.setDate(6, new java.sql.Date(dto.getNgayKiemKe().getTime()));
            ps.setString(7, dto.getNguoiKiemKe());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


