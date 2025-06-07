package Utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmailSMTP {

    private static final String USERNAME = "nguyengiabao.bh2005@gmail.com";
    private static final String PASSWORD = "tmkf lbcg bjeg jlgh";

    public static void sendPhieuNhapApproved(String emailTo, String tenPhieuNhap, String nguoiDuyet, String ngayDuyet) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Thông báo duyệt phiếu nhập: " + tenPhieuNhap);

            String bodyText = "Kính gửi bộ phận tài,\n\n"
                    + "Phiếu nhập \"" + tenPhieuNhap + "\" đã được duyệt bởi " + nguoiDuyet + " vào ngày " + ngayDuyet + ".\n"
                    + "Vui lòng kiểm tra hệ thống để xem chi tiết.\n\n"
                    + "Trân trọng,\n"
                    + "Bộ phận quản lý kho";

            message.setText(bodyText);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
