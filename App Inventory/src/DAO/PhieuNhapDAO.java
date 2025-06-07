package DAO;

import Connection.ConnectionUtils;
import DTO.PhieuNhapDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhieuNhapDAO implements DAOInterface<PhieuNhapDTO> {

    public static PhieuNhapDAO getInstance() {
        return new PhieuNhapDAO();
    }

    @Override
    public int insert(PhieuNhapDTO t) {
        int result = 0;
        String sql = "INSERT INTO PHIEUNHAP (MaPhieuNhap, NgayNhap, MaNV, MaNhaCungCap, TongSanPham, TrangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, t.getMaphieu());
            pst.setTimestamp(2, t.getThoigiantao());
            pst.setInt(3, t.getManguoitao());
            pst.setInt(4, t.getManhacungcap());
            pst.setInt(5, t.getTongSP());
            pst.setInt(6, t.getTrangthai());
            result = pst.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(PhieuNhapDTO t) {
        int result = 0;
        String sql = "UPDATE PHIEUNHAP SET NgayNhap = ?, MaNhaCungCap = ?, TongSanPham = ?, TrangThai = ? " +
                     "WHERE MaPhieuNhap = ?";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setTimestamp(1, t.getThoigiantao());
            pst.setInt(2, t.getManhacungcap());
            pst.setInt(3, t.getTongSP());
            pst.setInt(4, t.getTrangthai());
            pst.setInt(5, t.getMaphieu());
            result = pst.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String id) {
        int result = 0;
        String sql = "DELETE FROM PHIEUNHAP WHERE MaPhieuNhap = ?";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id);
            result = pst.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<PhieuNhapDTO> selectAll() {
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUNHAP WHERE TRANGTHAI = '1' ORDER BY MaPhieuNhap DESC";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int maphieu = rs.getInt("MaPhieuNhap");
                Timestamp ngaynhap = rs.getTimestamp("NgayNhap");
                int manv = rs.getInt("MaNV");
                int mancc = rs.getInt("MaNhaCungCap");
                int tongsanpham = rs.getInt("TongSanPham");
                int trangthai = rs.getInt("TrangThai");

                PhieuNhapDTO phieu = new PhieuNhapDTO(mancc, maphieu, manv, ngaynhap, tongsanpham,trangthai);
                result.add(phieu);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public PhieuNhapDTO selectById(String id) {
        PhieuNhapDTO result = null;
        String sql = "SELECT * FROM PHIEUNHAP WHERE MaPhieuNhap = ?";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int maphieu = rs.getInt("MaPhieuNhap");
                    Timestamp ngaynhap = rs.getTimestamp("NgayNhap");
                    int manv = rs.getInt("MaNV");
                    int mancc = rs.getInt("MaNhaCungCap");
                    int tongsanpham = rs.getInt("TongSanPham");
                    int trangthai = rs.getInt("TrangThai");

                    result = new PhieuNhapDTO(mancc, maphieu, manv, ngaynhap, tongsanpham,trangthai);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        String sql = "SELECT SEQ_PHIEUNHAP.NEXTVAL FROM DUAL";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int capNhatTrangThai(int maPhieuNhap, int trangThai) {
    int result = 0;
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "UPDATE phieunhap SET trangthai = ? WHERE maphieunhap = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, trangThai);
        pst.setInt(2, maPhieuNhap);
        result = pst.executeUpdate();
    } catch (Exception ex) {
        Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
    }
    public ArrayList<PhieuNhapDTO> selectByTrangThai(int trangthai) {
    ArrayList<PhieuNhapDTO> result = new ArrayList<>();
    String sql = "SELECT * FROM PHIEUNHAP WHERE TRANGTHAI = ? ORDER BY MaPhieuNhap DESC";
    try (Connection con = ConnectionUtils.getMyConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, trangthai);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int maphieu = rs.getInt("MaPhieuNhap");
                Timestamp ngaynhap = rs.getTimestamp("NgayNhap");
                int manv = rs.getInt("MaNV");
                int mancc = rs.getInt("MaNhaCungCap");
                int tongsanpham = rs.getInt("TongSanPham");
                int trangthaiDB = rs.getInt("TrangThai");

                PhieuNhapDTO phieu = new PhieuNhapDTO(mancc, maphieu, manv, ngaynhap, tongsanpham, trangthaiDB);
                result.add(phieu);
            }
        }

    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
    }
}
