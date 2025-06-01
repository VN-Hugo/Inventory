package DAO;

import Connection.ConnectionUtils;
import DTO.KhachHangDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO {

    // Singleton pattern (nếu muốn)
    public static KhachHangDAO getInstance() {
        return new KhachHangDAO();
    }

    // Lấy danh sách khách hàng
    public ArrayList<KhachHangDTO> selectAll() {
        ArrayList<KhachHangDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE trangthai = 1";  // Giả sử có cột trangthai
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int maKH = rs.getInt("makhachhang");
                String hoten = rs.getString("hoten");
                String sdt = rs.getString("sdt");
                String diachi = rs.getString("diachi");
                Date ngaythamgia = rs.getDate("ngaythamgia");  // Nếu có cột này
                KhachHangDTO kh = new KhachHangDTO(maKH, hoten, sdt, diachi, ngaythamgia);
                result.add(kh);
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Thêm khách hàng mới
    public int insert(KhachHangDTO kh) {
        int result = 0;
        String sql = "INSERT INTO KhachHang (makhachhang, hoten, sdt, diachi, ngaythamgia, trangthai) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, kh.getMaKH());
            pst.setString(2, kh.getHoten());
            pst.setString(3, kh.getSdt());
            pst.setString(4, kh.getDiachi());
            pst.setDate(5, new java.sql.Date(kh.getNgaythamgia().getTime())); // Convert java.util.Date -> java.sql.Date
            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Cập nhật khách hàng
    public int update(KhachHangDTO kh) {
        int result = 0;
        String sql = "UPDATE KhachHang SET hoten = ?, sdt = ?, diachi = ?, ngaythamgia = ? WHERE makhachhang = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, kh.getHoten());
            pst.setString(2, kh.getSdt());
            pst.setString(3, kh.getDiachi());
            pst.setDate(4, new java.sql.Date(kh.getNgaythamgia().getTime()));
            pst.setInt(5, kh.getMaKH());
            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Xóa khách hàng theo ID
    public int delete(String maKH) {
        int result = 0;
        String sql = "DELETE FROM KhachHang WHERE makhachhang = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maKH);
            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Lấy thông tin khách hàng theo ID
    public KhachHangDTO selectById(String maKH) {
        KhachHangDTO result = null;
        String sql = "SELECT * FROM KhachHang WHERE makhachhang = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, maKH);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int makh = rs.getInt("makh");
                    String hoten = rs.getString("hoten");
                    String sdt = rs.getString("sdt");
                    String diachi = rs.getString("diachi");
                    Date ngaythamgia = rs.getDate("ngaythamgia");
                    result = new KhachHangDTO(makh, hoten, sdt, diachi, ngaythamgia);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Lấy giá trị Auto Increment từ sequence (Oracle)
    public int getAutoIncrement() {
        int result = -1;
        String sql = "SELECT seq_khachhang_id.NEXTVAL FROM dual";  // Giả sử bạn có sequence này
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
