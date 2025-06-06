package DAO;

import Connection.ConnectionUtils;
import DTO.KhuVucKhoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhuVucKhoDAO {

    public static KhuVucKhoDAO getInstance() {
        return new KhuVucKhoDAO();
    }

    // Lấy tất cả khu vực kho có trạng thái = 1 (nếu có)
    public ArrayList<KhuVucKhoDTO> selectAll() {
        ArrayList<KhuVucKhoDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM KhuVucKho";  // Thêm WHERE trangthai = 1 nếu có cột đó
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int makhuvuc = rs.getInt("makhuvuc");
                String tenkhuvuc = rs.getString("tenkhuvuc");
                String ghichu = rs.getString("ghichu");

                KhuVucKhoDTO kv = new KhuVucKhoDTO(makhuvuc, tenkhuvuc, ghichu);
                list.add(kv);
            }

        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Thêm mới khu vực kho
    public int insert(KhuVucKhoDTO kv) {
        int result = 0;
        String sql = "INSERT INTO KhuVucKho (makhuvuc, tenkhuvuc, ghichu) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, kv.getMakhuvuc());
            pst.setString(2, kv.getTenkhuvuc());
            pst.setString(3, kv.getGhichu());

            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Cập nhật khu vực kho
    public int update(KhuVucKhoDTO kv) {
        int result = 0;
        String sql = "UPDATE KhuVucKho SET tenkhuvuc = ?, ghichu = ? WHERE makhuvuc = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, kv.getTenkhuvuc());
            pst.setString(2, kv.getGhichu());
            pst.setInt(3, kv.getMakhuvuc());

            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Xóa khu vực kho theo mã
    public int delete(String makhuvuc) {
        int result = 0;
        String sql = "DELETE FROM KhuVucKho WHERE makhuvuc = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, makhuvuc);
            result = pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    // Lấy khu vực kho theo ID
    public KhuVucKhoDTO selectById(String t) {
        KhuVucKhoDTO kv = null;
        String sql = "SELECT * FROM KhuVucKho WHERE makhuvuc = ?";
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int makhuvuc = rs.getInt("makhuvuc");
                String tenkhuvuc = rs.getString("tenkhuvuc");
                String ghichu = rs.getString("ghichu");
                kv = new KhuVucKhoDTO(makhuvuc, tenkhuvuc, ghichu);
            }

        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return kv;
    }

    // Lấy mã khu vực kế tiếp từ Sequence nếu dùng Oracle
    public int getAutoIncrement() {
        int result = -1;
        String sql = "SELECT seq_khuvuc_id.NEXTVAL FROM dual";  // Giả sử bạn đã tạo sequence này
        try (Connection conn = ConnectionUtils.getMyConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (Exception e) {
            Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    public ArrayList<String> getAllTenKhuVucKho() {
    ArrayList<String> list = new ArrayList<>();
    String sql = "SELECT tenkhuvuc FROM KhuVucKho";

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            String tenKhuVuc = rs.getString("tenkhuvuc");
            list.add(tenKhuVuc);
        }

    } catch (Exception e) {
        Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
    }
    return list;
    }
    public Integer getMaKhuVucKhoByTen(String tenKhu) {
    for (KhuVucKhoDTO kv : selectAll()) {
        if (kv.getTenkhuvuc().equalsIgnoreCase(tenKhu)) {
            return kv.getMakhuvuc();
        }
    }
    return null;
    }
    public String getTenKhuVucKhoByMa(int maKho) {
    String tenKhuVuc = null;
    String sql = "SELECT tenkhuvuc FROM KhuVucKho WHERE makhuvuc = ?";

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, maKho);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            tenKhuVuc = rs.getString("tenkhuvuc");
        }

    } catch (Exception e) {
        Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
    }

    return tenKhuVuc;
    }
    

    public ArrayList<Integer> getKhuVucByPhieuNhap(int maPhieuNhap) {
    ArrayList<Integer> listMaKhuVuc = new ArrayList<>();
    String sql = """
        SELECT DISTINCT ctpn.MaKho
        FROM ChiTietPhieuNhap ctpn
        WHERE ctpn.MaPhieuNhap = ?
    """;

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        pst.setInt(1, maPhieuNhap);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            listMaKhuVuc.add(rs.getInt("MaKho"));
        }

    } catch (Exception e) {
        Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
    }

    return listMaKhuVuc;
    }
    public ArrayList<Integer> getMaKhuVucTheoMaSanPham(int maSanPham) {
    ArrayList<Integer> listMaKhuVuc = new ArrayList<>();
    String sql = "SELECT DISTINCT makhuvuc FROM TonKho WHERE masanpham = ?";

    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        
        pst.setInt(1, maSanPham);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            listMaKhuVuc.add(rs.getInt("makhuvuc"));
        }

    } catch (Exception e) {
        Logger.getLogger(KhuVucKhoDAO.class.getName()).log(Level.SEVERE, null, e);
    }

    return listMaKhuVuc;
    }

}
