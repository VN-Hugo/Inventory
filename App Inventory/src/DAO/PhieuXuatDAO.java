package DAO;

import Connection.ConnectionUtils;
import DTO.PhieuXuatDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhieuXuatDAO implements DAOInterface<PhieuXuatDTO> {

    public static PhieuXuatDAO getInstance() {
        return new PhieuXuatDAO();
    }

    @Override
    public int insert(PhieuXuatDTO t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "INSERT INTO phieuxuat (maphieuxuat, ngayxuat, manv, makhachhang, tongsanpham, trangthai) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaphieu());
            pst.setTimestamp(2, t.getThoigiantao());
            pst.setInt(3, t.getManguoitao());
            pst.setInt(4, t.getMakh());
            pst.setDouble(5, t.getTongSP());
            pst.setInt(6, t.getTrangthai()); // trạng thái mặc định là 1
            result = pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(PhieuXuatDTO t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE phieuxuat SET ngayxuat = ?, makhachhang = ?, tongsanpham = ?, trangthai = ? WHERE maphieuxuat = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, t.getThoigiantao());
            pst.setInt(2, t.getMakh());
            pst.setInt(3, t.getTongSP());
            pst.setInt(4, t.getTrangthai());
            pst.setInt(5, t.getMaphieu());
            result = pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE phieuxuat SET trangthai = 0 WHERE maphieuxuat = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<PhieuXuatDTO> selectAll() {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM phieuxuat WHERE trangthai = 1 ORDER BY maphieuxuat DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("maphieuxuat");
                Timestamp thoigiantao = rs.getTimestamp("ngayxuat");
                int makhachhang = rs.getInt("makhachhang");
                int manv = rs.getInt("manv");
                int tongsanpham = rs.getInt("tongsanpham");
                int trangthai = rs.getInt("trangthai");

                PhieuXuatDTO phieuxuat = new PhieuXuatDTO(makhachhang, maphieu, manv, thoigiantao, tongsanpham, trangthai);
                result.add(phieuxuat);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public PhieuXuatDTO selectById(String t) {
        PhieuXuatDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM phieuxuat WHERE maphieuxuat = ? AND trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int maphieu = rs.getInt("maphieuxuat");
                Timestamp thoigiantao = rs.getTimestamp("ngayxuat");
                int makhachhang = rs.getInt("makhachhang");
                int manv = rs.getInt("manv");
                int tongsanpham = rs.getInt("tongsanpham");
                int trangthai = rs.getInt("trangthai");

                result = new PhieuXuatDTO(makhachhang, maphieu, manv, thoigiantao, tongsanpham, trangthai);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        String sql = "SELECT SEQ_PHIEUXUAT.NEXTVAL FROM DUAL";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int capNhatTrangThai(int maPhieuXuat, int trangThai) {
    int result = 0;
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "UPDATE phieuxuat SET trangthai = ? WHERE maphieuxuat = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, trangThai);
        pst.setInt(2, maPhieuXuat);
        result = pst.executeUpdate();
    } catch (Exception ex) {
        Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
    }
    public ArrayList<PhieuXuatDTO> selectByTrangThai(int trangthai) {
    ArrayList<PhieuXuatDTO> result = new ArrayList<>();
    String sql = "SELECT * FROM PHIEUXUAT WHERE TRANGTHAI = ? ORDER BY MaPhieuXuat DESC";
    try (Connection con = ConnectionUtils.getMyConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, trangthai);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int maphieu = rs.getInt("MaPhieuXuat");
                Timestamp ngayxuat = rs.getTimestamp("NgayXuat");
                int manv = rs.getInt("MaNV");
                int mancc = rs.getInt("MaKhachHang");
                int tongsanpham = rs.getInt("TongSanPham");
                int trangthaiDB = rs.getInt("TrangThai");

                PhieuXuatDTO phieu = new PhieuXuatDTO(mancc, maphieu, manv, ngayxuat, tongsanpham, trangthaiDB);
                result.add(phieu);
            }
        }

    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
    }
}
