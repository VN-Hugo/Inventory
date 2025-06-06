package BUS;

import DAO.SanPhamDAO;
import DAO.TonKhoDAO;
import DTO.SanPhamDTO;
import DTO.TonKhoDTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPhamBUS {

    public final SanPhamDAO spDAO = new SanPhamDAO();
    private ArrayList<SanPhamDTO> listSP = new ArrayList<>();

    public SanPhamBUS() {
        listSP = spDAO.selectAll();
    }

    public ArrayList<SanPhamDTO> getAll() {
        return this.listSP;
    }

    public SanPhamDTO getByIndex(int index) {
        return this.listSP.get(index);
    }

    public SanPhamDTO getByMaSP(int masp) {
        int vitri = -1;
        int i = 0;
        while (i < this.listSP.size() && vitri == -1) {
            if (this.listSP.get(i).getMasp() == masp) {
                vitri = i;
            } else {
                i++;
            }
        }
        return (vitri != -1) ? this.listSP.get(vitri) : null;
    }

    public int getIndexByMaSP(int masanpham) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSP.size() && vitri == -1) {
            if (listSP.get(i).getMasp() == masanpham) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(SanPhamDTO lh) {
        boolean check = spDAO.insert(lh) != 0;
        if (check) {
            this.listSP.add(lh);
        }
        return check;
    }
      public boolean delete(SanPhamDTO sp, int index) {
        try {
            boolean check = spDAO.delete(Integer.toString(sp.getMasp())) != 0;
            if (check) {
                this.listSP.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


    public Boolean update(SanPhamDTO lh) {
        boolean check = spDAO.update(lh) != 0;
        if (check) {
            this.listSP.set(getIndexByMaSP(lh.getMasp()), lh);
        }
        return check;
    }
    public ArrayList<SanPhamDTO> getSanPhamByMaKhuVuc(int maKhuVuc) {
    ArrayList<SanPhamDTO> result = new ArrayList<>();
    TonKhoDAO tonKhoDAO = TonKhoDAO.getInstance();  // Lấy instance của TonKhoDAO
    ArrayList<TonKhoDTO> listTonKho = tonKhoDAO.getTonKhoByMaKhuVuc(maKhuVuc);
    for (TonKhoDTO tk : listTonKho) {
        SanPhamDTO sp = spDAO.selectById(tk.getMaSanPham());
        if (sp != null) {
            result.add(sp);
        }
    }
    return result;
}

//    public ArrayList<SanPhamDTO> search(String txt, String type) {
//        ArrayList<SanPhamDTO> result = new ArrayList<>();
//        txt = txt.toLowerCase();
//        for (NhaCungCapDTO i : listNcc) {
//            switch (type) {
//                case "Tất cả" -> {
//                    if (Integer.toString(i.getMancc()).contains(txt) || i.getTenncc().toLowerCase().contains(txt) ||
//                        i.getDiachi().toLowerCase().contains(txt) || i.getEmail().toLowerCase().contains(txt) ||
//                        i.getSdt().toLowerCase().contains(txt)) {
//                        result.add(i);
//                    }
//                }
//                case "Mã nhà cung cấp" -> {
//                    if (Integer.toString(i.getMancc()).contains(txt)) result.add(i);
//                }
//                case "Tên nhà cung cấp" -> {
//                    if (i.getTenncc().toLowerCase().contains(txt)) result.add(i);
//                }
//                case "Địa chỉ" -> {
//                    if (i.getDiachi().toLowerCase().contains(txt)) result.add(i);
//                }
//                case "Số điện thoại" -> {
//                    if (i.getSdt().toLowerCase().contains(txt)) result.add(i);
//                }
//                case "Email" -> {
//                    if (i.getEmail().toLowerCase().contains(txt)) result.add(i);
//                }
//            }
//        }
//        return result;
//    }
    public ArrayList<SanPhamDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : this.listSP) {
            if (Integer.toString(i.getMasp()).toLowerCase().contains(text) || i.getTensp().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }
    public ArrayList<SanPhamDTO> getSanPhamCoTonKhoTheoKhuVuc(int maKhuVuc) {
    return TonKhoDAO.getInstance().getSanPhamTheoKhuVuc(maKhuVuc);
}

}
