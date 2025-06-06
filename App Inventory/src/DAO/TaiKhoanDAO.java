
package DAO;

import Connection.ConnectionUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import DTO.TaiKhoanDTO;




public class TaiKhoanDAO implements DAOInterface<TaiKhoanDTO>{
    
    public static TaiKhoanDAO getInstance(){
        return new TaiKhoanDAO();
    }

    @Override
    public int insert(TaiKhoanDTO t) {
        int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "INSERT INTO TaiKhoan(manv,tendangnhap,matkhau,role,trangthai) VALUES (?,?,?,?,?)";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, t.getManv());
            pst.setString(2, t.getUsername());
            pst.setString(3, t.getMatkhau());
            pst.setString(4, t.getRole());
            pst.setInt(5, t.getTrangthai());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(TaiKhoanDTO t) {
          int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE TaiKhoan SET Tendangnhap=?, Trangthai=?,role =?,matkhau=? WHERE manv=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t.getUsername());
            pst.setInt(2, t.getTrangthai());
            pst.setString(3, t.getRole());
            pst.setString(4, t.getMatkhau());
            pst.setInt(5, t.getManv());
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public TaiKhoanDTO selectByEmail(String t) {
        TaiKhoanDTO tk = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM taikhoan tk join nhanvien nv on tk.manv=nv.manv where nv.email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,t);
            ResultSet rs = pst.executeQuery();  
            while (rs.next()) {
                int manv = rs.getInt("manv");
                String tendangnhap = rs.getString("tendangnhap");
                String matkhau = rs.getString("matkhau");
                int trangthai = rs.getInt("trangthai");
                String role = rs.getString("role");
                tk = new TaiKhoanDTO(manv, tendangnhap, matkhau, role, trangthai);
                return tk;
            }
        } catch (Exception e) {
            // TODO: handle exception           
        }
        return tk;
    }
    
    public void sendOpt(String email, String opt) throws ClassNotFoundException{
        int result;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE taikhoan tk join nhanvien nv on tk.manv=nv.manv SET `otp`=? WHERE email=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, opt);
            pst.setString(2, email);
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkOtp(String email, String otp){
        boolean check = false;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM taikhoan tk join nhanvien nv on tk.manv=nv.manv where nv.email = ? and tk.otp = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, otp);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                check = true;
                return check;
            }
        } catch (Exception e) {
        }
        return check;
    }

    @Override
    public int delete(String t) {
         int result = 0 ;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "UPDATE TaiKhoan SET trangthai = -1 where manv = ?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(t));
            result = pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<TaiKhoanDTO> selectAll() {
        ArrayList<TaiKhoanDTO> result = new ArrayList<TaiKhoanDTO>();
        try {
             Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM taikhoan WHERE trangthai = '0' OR trangthai = '1'";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String username = rs.getString("tendangnhap");
                String matkhau = rs.getString("matkhau");
                String role = rs.getString("role");
                int trangthai = rs.getInt("trangthai");
                TaiKhoanDTO tk = new TaiKhoanDTO(manv, username, matkhau,role, trangthai);
                result.add(tk);
            }
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public TaiKhoanDTO selectById(String t) {
        TaiKhoanDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM taikhoan WHERE manv=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String tendangnhap = rs.getString("tendangnhap");
                String matkhau = rs.getString("matkhau");
                int trangthai = rs.getInt("trangthai");
                String role= rs.getString("role");
                TaiKhoanDTO tk = new TaiKhoanDTO(manv, tendangnhap, matkhau, role, trangthai);
                return result;
            }
        } catch (Exception e) {
        }
        return result;
    }
    
    public TaiKhoanDTO selectByUser(String t) {
        TaiKhoanDTO result = null;
        try {
            Connection con = ConnectionUtils.getMyConnection();
            String sql = "SELECT * FROM taikhoan WHERE tendangnhap=?";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while(rs.next()){
                int manv = rs.getInt("manv");
                String tendangnhap = rs.getString("tendangnhap");
                String matkhau = rs.getString("matkhau");
                int trangthai = rs.getInt("trangthai");
                String role = rs.getString("role");
                TaiKhoanDTO tk = new TaiKhoanDTO(manv, tendangnhap, matkhau,role, trangthai);
                result = tk;
            }
        } catch (Exception e) {
        }
        return result;
    }
    
    @Override
    public int getAutoIncrement() {
    int result = -1;
    String sql = "SELECT seq_taikhoan_id.NEXTVAL FROM dual";

    try (Connection con = ConnectionUtils.getMyConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        if (rs.next()) {
            result = rs.getInt(1);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
}
    
 public TaiKhoanDTO DangNhap(String username, String password) throws SQLException, ClassNotFoundException {
    TaiKhoanDTO tk = null;
    Connection con = ConnectionUtils.getMyConnection();



    String sql = "SELECT * FROM taikhoan WHERE tendangnhap = ? AND matkhau = ? AND trangthai = 1";
    PreparedStatement pst = con.prepareStatement(sql);
    pst.setString(1, username);
    pst.setString(2, password);
    ResultSet rs = pst.executeQuery();

    if (rs.next()) {
        tk = new TaiKhoanDTO(
            rs.getInt("manv"),
            rs.getString("tendangnhap"),
            rs.getString("matkhau"),
            rs.getString("role"),
            rs.getInt("trangthai")
        );
    }
    return tk;
 }
}




