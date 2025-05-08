package BUS;

import DTO.Product;
import DAO.ProductDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBUS {
    private ProductDAO productDAO;

    public ProductBUS() {
        productDAO = new ProductDAO();
    }

    public ArrayList<Product> getDanhSachSanPham() throws SQLException, ClassNotFoundException {
        return productDAO.getAllSanPham();
    }
    public void xoaSanPham(String maSP) throws SQLException, ClassNotFoundException {
    productDAO.xoaSanPham(maSP);
    }
    public void suaSanPham(Product updatedProduct) throws SQLException, ClassNotFoundException {
    productDAO.suaSanPham(updatedProduct);
    }
    public void themSanPham(Product newProduct) throws SQLException, ClassNotFoundException {
    productDAO.themSanPham(newProduct);
    }
}