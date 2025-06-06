package BUS;

import DAO.ChiTietPhieuXuatDAO;
import DAO.PhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.PhieuXuatDTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PhieuXuatBUS {
    
    public final PhieuXuatDAO phieuXuatDAO = new PhieuXuatDAO();

    private final ChiTietPhieuXuatDAO chiTietPhieuXuatDAO = ChiTietPhieuXuatDAO.getInstance();
     private ArrayList<PhieuXuatDTO> listPhieuXuat = new ArrayList<>();

    NhanVienBUS nvBUS = new NhanVienBUS();
    KhachHangBUS khBUS = new KhachHangBUS();
    
     public PhieuXuatBUS() {
    
     }

    public ArrayList<PhieuXuatDTO> getAll() {
        this.listPhieuXuat = phieuXuatDAO.selectAll();
        return listPhieuXuat;
    }
     public ArrayList<PhieuXuatDTO> getAllList() {
        return this.listPhieuXuat;
    }

    public PhieuXuatDTO getSelect(int index) {
        return listPhieuXuat.get(index);
    }

//    public void cancel(int px) {
//        phieuXuatDAO.cancel(px);
//    }

    public void remove(int px) {
        listPhieuXuat.remove(px);
    }

    public ArrayList<ChiTietPhieuXuatDTO> getChiTietPhieuNhap(int maphieunhap) {
        return chiTietPhieuXuatDAO.selectAll(Integer.toString(maphieunhap));
    }
     public boolean add(PhieuXuatDTO phieu, ArrayList<ChiTietPhieuXuatDTO> ctPhieu) throws ClassNotFoundException {
        boolean check = phieuXuatDAO.insert(phieu) != 0;
        if (check) {
            check = chiTietPhieuXuatDAO.insert(ctPhieu) != 0;
        }
        return check;
    }
    
    public ArrayList<ChiTietPhieuXuatDTO> selectCTP(int maphieu) {
        return chiTietPhieuXuatDAO.selectAll(Integer.toString(maphieu));
    }
    public ChiTietPhieuXuatDTO findCT(ArrayList<ChiTietPhieuXuatDTO> ctphieu, int masp) {
    for (ChiTietPhieuXuatDTO ct : ctphieu) {
        if (ct.getMaSanPham() == masp) {
            return ct;
        }
    }
    return null;  
    }
    public int getTongSoLuong(ArrayList<ChiTietPhieuXuatDTO> ctphieu) {
    int tongSoLuong = 0;
    for (ChiTietPhieuXuatDTO item : ctphieu) {
        tongSoLuong += item.getSoLuong();
    }
    return tongSoLuong;
    }


}
