package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DAO.PhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import DTO.TonKhoDTO;
import DAO.TonKhoDAO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PhieuNhapBUS {

    public final PhieuNhapDAO phieunhapDAO = new PhieuNhapDAO();
    public final ChiTietPhieuNhapDAO ctPhieuNhapDAO = new ChiTietPhieuNhapDAO();
    public final TonKhoDAO tonkhoDAO = new TonKhoDAO();
    
    NhaCungCapBUS nccBUS = new NhaCungCapBUS();
    NhanVienBUS nvBUS = new NhanVienBUS();  

    private ArrayList<PhieuNhapDTO> listPhieuNhap = new ArrayList<>();

    public PhieuNhapBUS() {
    }

    public ArrayList<PhieuNhapDTO> getAll() {
        this.listPhieuNhap = phieunhapDAO.selectAll();
        return listPhieuNhap;
    }
     public ArrayList<PhieuNhapDTO> getAllList() {
        return this.listPhieuNhap;
    }

    public PhieuNhapDTO getById(int maphieu) {
        for (PhieuNhapDTO phieu : listPhieuNhap) {
            if (phieu.getMaphieu() == maphieu) {
                return phieu;
            }
        }
        return null;
    }
      // Trả về chi tiết phiếu nhập
    public ArrayList<ChiTietPhieuNhapDTO> getChiTietPhieuNhap(int maphieunhap) {
        return ctPhieuNhapDAO.selectAll(Integer.toString(maphieunhap));
    }
    public ArrayList<ChiTietPhieuNhapDTO> getChiTietPhieu_Type(int maphieunhap) {
        ArrayList<ChiTietPhieuNhapDTO> arr = ctPhieuNhapDAO.selectAll(Integer.toString(maphieunhap));
        ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        for (ChiTietPhieuNhapDTO i : arr) {
            result.add(i);
    }
        return result;
    }
     public boolean add(PhieuNhapDTO phieu, ArrayList<ChiTietPhieuNhapDTO> ctPhieu) throws ClassNotFoundException {
        boolean check = phieunhapDAO.insert(phieu) != 0;
        if (check) {
            check = ctPhieuNhapDAO.insert(ctPhieu) != 0;
        }
        return check;
    }
    
    public int getTongSoLuong(ArrayList<ChiTietPhieuNhapDTO> ctphieu) {
    int tongSoLuong = 0;
    for (ChiTietPhieuNhapDTO item : ctphieu) {
        tongSoLuong += item.getSoLuong();
    }
    return tongSoLuong;
    }

    public ChiTietPhieuNhapDTO findCT(ArrayList<ChiTietPhieuNhapDTO> ctphieu, int masp) {
    for (ChiTietPhieuNhapDTO ct : ctphieu) {
        if (ct.getMaSanPham() == masp) {
            return ct;
        }
    }
    return null;  
    }
    public int duyetPhieuNhap(int maPhieu) throws ClassNotFoundException {
    // Cập nhật trạng thái phiếu nhập (ví dụ: trạng thái 1 = Đã duyệt)
    int result = phieunhapDAO.capNhatTrangThai(maPhieu, 1);
    
    if (result > 0) {
        // Lấy chi tiết phiếu nhập
        ArrayList<ChiTietPhieuNhapDTO> dsCT = ctPhieuNhapDAO.selectAll(String.valueOf(maPhieu));
        
        // Cập nhật tồn kho cho từng sản phẩm trong phiếu
        for (ChiTietPhieuNhapDTO ct : dsCT) {
            TonKhoDAO.getInstance().updateSoLuongTon(ct.getMaSanPham(), ct.getSoLuong(), ct.getMaKho());
        }
    }
    return result;
    }
    public ArrayList<PhieuNhapDTO> getAllPhieuNhapChuaDuyet() {
        return phieunhapDAO.selectByTrangThai(0);  
    }
       public boolean delete(PhieuNhapDTO ncc, int index) {
        try {
            boolean check = phieunhapDAO.delete(Integer.toString(ncc.getMaphieu())) != 0;
            if (check) {
                this.listPhieuNhap.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    public int huyPhieuNhap(String maPhieu) {
           return phieunhapDAO.delete(maPhieu);  // đã có sẵn
    }
}
