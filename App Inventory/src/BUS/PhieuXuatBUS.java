package BUS;

import DAO.ChiTietPhieuXuatDAO;
import DAO.PhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.TonKhoDTO;
import DAO.TonKhoDAO;
import DTO.PhieuXuatDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PhieuXuatBUS {

    public final PhieuXuatDAO phieuxuatDAO = new PhieuXuatDAO();
    public final ChiTietPhieuXuatDAO ctPhieuXuatDAO = new ChiTietPhieuXuatDAO();
    public final TonKhoDAO tonkhoDAO = new TonKhoDAO();
    
    NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();  

    private ArrayList<PhieuXuatDTO> listPhieuXuat = new ArrayList<>();

    public PhieuXuatBUS() {
    }

    public ArrayList<PhieuXuatDTO> getAll() {
        this.listPhieuXuat = phieuxuatDAO.selectAll();
        return listPhieuXuat;
    }
     public ArrayList<PhieuXuatDTO> getAllList() {
        return this.listPhieuXuat;
    }

    public PhieuXuatDTO getById(int maphieu) {
        for (PhieuXuatDTO phieu : listPhieuXuat) {
            if (phieu.getMaphieu() == maphieu) {
                return phieu;
            }
        }
        return null;
    }
      // Trả về chi tiết phiếu nhập
    public ArrayList<ChiTietPhieuXuatDTO> getChiTietPhieuXuat(int maphieuxuat) {
        return ctPhieuXuatDAO.selectAll(Integer.toString(maphieuxuat));
    }
    public ArrayList<ChiTietPhieuXuatDTO> getChiTietPhieu_Type(int maphieuxuat) {
        ArrayList<ChiTietPhieuXuatDTO> arr = ctPhieuXuatDAO.selectAll(Integer.toString(maphieuxuat));
        ArrayList<ChiTietPhieuXuatDTO> result = new ArrayList<>();
        for (ChiTietPhieuXuatDTO i : arr) {
            result.add(i);
    }
        return result;
    }
     public boolean add(PhieuXuatDTO phieu, ArrayList<ChiTietPhieuXuatDTO> ctPhieu) throws ClassNotFoundException {
        boolean check = phieuxuatDAO.insert(phieu) != 0;
        if (check) {
            check = ctPhieuXuatDAO.insert(ctPhieu) != 0;
        }
        return check;
    }
    
    public int getTongSoLuong(ArrayList<ChiTietPhieuXuatDTO> ctphieu) {
    int tongSoLuong = 0;
    for (ChiTietPhieuXuatDTO item : ctphieu) {
        tongSoLuong += item.getSoLuong();
    }
    return tongSoLuong;
    }

    public ChiTietPhieuXuatDTO findCT(ArrayList<ChiTietPhieuXuatDTO> ctphieu, int masp) {
    for (ChiTietPhieuXuatDTO ct : ctphieu) {
        if (ct.getMaSanPham() == masp) {
            return ct;
        }
    }
    return null;  
    }
    public int duyetPhieuXuat(int maPhieu) throws ClassNotFoundException {
    // Cập nhật trạng thái phiếu nhập (ví dụ: trạng thái 1 = Đã duyệt)
    int result = phieuxuatDAO.capNhatTrangThai(maPhieu, 1);
    
    if (result > 0) {
        // Lấy chi tiết phiếu nhập
        ArrayList<ChiTietPhieuXuatDTO> dsCT = ctPhieuXuatDAO.selectAll(String.valueOf(maPhieu));
        
        // Cập nhật tồn kho cho từng sản phẩm trong phiếu
        for (ChiTietPhieuXuatDTO ct : dsCT) {
            TonKhoDAO.getInstance().updateSoLuongTon(ct.getMaSanPham(), -ct.getSoLuong(), ct.getMaKho());
        }
    }
    return result;
    }
    public ArrayList<PhieuXuatDTO> getAllPhieuXuatChuaDuyet() {
        return phieuxuatDAO.selectByTrangThai(0);  
    }
       public boolean delete(PhieuXuatDTO ncc, int index) {
        try {
            boolean check = phieuxuatDAO.delete(Integer.toString(ncc.getMaphieu())) != 0;
            if (check) {
                this.listPhieuXuat.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    public int huyPhieuXuat(String maPhieu) {
           return phieuxuatDAO.delete(maPhieu);  // đã có sẵn
    }
}
