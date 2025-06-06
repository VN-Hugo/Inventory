package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class NhanVienBUS {

    private final NhanVienDAO nvDAO = new NhanVienDAO();
    public ArrayList<NhanVienDTO> listNv = new ArrayList<>();

     public NhanVienBUS() {
        try {
            this.listNv = nvDAO.selectAll();
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<DTO.NhanVienDTO> getAll() {
        return this.listNv;
    }

    public NhanVienDTO getByIndex(int index) {
        return this.listNv.get(index);
    }
    public int getIndexById(int manv) {
        int i = 0;
        int vitri = -1;
        int size = this.listNv.size();
        while (i < size && vitri == -1) {
            if (this.listNv.get(i).getManv() == manv) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
    
    public boolean add(NhanVienDTO nv) {
        try {
            boolean check = nvDAO.insert(nv) != 0;
            if (check) {
                this.listNv.add(nv);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    public boolean update(NhanVienDTO nv) {
        try {
            boolean check = nvDAO.update(nv) != 0;
            if (check) {
                this.listNv.set(getIndexByMaNV(nv.getManv()), nv);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    public int getIndexByMaNV(int manv) {
        for (int i = 0; i < this.listNv.size(); i++) {
            if (listNv.get(i).getManv() == manv) {
                return i;
            }
        }
        return -1;
    }
    public String getNameById(int manv) {
        return nvDAO.selectById(manv+"").getHoten();
    }

    public String[] getArrTenNhanVien() {
        return listNv.stream().map(NhanVienDTO::getHoten).toArray(String[]::new);
    }

     public void insertNv(NhanVienDTO nv) {
        listNv.add(nv);
    }

    public void updateNv(int index, NhanVienDTO nv) {
        listNv.set(index, nv);
    }
    
    public ArrayList<NhanVienDTO> search(String txt, String type) {
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        for (NhanVienDTO i : listNv) {
            switch (type) {
                case "Tất cả" -> {
                    if (Integer.toString(i.getManv()).contains(txt) || i.getHoten().toLowerCase().contains(txt) ||
                        i.getSdt().toLowerCase().contains(txt) || i.getEmail().toLowerCase().contains(txt)) 
                        {
                        result.add(i);
                    }
                }
                case "Mã nhà cung cấp" -> {
                    if (Integer.toString(i.getManv()).contains(txt)) result.add(i);
                }
                case "Tên nhân viên" -> {
                    if (i.getHoten().toLowerCase().contains(txt)) result.add(i);
                }
                case "Số điện thoại" -> {
                    if (i.getSdt().toLowerCase().contains(txt)) result.add(i);
                }
                case "Email" -> {
                    if (i.getEmail().toLowerCase().contains(txt)) result.add(i);
                }
            }
        }
        return result;
    }
    public boolean delete(NhanVienDTO ncc, int index) {
        try {
            boolean check = nvDAO.delete(Integer.toString(ncc.getManv())) != 0;
            if (check) {
                this.listNv.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

}
