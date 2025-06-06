package DAO;

import Connection.ConnectionUtils;
import DTO.Product;
import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {

    public ArrayList<Product> getAllSanPham() throws SQLException, ClassNotFoundException {
        ArrayList<Product> danhSachSanPham = new ArrayList<>();
        try (Connection conn = ConnectionUtils.getMyConnection()) {
            String sql = "SELECT * FROM SANPHAM";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String maSP = resultSet.getString("MASP");
                    String tenSP = resultSet.getString("TENSP");
                    String donViTinh = resultSet.getString("DONVITINH");
                    int soLuong = resultSet.getInt("SOLUONG");
                    double donGia = resultSet.getDouble("DONGIA");
                    String ngaySX = resultSet.getString("NGAYSX");
                    String hangSX = resultSet.getString("HANGSX");
                    Product sp = new Product(maSP, tenSP, donViTinh, soLuong, donGia, ngaySX, hangSX);
                    danhSachSanPham.add(sp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSanPham;
    }
    
    public void themSanPham(Product newProduct) throws SQLException, ClassNotFoundException {
    String sql = "INSERT INTO SANPHAM (MASP, TENSP, DONVITINH, SOLUONG, DONGIA, NGAYSX, HANGSX) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setString(1, newProduct.getMaSP());
        statement.setString(2, newProduct.getTenSP());
        statement.setString(3, newProduct.getDonViTinh());
        statement.setInt(4, newProduct.getSoLuong());
        statement.setDouble(5, newProduct.getDonGia());
        statement.setString(6, newProduct.getNgaySX());
        statement.setString(7, newProduct.getHangSX());
        
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Reroute the exception if needed
    }
    }

    
    public void suaSanPham(Product updatedProduct) throws SQLException, ClassNotFoundException {
    String sql = "UPDATE SANPHAM SET TENSP = ?, DONVITINH = ?, SOLUONG = ?, DONGIA = ?, NGAYSX = ?, HANGSX = ? WHERE MASP = ?";
    
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setString(1, updatedProduct.getTenSP());
        statement.setString(2, updatedProduct.getDonViTinh());
        statement.setInt(3, updatedProduct.getSoLuong());
        statement.setDouble(4, updatedProduct.getDonGia());
        statement.setString(5, updatedProduct.getNgaySX());
        statement.setString(6, updatedProduct.getHangSX());
        statement.setString(7, updatedProduct.getMaSP()); // Đảm bảo cập nhật dựa trên MASP
        
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Reroute the exception if needed
        }
    }

    public void xoaSanPham(String maSP) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM SANPHAM WHERE MASP = ?";
    
    try (Connection conn = ConnectionUtils.getMyConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setString(1, maSP);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; 
    }
    }
}
