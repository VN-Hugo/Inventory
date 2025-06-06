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
        String sql = "SELECT * FROM SanPham"; 
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                    rs.getInt("masp"),
                    rs.getString("tensp"),
                    rs.getString("xuatxu"),
                    rs.getString("thuonghieu")
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
        String sql = "INSERT INTO SanPham (masp, tensp, xuatxu, thuonghieu) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, sp.getMasp());
            pst.setString(2, sp.getTensp());
            pst.setString(3, sp.getXuatxu());
            pst.setString(4, sp.getThuonghieu());
            result = pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Cập nhật thông tin sản phẩm
    public int update(SanPhamDTO sp) {
        int result = 0;
        String sql = "UPDATE SanPham SET tensp = ?, xuatxu = ?, thuonghieu = ? WHERE masp = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, sp.getTensp());
            pst.setString(2, sp.getXuatxu());
            pst.setString(3, sp.getThuonghieu());
            pst.setInt(4, sp.getMasp());

            result = pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int delete(String masp) {
    int result = 0;
    String sql = "DELETE FROM SanPham WHERE masp = ?";
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setString(1, masp);
        result = pst.executeUpdate();
    } catch (Exception e) {
        Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
    }
    return result;
}

    // Tìm sản phẩm theo mã
    public SanPhamDTO selectById(String masp) {
        SanPhamDTO sp = null;
        String sql = "SELECT * FROM SanPham WHERE masp = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, masp);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    sp = new SanPhamDTO(
                        rs.getInt("masp"),
                        rs.getString("tensp"),
                        rs.getString("xuatxu"),
                        rs.getString("thuonghieu")
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
   

//     public int updateSoLuongTon(int masp, int soluong) {
//        int quantity_current = this.selectById(Integer.toString(masp)).getSoluongton();
//        int result = 0;
//        int quantity_change = quantity_current + soluong;
//        try {
//            Connection con = ConnectionUtils.getMyConnection();
//            String sql = "UPDATE `sanpham` SET `soluongton`=? WHERE masp = ?";
//            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
//            pst.setInt(1, quantity_change);
//            pst.setInt(2, masp);
//            result = pst.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return result;
//    }
//    public ArrayList<Object[]> getSoLuongTonTheoSanPham() {
//    ArrayList<Object[]> result = new ArrayList<>();
//    String sql = "SELECT tensp, soluongton FROM SanPham WHERE trangthai = 1";
//    try (Connection conn = ConnectionUtils.getMyConnection();
//         PreparedStatement pst = conn.prepareStatement(sql);
//         ResultSet rs = pst.executeQuery()) {
//
//        while (rs.next()) {
//            result.add(new Object[]{rs.getString("tensp"), rs.getInt("soluongton")});
//        }
//    } catch (Exception e) {
//        Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
//    }
//    return result;
//    }
//    public ArrayList<Object[]> getSoLuongTonTheoKhuVucKho() {
//    ArrayList<Object[]> result = new ArrayList<>();
//    String sql = "SELECT khuvuckho, SUM(soluongton) as tongton FROM SanPham WHERE trangthai = 1 GROUP BY khuvuckho";
//    try (Connection conn = ConnectionUtils.getMyConnection();
//         PreparedStatement pst = conn.prepareStatement(sql);
//         ResultSet rs = pst.executeQuery()) {
//
//        while (rs.next()) {
//            result.add(new Object[]{rs.getInt("khuvuckho"), rs.getInt("tongton")});
//        }
//    } catch (Exception e) {
//        Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
//    }
//    return result;
//    }
//    public ArrayList<Object[]> getSoLuongTonTheoThuongHieu() {
//    ArrayList<Object[]> result = new ArrayList<>();
//    String sql = "SELECT thuonghieu, SUM(soluongton) as tongton FROM SanPham WHERE trangthai = 1 GROUP BY thuonghieu";
//    try (Connection conn = ConnectionUtils.getMyConnection();
//         PreparedStatement pst = conn.prepareStatement(sql);
//         ResultSet rs = pst.executeQuery()) {
//
//        while (rs.next()) {
//            result.add(new Object[]{rs.getString("thuonghieu"), rs.getInt("tongton")});
//        }
//    } catch (Exception e) {
//        Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
//    }
//    return result;
//    }
}
