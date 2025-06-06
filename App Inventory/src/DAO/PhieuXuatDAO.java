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
            String sql = "INSERT INTO phieuxuat(maphieuxuat, ngayxuat, manv, makhachhang, tongsanpham) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaphieu());
            pst.setTimestamp(2, t.getThoigiantao());
            pst.setInt(3, t.getManguoitao());
            pst.setInt(4, t.getMakh());
            pst.setDouble(5, t.getTongSP());
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
            String sql = "UPDATE phieuxuat SET ngayxuat=?, makhachhang=?, tongsanpham=? WHERE maphieuxuat=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, t.getThoigiantao());
            pst.setInt(2, t.getMakh());
            pst.setInt(3, t.getTongSP());
            pst.setInt(4, t.getMaphieu());
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
            String sql = "DELETE FROM phieuxuat WHERE maphieuxuat = ?";
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
            String sql = "SELECT * FROM phieuxuat ORDER BY maphieuxuat DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("maphieuxuat");
                Timestamp thoigiantao = rs.getTimestamp("ngayxuat");
                int makhachhang = rs.getInt("makhachhang");
                int manv = rs.getInt("manv");
                int tongsanpham = rs.getInt("tongsanpham");
                PhieuXuatDTO phieuxuat = new PhieuXuatDTO(makhachhang, maphieu, manv, thoigiantao, tongsanpham);
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
            String sql = "SELECT * FROM phieuxuat WHERE maphieuxuat=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int maphieu = rs.getInt("maphieuxuat");
                Timestamp thoigiantao = rs.getTimestamp("ngayxuat");
                int makhachhang = rs.getInt("makhachhang");
                int manv = rs.getInt("manv");
                int tongsanpham = rs.getInt("tongsanpham");
                result = new PhieuXuatDTO(makhachhang, maphieu, manv, thoigiantao, tongsanpham);
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
}
