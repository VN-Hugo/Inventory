package DAO;

import Connection.ConnectionUtils;
import DTO.ChiTietPhieuXuatDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietPhieuXuatDAO {

    public static ChiTietPhieuXuatDAO getInstance() {
        return new ChiTietPhieuXuatDAO();
    }

    public int insert(ArrayList<ChiTietPhieuXuatDTO> list) throws ClassNotFoundException {
        int result = 0;
        for (ChiTietPhieuXuatDTO ct : list) {
            try {
                Connection con = ConnectionUtils.getMyConnection();
                String sql = "INSERT INTO chitietphieuxuat(maphieuxuat, masanpham, soluong, makho) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, ct.getMaPhieuXuat());
                pst.setInt(2, ct.getMaSanPham());
                pst.setInt(3, ct.getSoLuong());
                pst.setInt(4, ct.getMaKho());
                result = pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Cập nhật tồn kho sau khi xuất hàng (giảm số lượng tồn)
            TonKhoDAO.getInstance().updateSoLuongTon(ct.getMaSanPham(), -ct.getSoLuong(), ct.getMaKho());
        }
        return result;
    }

    public int update(ChiTietPhieuXuatDTO ct) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE chitietphieuxuat SET soluong=? WHERE maphieuxuat=? AND masanpham=? AND makho=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ct.getSoLuong());
            pst.setInt(2, ct.getMaPhieuXuat());
            pst.setInt(3, ct.getMaSanPham());
            pst.setInt(4, ct.getMaKho());
            result = pst.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(String maphieu) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "DELETE FROM chitietphieuxuat WHERE maphieuxuat = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maphieu);
            result = pst.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<ChiTietPhieuXuatDTO> selectAll(String maPhieu) {
        ArrayList<ChiTietPhieuXuatDTO> result = new ArrayList<>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM chitietphieuxuat WHERE maphieuxuat = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maPhieu);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ChiTietPhieuXuatDTO dto = new ChiTietPhieuXuatDTO(
                        rs.getInt("maphieuxuat"),
                        rs.getInt("masanpham"),
                        rs.getInt("soluong"),
                        rs.getInt("makho")
                );
                result.add(dto);
            }
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ChiTietPhieuXuatDTO selectById(String id) {
        // Nếu cần, có thể viết thêm logic select theo maphieuxuat & masanpham
        return null;
    }

    public int getAutoIncrement() {
        // Không áp dụng AutoIncrement với bảng chi tiết
        return -1;
    }
}
