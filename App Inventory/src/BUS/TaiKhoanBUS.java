
package BUS;


import DAO.TaiKhoanDAO;

import DTO.TaiKhoanDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class TaiKhoanBUS {
    
    private final TaiKhoanDAO tkDAO = new TaiKhoanDAO();
    private ArrayList<TaiKhoanDTO> listtk = new ArrayList<>();

    
    public TaiKhoanBUS(){
        try {
            this.listtk = tkDAO.selectAll();
        } catch (Exception e) {
            Logger.getLogger(TaiKhoanBUS.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public ArrayList<TaiKhoanDTO> getTaiKhoanAll(){
        return listtk;
    }
    
    public TaiKhoanDTO getTaiKhoan(int index){
        return listtk.get(index);
    }
    public int getTaiKhoanByMaNV(int manv){
         int i = 0;
        int vitri = -1;
        while (i < this.listtk.size() && vitri == -1) {
            if (listtk.get(i).getManv()== manv) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
    
     public boolean addAcc(TaiKhoanDTO tk) {
        try {
            boolean check = tkDAO.insert(tk) != 0;
            if (check) {
                this.listtk.add(tk);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(TaiKhoanBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean updateAcc(TaiKhoanDTO tk) {
        try {
            boolean check = tkDAO.update(tk) != 0;
            if (check) {
                this.listtk.set(getTaiKhoanByMaNV(tk.getManv()), tk);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(TaiKhoanBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
     public boolean deleteACC(TaiKhoanDTO tk, int index) {
        try {
            boolean check = tkDAO.delete(Integer.toString(tk.getManv())) != 0;
            if (check) {
                this.listtk.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(TaiKhoanBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public ArrayList<TaiKhoanDTO> search(String txt, String type) {
    ArrayList<TaiKhoanDTO> result = new ArrayList<>();
    txt = txt.toLowerCase();

    switch (type) {
        case "Tất cả" -> {
            for (TaiKhoanDTO i : listtk) {
                if (Integer.toString(i.getManv()).contains(txt) || i.getUsername().toLowerCase().contains(txt)) {
                    result.add(i);
                }
            }
        }
        case "Mã nhân viên" -> {
            for (TaiKhoanDTO i : listtk) {
                if (Integer.toString(i.getManv()).contains(txt)) {
                    result.add(i);
                }
            }
        }
        case "Username" -> {
            for (TaiKhoanDTO i : listtk) { 
                if (i.getUsername().toLowerCase().contains(txt)) {
                    result.add(i);
                }
            }
        }
    }
    return result;
    }
}


