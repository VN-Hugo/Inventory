package DAO;

import Connection.ConnectionUtils;
import DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {
    public static NhaCungCapDAO getInstance(){
        return new NhaCungCapDAO();
    }
    public ArrayList<NhaCungCapDTO> selectAll() throws SQLException, ClassNotFoundException {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            String sql = "SELECT * FROM NhaCungCap WHERE trangthai = 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int mancc = resultSet.getInt("manhacungcap");
                String tenncc = resultSet.getString("tennhacungcap");
                String diachi = resultSet.getString("diachi");
                String email = resultSet.getString("email");
                String sdt = resultSet.getString("sdt");

                NhaCungCapDTO ncc = new NhaCungCapDTO(mancc, tenncc, diachi, email, sdt);
                result.add(ncc);
            }
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public int insert(NhaCungCapDTO ncc) throws SQLException, ClassNotFoundException {
        int result = 0;
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            String sql = "INSERT INTO NhaCungCap (manhacungcap, tennhacungcap, diachi, email, sdt, trangthai) VALUES (?, ?, ?, ?, ?, 1)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, ncc.getMancc());
            preparedStatement.setString(2, ncc.getTenncc());
            preparedStatement.setString(3, ncc.getDiachi());
            preparedStatement.setString(4, ncc.getEmail());
            preparedStatement.setString(5, ncc.getSdt());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int delete(String t) throws SQLException, ClassNotFoundException {
        int result = 0;
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            String sql = "DELETE FROM NhaCungCap WHERE manhacungcap = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, t);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int update(NhaCungCapDTO ncc) throws SQLException, ClassNotFoundException {
        int result = 0;
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            String sql = "UPDATE NhaCungCap SET tennhacungcap = ?, diachi = ?, email = ?, sdt = ? WHERE manhacungcap = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ncc.getTenncc());
            preparedStatement.setString(2, ncc.getDiachi());
            preparedStatement.setString(3, ncc.getEmail());
            preparedStatement.setString(4, ncc.getSdt());
            preparedStatement.setInt(5, ncc.getMancc());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public NhaCungCapDTO selectById(String t) {
        NhaCungCapDTO result = null;
        String sql = "SELECT * FROM NhaCungCap WHERE manhacungcap = ?";
        try (Connection con = ConnectionUtils.getMyConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, t);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int mancc = rs.getInt("manhacungcap");
                    String tenncc = rs.getString("tennhacungcap");
                    String diachi = rs.getString("diachi");
                    String email = rs.getString("email");
                    String sdt = rs.getString("sdt");
                    result = new NhaCungCapDTO(mancc, tenncc, diachi, email, sdt);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
   public int getAutoIncrement() {
    int result = -1;
    String sql = "SELECT seq_nhacungcap_id.NEXTVAL FROM dual";

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

}
