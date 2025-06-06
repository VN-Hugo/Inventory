
package DAO;

import Connection.ConnectionUtils;
import java.sql.*;
import DTO.NhanVienDTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NhanVienDAO implements DAOInterface<NhanVienDTO>{
    public static NhanVienDAO getInstance(){
        return new NhanVienDAO();
    }

    @Override
    public int insert(NhanVienDTO t) {
        int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
           String sql = "INSERT INTO nhanvien (manv, hoten, gioitinh, sdt, ngaysinh, trangthai, email) VALUES (seq_nhanvien.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getHoten());
            pst.setInt(2, t.getGioitinh());
            pst.setString(3, t.getSdt());
            pst.setDate(4, new java.sql.Date(t.getNgaysinh().getTime()));
            pst.setInt(5, t.getTrangthai());
            pst.setString(6, t.getEmail());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(NhanVienDTO t) {
        int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE nhanvien SET hoten =?, gioitinh =?,ngaysinh =?, sdt =?, trangthai =?, email =?  WHERE manv =?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getHoten());
            pst.setInt(2, t.getGioitinh());
            pst.setDate(3, new java.sql.Date(t.getNgaysinh().getTime()));
            pst.setString(4, t.getSdt());
            pst.setInt(5, t.getTrangthai());
            pst.setString(6, t.getEmail());
            pst.setInt(7, t.getManv());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "Update nhanvien set `trangthai` = -1 WHERE manv = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<NhanVienDTO> selectAll() {
        ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM NhanVien WHERE trangthai = '1'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String hoten = rs.getString("hoten");
                int gioitinh = rs.getInt("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String sdt = rs.getString("sdt");
                int trangthai = rs.getInt("trangthai");
                String email = rs.getString("email");
                NhanVienDTO nv = new NhanVienDTO(manv,hoten,gioitinh,ngaysinh,sdt,trangthai,email);
                result.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public ArrayList<NhanVienDTO> selectAlll() {
        ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM NhanVien";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String hoten = rs.getString("hoten");
                int gioitinh = rs.getInt("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String sdt = rs.getString("sdt");
                int trangthai = rs.getInt("trangthai");
                String email = rs.getString("email");
                
                NhanVienDTO nv = new NhanVienDTO(manv,hoten,gioitinh,ngaysinh,sdt,trangthai,email);
                result.add(nv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public ArrayList<NhanVienDTO> selectAllNV() {
        ArrayList<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM NhanVien nv where nv.trangthai = 1 and not EXISTS(SELECT * FROM taikhoan tk WHERE nv.manv=tk.manv)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String hoten = rs.getString("hoten");
                int gioitinh = rs.getInt("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String sdt = rs.getString("sdt");
                int trangthai = rs.getInt("trangthai");
                String email = rs.getString("email");
                NhanVienDTO nv = new NhanVienDTO(manv,hoten,gioitinh,ngaysinh,sdt,trangthai,email);
                result.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    

    @Override
    public NhanVienDTO selectById(String t) {
        NhanVienDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM NhanVien WHERE manv=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String hoten = rs.getString("hoten");
                int gioitinh = rs.getInt("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String sdt = rs.getString("sdt");
                int trangthai = rs.getInt("trangthai");
                String email = rs.getString("email");
                result = new NhanVienDTO(manv,hoten,gioitinh,ngaysinh,sdt,trangthai,email);
            };
        } catch (Exception e) {
        }
        return result;
    }
    
    public NhanVienDTO selectByEmail(String t) {
        NhanVienDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM NhanVien WHERE email=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String hoten = rs.getString("hoten");
                int gioitinh = rs.getInt("gioitinh");
                Date ngaysinh = rs.getDate("ngaysinh");
                String sdt = rs.getString("sdt");
                int trangthai = rs.getInt("trangthai");
                String email = rs.getString("email");
                result = new NhanVienDTO(manv,hoten,gioitinh,ngaysinh,sdt,trangthai,email);
            }

        } catch (Exception e) {
        }
        return result;
    }
    
   @Override
public int getAutoIncrement() {
    int result = -1;
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "SELECT seq_nhanvien.NEXTVAL FROM dual";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}   
public ArrayList<Integer> getAllMaNV() {
    ArrayList<Integer> result = new ArrayList<>();
    try {
        Connection con = ConnectionUtils.getMyConnection();
        String sql = "SELECT manv FROM NhanVien WHERE trangthai = 1";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int manv = rs.getInt("manv");
            result.add(manv);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}
}