/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapBUS {

    private final NhaCungCapDAO NccDAO = new NhaCungCapDAO();
    private ArrayList<NhaCungCapDTO> listNcc = new ArrayList<>();

    public NhaCungCapBUS() {
        try {
            this.listNcc = NccDAO.selectAll();
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ArrayList<NhaCungCapDTO> getAll() {
        return this.listNcc;
    }

    public NhaCungCapDTO getByIndex(int index) {
        return this.listNcc.get(index);
    }

    public boolean add(NhaCungCapDTO ncc) {
        try {
            boolean check = NccDAO.insert(ncc) != 0;
            if (check) {
                this.listNcc.add(ncc);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean delete(NhaCungCapDTO ncc, int index) {
        try {
            boolean check = NccDAO.delete(Integer.toString(ncc.getMancc())) != 0;
            if (check) {
                this.listNcc.remove(index);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean update(NhaCungCapDTO ncc) {
        try {
            boolean check = NccDAO.update(ncc) != 0;
            if (check) {
                this.listNcc.set(getIndexByMaNCC(ncc.getMancc()), ncc);
            }
            return check;
        } catch (Exception e) {
            Logger.getLogger(NhaCungCapBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public int getIndexByMaNCC(int mancc) {
        for (int i = 0; i < this.listNcc.size(); i++) {
            if (listNcc.get(i).getMancc() == mancc) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<NhaCungCapDTO> search(String txt, String type) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
            for (NhaCungCapDTO i : listNcc) {
            switch (type) {
                case "Tất cả" -> {
                    if (Integer.toString(i.getMancc()).contains(txt) || i.getTenncc().toLowerCase().contains(txt) ||
                        i.getDiachi().toLowerCase().contains(txt) || i.getEmail().toLowerCase().contains(txt) ||
                        i.getSdt().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
                case "Mã nhà cung cấp" -> {
                    if (Integer.toString(i.getMancc()).contains(txt)) result.add(i);
                }
                case "Tên nhà cung cấp" -> {
                    if (i.getTenncc().toLowerCase().contains(txt)) result.add(i);
                }
                case "Địa chỉ" -> {
                    if (i.getDiachi().toLowerCase().contains(txt)) result.add(i);
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

    public String[] getArrTenNhaCungCap() {
        return listNcc.stream().map(NhaCungCapDTO::getTenncc).toArray(String[]::new);
    }

    public String getTenNhaCungCap(int mancc) {
        int index = getIndexByMaNCC(mancc);
        return index >= 0 ? listNcc.get(index).getTenncc() : null;
    }

    public NhaCungCapDTO findCT(ArrayList<NhaCungCapDTO> nccList, String tenncc) {
        return nccList.stream()
                .filter(ncc -> ncc.getTenncc().equals(tenncc))
                .findFirst()
                .orElse(null);
    }
}
