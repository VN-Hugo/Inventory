//
//package BUS;
//
//
//public class TonKhoBUS {
//        private TonKhoDAO dao = new TonKhoDAO();
//
//    public boolean kiemTraDuTon(String maSP, String maKhuVuc, int soLuongCanXuat) {
//        int ton = dao.laySoLuongTon(maSP, maKhuVuc);
//        return ton >= soLuongCanXuat;
//    }
//
//    public void capNhatSauNhap(String maSP, String maKhuVuc, int soLuongNhap) {
//        dao.tangTon(maSP, maKhuVuc, soLuongNhap);
//    }
//
//    public void capNhatSauXuat(String maSP, String maKhuVuc, int soLuongXuat) {
//        dao.giamTon(maSP, maKhuVuc, soLuongXuat);
//    }
//}
