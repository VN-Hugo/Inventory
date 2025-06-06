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
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "INSERT INTO phieunhap(maphieunhap,ngaynhap,manv,manhacungcap,tongsanpham) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaphieu());
            pst.setTimestamp(2, t.getThoigiantao());
            pst.setInt(3, t.getManguoitao());
            pst.setInt(4, t.getManhacungcap());
            pst.setDouble(5, t.getTongSP());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(PhieuNhapDTO t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE phieunhap SET thoigian=?,manhacungcap=?,tongsanpham=? WHERE maphieunhap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setTimestamp(1, t.getThoigiantao());
            pst.setInt(2, t.getManhacungcap());
            pst.setInt(3, t.getTongSP());
            pst.setInt(4, t.getMaphieu());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "DELETE FROM PHIEUNHAP WHERE maphieunhap = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
public ArrayList<PhieuNhapDTO> selectAll() {
    ArrayList<PhieuNhapDTO> result = new ArrayList<>();
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "SELECT * FROM PhieuNhap ORDER BY MaPhieuNhap DESC";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int maphieu = rs.getInt("MaPhieuNhap");
            Timestamp thoigiantao = rs.getTimestamp("NgayNhap"); // sửa tên cột
            int mancc = rs.getInt("MaNhaCungCap");
            int nguoitao = rs.getInt("MaNV");                    // sửa tên cột
            int tongsanpham = rs.getInt("TongSanPham");
            PhieuNhapDTO phieunhap = new PhieuNhapDTO(mancc, maphieu, nguoitao, thoigiantao, tongsanpham);
            result.add(phieunhap);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}


    @Override
    public PhieuNhapDTO selectById(String t) {
        PhieuNhapDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM phieunhap WHERE maphieunhap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maphieu = rs.getInt("maphieunhap");
                Timestamp thoigiantao = rs.getTimestamp("thoigian");
                int mancc = rs.getInt("manhacungcap");
                int nguoitao = rs.getInt("nguoitao");
                int tongtien = rs.getInt("tongsanpham");
                result = new PhieuNhapDTO(mancc, maphieu, nguoitao, thoigiantao, tongtien);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}
