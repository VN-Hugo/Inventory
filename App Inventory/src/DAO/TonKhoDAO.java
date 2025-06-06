package DAO;

import Connection.ConnectionUtils;
import DTO.SanPhamDTO;
import DTO.TonKhoDTO;

import java.sql.*;
import java.util.ArrayList;

public class TonKhoDAO {

    private static TonKhoDAO instance;

    public static TonKhoDAO getInstance() {
        if (instance == null) {
            instance = new TonKhoDAO();
        }
        return instance;
    }

    public boolean updateSoLuongTon(int maSanPham, int soLuong, int maKhuVuc) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = ConnectionUtils.getMyConnection();

            // Kiểm tra tồn tại trong TONKHO
            String checkQuery = "SELECT SoLuongTon FROM TONKHO WHERE MaSanPham = ? AND MaKhuVuc = ?";
            stmt = con.prepareStatement(checkQuery);
            stmt.setInt(1, maSanPham);
            stmt.setInt(2, maKhuVuc);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int soLuongHienTai = rs.getInt("SoLuongTon");
                int soLuongMoi = soLuongHienTai + soLuong;

                if (soLuongMoi < 0) return false;

                rs.close();
                stmt.close();

                String updateQuery = "UPDATE TONKHO SET SoLuongTon = ? WHERE MaSanPham = ? AND MaKhuVuc = ?";
                stmt = con.prepareStatement(updateQuery);
                stmt.setInt(1, soLuongMoi);
                stmt.setInt(2, maSanPham);
                stmt.setInt(3, maKhuVuc);
                stmt.executeUpdate();
            } else {
                rs.close();
                stmt.close();

                String insertQuery = "INSERT INTO TONKHO (MaSanPham, SoLuongTon, MaKhuVuc) VALUES (?, ?, ?)";
                stmt = con.prepareStatement(insertQuery);
                stmt.setInt(1, maSanPham);
                stmt.setInt(2, soLuong);
                stmt.setInt(3, maKhuVuc);
                stmt.executeUpdate();
            }

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null && !rs.isClosed()) rs.close();
                if (stmt != null && !stmt.isClosed()) stmt.close();
                if (con != null && !con.isClosed()) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<TonKhoDTO> getTonKhoByMaKhuVuc(int maKhuVuc) {
        ArrayList<TonKhoDTO> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionUtils.getMyConnection();
            String sql = "SELECT MaSanPham, MaKhuVuc, SoLuongTon FROM TONKHO WHERE MaKhuVuc = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, maKhuVuc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TonKhoDTO tk = new TonKhoDTO();
                tk.setMaSanPham(rs.getString("MaSanPham"));
                tk.setMaKhuVuc(rs.getString("MaKhuVuc"));
                tk.setSoLuongTon(rs.getInt("SoLuongTon"));
                result.add(tk);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) rs.close();
                if (stmt != null && !stmt.isClosed()) stmt.close();
                if (con != null && !con.isClosed()) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public int getSoLuongTonByMaSPAndKhuVuc(int maSanPham, int maKhuVuc) {
    int soLuongTon = 0;
    String sql = "SELECT SoLuongTon FROM TonKho WHERE MaSanPham = ? AND MaKhuVuc = ?";
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, maSanPham);
        ps.setInt(2, maKhuVuc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            soLuongTon = rs.getInt("SoLuongTon");
        }
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return soLuongTon;
    }
    public Integer getMaKhuVucByMaSanPham(int maSanPham) {
    String sql = "SELECT MaKhuVuc FROM TonKho WHERE MaSanPham = ? ORDER BY NgayCapNhat DESC FETCH FIRST 1 ROWS ONLY";
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, maSanPham);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("MaKhuVuc");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // Không tìm thấy
    }
    public ArrayList<SanPhamDTO> getSanPhamByMaKhuVuc(int maKhuVuc) {
    ArrayList<SanPhamDTO> list = new ArrayList<>();
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "SELECT DISTINCT sp.masp, sp.tensp, sp.thuonghieu, sp.xuatxu " +
                     "FROM ChiTietPhieuNhap ctpn JOIN SanPham sp ON ctpn.MaSanPham = sp.masp " +
                     "WHERE ctpn.MaKhuVuc = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, maKhuVuc);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            SanPhamDTO sp = new SanPhamDTO();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setThuonghieu(rs.getString("thuonghieu"));
            sp.setXuatxu(rs.getString("xuatxu"));
            list.add(sp);
        }
        rs.close();
        ps.close();
        con.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
    return list;
    }
public ArrayList<SanPhamDTO> getSanPhamTheoKhuVuc(int maKhuVuc) {
    ArrayList<SanPhamDTO> list = new ArrayList<>();
    String sql = "SELECT sp.masp, sp.tensp, sp.thuonghieu, sp.xuatxu " +
                 "FROM TonKho tk JOIN SanPham sp ON tk.MaSanPham = sp.masp " +
                 "WHERE tk.MaKhuVuc = ?";

    try (Connection con = ConnectionUtils.getMyConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, maKhuVuc);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SanPhamDTO sp = new SanPhamDTO();
            sp.setMasp(rs.getInt("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setThuonghieu(rs.getString("thuonghieu"));
            sp.setXuatxu(rs.getString("xuatxu"));
            // Không setSoLuongTon nữa
            list.add(sp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    public int getTongSoLuongTonByMaSanPham(int maSanPham) {
    int tongSoLuongTon = 0;
    String sql = "SELECT SUM(SoLuongTon) AS TongSoLuong FROM TonKho WHERE MaSanPham = ?";

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, maSanPham);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            tongSoLuongTon = rs.getInt("TongSoLuong");
        }
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return tongSoLuongTon;
    }
    public boolean capNhatSoLuongThucTe(int maSP, int soLuongTon, int maKhuVuc) {
    String sql = "UPDATE TONKHO SET SoLuongTon = ? WHERE MaSanPham = ? AND MaKhuVuc = ?";

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, soLuongTon);
        stmt.setInt(2, maSP);
        stmt.setInt(3, maKhuVuc);

        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace(); // Nên thay bằng logger nếu dùng log framework
        return false;
    }
    }

}

