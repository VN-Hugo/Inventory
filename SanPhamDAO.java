package DAO;

import Connection.ConnectionUtils;
import DTO.SanPhamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPhamDAO {

    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }

    // Lấy tất cả sản phẩm đang hoạt động
    public ArrayList<SanPhamDTO> selectAll() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE trangthai = 1"; // nếu có cột trạng thái
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                    rs.getInt("masp"),
                    rs.getString("tensp"),
                    rs.getString("hinhanh"),
                    rs.getString("xuatxu"),
                    rs.getInt("thoigianbaohanh"),
                    rs.getString("thuonghieu"),
                    rs.getInt("khuvuckho"),
                    rs.getInt("soluongton")
                );
                result.add(sp);
            }

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Thêm sản phẩm mới
    public int insert(SanPhamDTO sp) {
        int result = 0;
        String sql = "INSERT INTO SanPham (masp, tensp, hinhanh, xuatxu, thoigianbaohanh, thuonghieu, khuvuckho, soluongton, trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, sp.getMasp());
            pst.setString(2, sp.getTensp());
            pst.setString(3, sp.getHinhanh());
            pst.setString(4, sp.getXuatxu());
            pst.setInt(5, sp.getThoigianbaohanh());
            pst.setString(6, sp.getThuonghieu());
            pst.setInt(7, sp.getKhuvuckho());
            pst.setInt(8, sp.getSoluongton());

            result = pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Cập nhật thông tin sản phẩm
    public int update(SanPhamDTO sp) {
        int result = 0;
        String sql = "UPDATE SanPham SET tensp = ?, hinhanh = ?, xuatxu = ?, thoigianbaohanh = ?, thuonghieu = ?, khuvuckho = ?, soluongton = ? WHERE masp = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, sp.getTensp());
            pst.setString(2, sp.getHinhanh());
            pst.setString(3, sp.getXuatxu());
            pst.setInt(4, sp.getThoigianbaohanh());
            pst.setString(5, sp.getThuonghieu());
            pst.setInt(6, sp.getKhuvuckho());
            pst.setInt(7, sp.getSoluongton());
            pst.setInt(8, sp.getMasp());

            result = pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Xóa sản phẩm (có thể là xóa mềm bằng cách cập nhật trạng thái)
    public int delete(String t) {
        int result = 0;
        String sql = "UPDATE SanPham SET trangthai = 0 WHERE masp = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Tìm sản phẩm theo mã
    public SanPhamDTO selectById(int masp) {
        SanPhamDTO sp = null;
        String sql = "SELECT * FROM SanPham WHERE masp = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, masp);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    sp = new SanPhamDTO(
                        rs.getInt("masp"),
                        rs.getString("tensp"),
                        rs.getString("hinhanh"),
                        rs.getString("xuatxu"),
                        rs.getInt("thoigianbaohanh"),
                        rs.getString("thuonghieu"),
                        rs.getInt("khuvuckho"),
                        rs.getInt("soluongton")
                    );
                }
            }

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sp;
    }

    // Lấy mã sản phẩm mới (nếu có dùng sequence trong Oracle)
    public int getAutoIncrement() {
        int result = -1;
        String sql = "SELECT seq_sanpham_id.NEXTVAL FROM dual"; // giả sử có sequence này
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
}
