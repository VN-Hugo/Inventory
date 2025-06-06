package DAO;

import Connection.ConnectionUtils;
import DTO.ChiTietPhieuNhapDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietPhieuNhapDAO {

    public static ChiTietPhieuNhapDAO getInstance() {
        return new ChiTietPhieuNhapDAO();
    }

    public int insert(ArrayList<ChiTietPhieuNhapDTO> t) throws ClassNotFoundException {
        int result = 0;
        for (int i = 0; i < t.size(); i++) {
            try {
                Connection con = ConnectionUtils.getMyConnection();
                String sql = "INSERT INTO chitietphieunhap(maphieunhap, masanpham, soluong,makho) VALUES (?, ?, ?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, t.get(i).getMaPhieuNhap());
                pst.setInt(2, t.get(i).getMaSanPham());
                pst.setInt(3, t.get(i).getSoLuong());
                pst.setInt(4, t.get(i).getMaKho());
                 result = pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            TonKhoDAO.getInstance().updateSoLuongTon(t.get(i).getMaSanPham(), t.get(i).getSoLuong(),t.get(i).getMaKho());
        }
        return result;
    }


    public int update(ChiTietPhieuNhapDTO t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE chitietphieunhap SET soluong=? WHERE maphieunhap=? AND masanpham=? AND makho =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getSoLuong());
            pst.setInt(2, t.getMaPhieuNhap());
            pst.setInt(3, t.getMaSanPham());
            pst.setInt(4, t.getMaKho());
            result = pst.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int delete(String maphieu) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "DELETE FROM chitietphieunhap WHERE maphieunhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maphieu);
            result = pst.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public ArrayList<ChiTietPhieuNhapDTO> selectAll(String t) {
        ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM chitietphieunhap  WHERE maphieunhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhapDTO dto = new ChiTietPhieuNhapDTO(
                        rs.getInt("maphieunhap"),
                        rs.getInt("masanpham"),
                        rs.getInt("soluong"),
                        rs.getInt("makho")
                );
                result.add(dto);
            }
        } catch (Exception ex) {
            Logger.getLogger(ChiTietPhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public ChiTietPhieuNhapDTO selectById(String id) {
        // Trường hợp này có thể cần truyền vào cả maphieu và masanpham => bỏ qua hoặc viết phương thức riêng
        return null;
    }

    public int getAutoIncrement() {
        // Bảng chi tiết thường không dùng AutoIncrement => trả về -1
        return -1;
    }
}
