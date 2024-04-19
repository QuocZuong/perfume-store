package Lib;

import DAOs.CustomerDAO;
import DAOs.OrderDetailDao;
import DAOs.ProductDAO;
import Exceptions.ProductNotFoundException;
import Models.Customer;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.User;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.codec.binary.Base64;

import com.google.api.services.gmail.model.Message;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.client.json.JsonFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    private PropsLoader propsLoader;
    private final boolean DEBUG = true;
    private String account; 
    
    public EmailSender() {
      propsLoader = new PropsLoader();
      account = propsLoader.getBotUserName();
    }

    // public final String GENERATE_PASSWORD_SUBJECT = "Your account at XXVI Store
    // has been created!";
    public final String GENERATE_PASSWORD_SUBJECT = "Tài khoản của bạn đã được tạo tại trang web XXVI Store";
    public final String CHANGE_PASSWORD_NOTFICATION = "[XXVI STORE] Mật khẩu đã thay đổi";
    public final String CHANGE_EMAIL_NOTFICATION = "[XXVI STORE] Địa chỉ email đã đổi";
    public final String CHANGE_USERNAME_NOTFICATION = "[XXVI STORE] Username đã đổi";
    public final String FORGOT_PASSWORD_NOTFICATION = "[XXVI STORE] Mật khẩu tạm thời đã được tạo";
    public final String EXPORT_RECEIPT_NOTFICATION = "[XXVI STORE] Xuất hoá đơn thành công";

    private String EmailTo;

    public void sendEmailByThread(final String subject, final String html) throws UnsupportedEncodingException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (DEBUG) System.out.println("Sending email to " + EmailTo);
                    sendEmail(subject, html);
                } catch (Exception ex) {
                  System.out.println(ex);
                }
            }
        });

        thread.start();
    }

    private boolean sendEmail(String subject, String html) throws UnsupportedEncodingException {
          try {
            JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
            HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Credential CRED = OAuth2.getCredential(JSON_FACTORY, HTTP_TRANSPORT);
      
            // Create gmail API client
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, CRED)
                .setApplicationName(OAuth2.APP_NAME)
                .build();

            // Create email
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(account)); // Set from address of the email
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EmailTo)); // Set email recipient
            message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B")); // Set email message subject
            message.setContent(html, "text/html; charset=utf-8");
            message.saveChanges();
            
            // Encode and create a gmail message
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            message.writeTo(buffer);

            byte[] messageBytes = buffer.toByteArray();
            String encodedMessage = Base64.encodeBase64URLSafeString(messageBytes);

            Message gmailMessage = new Message();
            gmailMessage.setRaw(encodedMessage);

            // Send the message
            if (DEBUG) System.out.println("Sending email to " + EmailTo);

            gmailMessage = service.users().messages().send("me", gmailMessage).execute();
            
            System.out.println("sent email successfully!");
            System.out.println("Message id: " + gmailMessage.getId());
            
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String generatePasswordEmailHTML(String generatePassword) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ClientURL = "http://localhost:8080/Customer/User";

        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "	<head>\n"
                + "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "		<title>XXVI STORE</title>\n"
                + "	</head>\n"
                + "	<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "		<div id=\"wrapper\" dir=\"ltr\" style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "				<tr>\n"
                + "					<td align=\"center\" valign=\"top\">\n"
                + "						<div id=\"template_header_image\">\n"
                + "													</div>\n"
                + "						<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\" style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "							<tr>\n"
                + "								<td align=\"center\" valign=\"top\">\n"
                + "									<!-- Header -->\n"
                + "									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\" style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "										<tr>\n"
                + "											<td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "												<h1 style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>Chào mừng tới XXVI STORE</h1>\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "									<!-- End Header -->\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "							<tr>\n"
                + "								<td align=\"center\" valign=\"top\">\n"
                + "									<!-- Body -->\n"
                + "									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "										<tr>\n"
                + "											<td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "												<!-- Content -->\n"
                + "												<table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "													<tr>\n"
                + "														<td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "															<div id=\"body_content_inner\" style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "<p style=\"margin: 0 0 16px;\">Xin chào "
                + username
                + ",</p>\n"
                + "<p style=\"margin: 0 0 16px;\">Cảm ơn bạn đã tạo tài khoản ở XXVI STORE. Tên tài khoản của bạn là <strong>"
                + username
                + "</strong>. Bạn có thể truy cập trang tài khoản để xem đơn hàng, đổi mật khẩu, và nhiều thứ khác tại: <a href=\""
                + ClientURL + "\" style=\"color: #2c7596; font-weight: normal; text-decoration: underline;\">"
                + ClientURL + "</a></p>\n"
                + "		<p style=\"margin: 0 0 16px;\">Mật khẩu của bạn đã được tạo tự động: <strong>"
                + generatePassword + "</strong></p>\n"
                + "\n"
                + "<p style=\"margin: 0 0 16px;\">We look forward to seeing you soon.</p>\n"
                + "															</div>\n"
                + "														</td>\n"
                + "													</tr>\n"
                + "												</table>\n"
                + "												<!-- End Content -->\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "									<!-- End Body -->\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "						</table>\n"
                + "					</td>\n"
                + "				</tr>\n"
                + "				<tr>\n"
                + "					<td align=\"center\" valign=\"top\">\n"
                + "						<!-- Footer -->\n"
                + "						<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "							<tr>\n"
                + "								<td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "									<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "										<tr>\n"
                + "											<td colspan=\"2\" valign=\"middle\" id=\"credit\" style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "												<p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "						</table>\n"
                + "						<!-- End Footer -->\n"
                + "					</td>\n"
                + "				</tr>\n"
                + "			</table>\n"
                + "		</div>\n"
                + "	</body>\n"
                + "</html>";
        return html;
    }

    public String forgotPasswordEmailHTML(String newPassword) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));

        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "	<head>\n"
                + "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "		<title>XXVI STORE</title>\n"
                + "	</head>\n"
                + "	<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "		<div id=\"wrapper\" dir=\"ltr\" style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "				<tr>\n"
                + "					<td align=\"center\" valign=\"top\">\n"
                + "						<div id=\"template_header_image\">\n"
                + "													</div>\n"
                + "						<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\" style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "							<tr>\n"
                + "								<td align=\"center\" valign=\"top\">\n"
                + "									<!-- Header -->\n"
                + "									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\" style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "										<tr>\n"
                + "											<td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "												<h1 style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>Mật khẩu tạm thời đã được tạo</h1>\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "									<!-- End Header -->\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "							<tr>\n"
                + "								<td align=\"center\" valign=\"top\">\n"
                + "									<!-- Body -->\n"
                + "									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "										<tr>\n"
                + "											<td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "												<!-- Content -->\n"
                + "												<table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "													<tr>\n"
                + "														<td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "															<div id=\"body_content_inner\" style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "<p style=\"margin: 0 0 16px;\">Xin chào "
                + username
                + ",</p>\n"
                + "<p style=\"margin: 0 0 16px;\">Lấy lại mật khẩu thành công XXVI STORE. Mật khẩu tạm thời cho tài khoản <strong>"
                + username
                + "</strong> của bạn là <strong>" + newPassword + "</strong> . Bạn có thể dùng mật khẩu tạm thời này để đăng nhập vào XXIV STORE."
                + "\n"
                + "<p style=\"margin: 0 0 16px;\">Sau khi đăng nhập vui lòng thay đổi mật khẩu.</p>\n"
                + "															</div>\n"
                + "														</td>\n"
                + "													</tr>\n"
                + "												</table>\n"
                + "												<!-- End Content -->\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "									<!-- End Body -->\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "						</table>\n"
                + "					</td>\n"
                + "				</tr>\n"
                + "				<tr>\n"
                + "					<td align=\"center\" valign=\"top\">\n"
                + "						<!-- Footer -->\n"
                + "						<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "							<tr>\n"
                + "								<td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "									<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "										<tr>\n"
                + "											<td colspan=\"2\" valign=\"middle\" id=\"credit\" style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "												<p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "											</td>\n"
                + "										</tr>\n"
                + "									</table>\n"
                + "								</td>\n"
                + "							</tr>\n"
                + "						</table>\n"
                + "						<!-- End Footer -->\n"
                + "					</td>\n"
                + "				</tr>\n"
                + "			</table>\n"
                + "		</div>\n"
                + "	</body>\n"
                + "</html>";
        return html;
    }

    public String changePasswordNotifcation(User user) {
        String email = EmailTo;
        String username = user.getUsername();
        String ShopURL = "http://localhost:8080";

        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "\n"
                + "<head>\n"
                + "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "	<title>XXVI STORE</title>\n"
                + "</head>\n"
                + "\n"
                + "<body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "	<div id=\"wrapper\" dir=\"ltr\"\n"
                + "		style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "			<tr>\n"
                + "				<td align=\"center\" valign=\"top\">\n"
                + "					<div id=\"template_header_image\">\n"
                + "					</div>\n"
                + "					<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\"\n"
                + "						style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "						<tr>\n"
                + "							<td align=\"center\" valign=\"top\">\n"
                + "								<!-- Header -->\n"
                + "								<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\"\n"
                + "									style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "									<tr>\n"
                + "										<td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "											<h1\n"
                + "												style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>\n"
                + "												MẬT KHẨU CỦA BẠN ĐÃ BỊ THAY ĐỔI</h1>\n"
                + "										</td>\n"
                + "									</tr>\n"
                + "								</table>\n"
                + "								<!-- End Header -->\n"
                + "							</td>\n"
                + "						</tr>\n"
                + "						<tr>\n"
                + "							<td align=\"center\" valign=\"top\">\n"
                + "								<!-- Body -->\n"
                + "								<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "									<tr>\n"
                + "										<td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "											<!-- Content -->\n"
                + "											<table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "												<tr>\n"
                + "													<td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "														<div id=\"body_content_inner\"\n"
                + "															style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "															<p style=\"margin: 0 0 16px;\">Xin chào " + username + ",</p>\n"
                + "															<p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng mật khẩu của bạn đã được thay đổi\n"
                + "																trên XXVI STORE. Nếu bạn không thay đổi mật khẩu, vui lòng liên hệ người quản trị\n"
                + "																website qua email <a href=\"mailto:"
                + account + "\">" + account + "</a>.\n"
                + "															</p>\n"
                + "															<p style=\"margin: 0 0 16px;\">Email này đã được gửi đến <a\n"
                + "																	href=\"mailto:" + email + "\">" + email + "</a></strong></p>\n"
                + "\n"
                + "															<p style=\"margin: 0 0 16px;\">Thân ái,\n"
                + "																<br>XXVI STORE\n"
                + "																<br><a href=\"" + ShopURL + "\">"
                + ShopURL + "</a>\n"
                + "															</p>\n"
                + "														</div>\n"
                + "													</td>\n"
                + "												</tr>\n"
                + "											</table>\n"
                + "											<!-- End Content -->\n"
                + "										</td>\n"
                + "									</tr>\n"
                + "								</table>\n"
                + "								<!-- End Body -->\n"
                + "							</td>\n"
                + "						</tr>\n"
                + "					</table>\n"
                + "				</td>\n"
                + "			</tr>\n"
                + "			<tr>\n"
                + "				<td align=\"center\" valign=\"top\">\n"
                + "					<!-- Footer -->\n"
                + "					<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "						<tr>\n"
                + "							<td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "								<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "									<tr>\n"
                + "										<td colspan=\"2\" valign=\"middle\" id=\"credit\"\n"
                + "											style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "											<p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "										</td>\n"
                + "									</tr>\n"
                + "								</table>\n"
                + "							</td>\n"
                + "						</tr>\n"
                + "					</table>\n"
                + "					<!-- End Footer -->\n"
                + "				</td>\n"
                + "			</tr>\n"
                + "		</table>\n"
                + "	</div>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        return html;
    }

    public String changeEmailNotification(String newEmail) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ShopURL = "http://localhost:8080";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "        <title>XXIV STORE</title>\n"
                + "    </head>\n"
                + "\n"
                + "    <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "        <div id=\"wrapper\" dir=\"ltr\"\n"
                + "             style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <div id=\"template_header_image\">\n"
                + "                        </div>\n"
                + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\"\n"
                + "                               style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Header -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\"\n"
                + "                                           style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "                                        <tr>\n"
                + "                                            <td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "                                                <h1\n"
                + "                                                    style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>\n"
                + "                                                    ĐỊA CHỈ EMAIL ĐÃ BỊ THAY ĐỔI</h1>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Header -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Body -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "                                        <tr>\n"
                + "                                            <td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "                                                <!-- Content -->\n"
                + "                                                <table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                                    <tr>\n"
                + "                                                        <td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "                                                            <div id=\"body_content_inner\"\n"
                + "                                                                 style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Xin chào " + username + ",</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng email của bạn trên XXIV STORE đã được thay đổi thành <strong><a href=\"mailto:" + newEmail + "\">" + newEmail + "</a></strong>.\n"
                + "                                                                </p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Nếu bạn không thay đổi email, vui lòng liên hệ Quản trị trang web tại <a href=\"mailto:" + account + "\">" + account + "</a></strong>.</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thân ái,\n"
                + "                                                                    <br>XXVI STORE\n"
                + "                                                                    <br><a href=\"" + ShopURL + "\">" + ShopURL + "</a>\n"
                + "                                                                </p>\n"
                + "                                                            </div>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "                                                <!-- End Content -->\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Body -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <!-- Footer -->\n"
                + "                        <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "                            <tr>\n"
                + "                                <td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                        <tr>\n"
                + "                                            <td colspan=\"2\" valign=\"middle\" id=\"credit\"\n"
                + "                                                style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "                                                <p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                        <!-- End Footer -->\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "\n"
                + "</html>";

        return html;
    }

    public String changeUsernameNotification(String newUsername) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ShopURL = "http://localhost:8080";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "        <title>XXIV STORE</title>\n"
                + "    </head>\n"
                + "\n"
                + "    <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "        <div id=\"wrapper\" dir=\"ltr\"\n"
                + "             style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <div id=\"template_header_image\">\n"
                + "                        </div>\n"
                + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\"\n"
                + "                               style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Header -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\"\n"
                + "                                           style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "                                        <tr>\n"
                + "                                            <td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "                                                <h1\n"
                + "                                                    style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>\n"
                + "                                                   USERNAME ĐÃ BỊ THAY ĐỔI</h1>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Header -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Body -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "                                        <tr>\n"
                + "                                            <td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "                                                <!-- Content -->\n"
                + "                                                <table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                                    <tr>\n"
                + "                                                        <td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "                                                            <div id=\"body_content_inner\"\n"
                + "                                                                 style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Xin chào " + username + ",</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng username của bạn trên XXIV STORE đã được thay đổi thành <strong>" + newUsername + "</strong>.\n"
                + "                                                                </p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Nếu bạn không thay đổi username, vui lòng liên hệ Quản trị trang web tại <a href=\"mailto:" + account + "\">" + account + "</a></strong>.</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thân ái,\n"
                + "                                                                    <br>XXVI STORE\n"
                + "                                                                    <br><a href=\"" + ShopURL + "\">" + ShopURL + "</a>\n"
                + "                                                                </p>\n"
                + "                                                            </div>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "                                                <!-- End Content -->\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Body -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <!-- Footer -->\n"
                + "                        <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "                            <tr>\n"
                + "                                <td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                        <tr>\n"
                + "                                            <td colspan=\"2\" valign=\"middle\" id=\"credit\"\n"
                + "                                                style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "                                                <p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                        <!-- End Footer -->\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "\n"
                + "</html>";

        return html;
    }

    public String getEmailSubscribe() {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ShopURL = "http://localhost:8080";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"vi\">\n"
                + "\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "        <title>XXIV STORE</title>\n"
                + "    </head>\n"
                + "\n"
                + "    <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\" style=\"padding: 0;\">\n"
                + "        <div id=\"wrapper\" dir=\"ltr\"\n"
                + "             style=\"background-color: #f7f7f7; margin: 0; padding: 70px 0; width: 100%; -webkit-text-size-adjust: none;\">\n"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <div id=\"template_header_image\">\n"
                + "                        </div>\n"
                + "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_container\"\n"
                + "                               style=\"background-color: #ffffff; border: 1px solid #dedede; box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1); border-radius: 3px;\">\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Header -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" id=\"template_header\"\n"
                + "                                           style='background-color: #000000db; color: #ffffff; border-bottom: 0; font-weight: bold; line-height: 100%; vertical-align: middle; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; border-radius: 3px 3px 0 0;'>\n"
                + "                                        <tr>\n"
                + "                                            <td id=\"header_wrapper\" style=\"padding: 36px 48px; display: block;\">\n"
                + "                                                <h1\n"
                + "                                                    style='font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 30px; font-weight: 300; line-height: 150%; margin: 0; text-align: left; text-shadow: 0 1px 0 #5691ab; color: #ffffff; background-color: inherit;'>\n"
                + "                                                   ĐĂNG KÝ NHẬN TIN TỪ XXVI STORE THÀNH CÔNG!</h1>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Header -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\">\n"
                + "                                    <!-- Body -->\n"
                + "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"template_body\">\n"
                + "                                        <tr>\n"
                + "                                            <td valign=\"top\" id=\"body_content\" style=\"background-color: #ffffff;\">\n"
                + "                                                <!-- Content -->\n"
                + "                                                <table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                                    <tr>\n"
                + "                                                        <td valign=\"top\" style=\"padding: 48px 48px 32px;\">\n"
                + "                                                            <div id=\"body_content_inner\"\n"
                + "                                                                 style='color: #636363; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 14px; line-height: 150%; text-align: left;'>\n"
                + "\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Xin chào " + username + ",</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng bạn đã thành công nhận tin từ XXIV STORE\n"
                + "                                                                </p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Mọi tin tức và khuyến mãi sẽ được chúng mình gửi đến bạn sớm nhất có thể.</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thân ái,\n"
                + "                                                                    <br>XXVI STORE\n"
                + "                                                                    <br><a href=\"" + ShopURL + "\">" + ShopURL + "</a>\n"
                + "                                                                </p>\n"
                + "                                                            </div>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "                                                <!-- End Content -->\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                    <!-- End Body -->\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr>\n"
                + "                    <td align=\"center\" valign=\"top\">\n"
                + "                        <!-- Footer -->\n"
                + "                        <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"template_footer\">\n"
                + "                            <tr>\n"
                + "                                <td valign=\"top\" style=\"padding: 0; border-radius: 6px;\">\n"
                + "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\">\n"
                + "                                        <tr>\n"
                + "                                            <td colspan=\"2\" valign=\"middle\" id=\"credit\"\n"
                + "                                                style='border-radius: 6px; border: 0; color: #8a8a8a; font-family: \"Helvetica Neue\", Helvetica, Roboto, Arial, sans-serif; font-size: 12px; line-height: 150%; text-align: center; padding: 24px 0;'>\n"
                + "                                                <p style=\"margin: 0 0 16px;\">XXVI STORE</p>\n"
                + "                                            </td>\n"
                + "                                        </tr>\n"
                + "                                    </table>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                        <!-- End Footer -->\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "\n"
                + "</html>";

        return html;
    }

    public String exportEmailReceipt(Order order) throws ProductNotFoundException {
        CustomerDAO cusDAO = new CustomerDAO();
        OrderDetailDao orderDetailDao = new OrderDetailDao();
        ProductDAO productDAO = new ProductDAO();
        Customer customer = cusDAO.getCustomer(order.getCustomerId());
        List<OrderDetail> oDetailList = orderDetailDao.getOrderDetail(order.getId());

        StringBuilder htmlBuilder = new StringBuilder();

        for (int i = 0; i < oDetailList.size(); i++) {
            OrderDetail orderDetail = oDetailList.get(i);
            Product product = productDAO.getProduct(orderDetail.getProductId());
            htmlBuilder.append(Generator.generateProductHTML(product.getImgURL(), product.getName(), orderDetail.getQuantity(), orderDetail.getTotal()));
        }

        String generatedHtml = htmlBuilder.toString();

        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\"><head><meta charset=\"UTF-8\"><meta content=\"width=device-width,initial-scale=1\" name=\"viewport\"><meta name=\"x-apple-disable-message-reformatting\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta content=\"telephone=no\" name=\"format-detection\"><title>Receipt</title><!--[if (mso 16)]><style type=\"text/css\">a{text-decoration:none}</style><![endif]--><!--[if gte mso 9\n"
                + "            ]><style>\n"
                + "                sup {\n"
                + "                    font-size: 100% !important;\n"
                + "                }\n"
                + "            </style><!\n"
                + "        [endif]--><!--[if gte mso 9]><xml><o:officedocumentsettings><o:allowpng></o:allowpng><o:pixelsperinch>96</o:pixelsperinch></o:officedocumentsettings></xml><![endif]--><style type=\"text/css\">#outlook a{padding:0}.es-button{mso-style-priority:100!important;text-decoration:none!important}a[x-apple-data-detectors]{color:inherit!important;text-decoration:none!important;font-size:inherit!important;font-family:inherit!important;font-weight:inherit!important;line-height:inherit!important}.es-desk-hidden{display:none;float:left;overflow:hidden;width:0;max-height:0;line-height:0;mso-hide:all}@media only screen and (max-width:600px){a,ol li,p,ul li{line-height:150%!important}h1,h1 a,h2,h2 a,h3,h3 a{line-height:120%!important}h1{font-size:36px!important;text-align:left}h2{font-size:26px!important;text-align:left}h3{font-size:20px!important;text-align:left}.es-content-body h1 a,.es-footer-body h1 a,.es-header-body h1 a{font-size:36px!important;text-align:left}.es-content-body h2 a,.es-footer-body h2 a,.es-header-body h2 a{font-size:26px!important;text-align:left}.es-content-body h3 a,.es-footer-body h3 a,.es-header-body h3 a{font-size:20px!important;text-align:left}.es-menu td a{font-size:12px!important}.es-header-body a,.es-header-body ol li,.es-header-body p,.es-header-body ul li{font-size:14px!important}.es-content-body a,.es-content-body ol li,.es-content-body p,.es-content-body ul li{font-size:14px!important}.es-footer-body a,.es-footer-body ol li,.es-footer-body p,.es-footer-body ul li{font-size:14px!important}.es-infoblock a,.es-infoblock ol li,.es-infoblock p,.es-infoblock ul li{font-size:12px!important}[class=gmail-fix]{display:none!important}.es-m-txt-c,.es-m-txt-c h1,.es-m-txt-c h2,.es-m-txt-c h3{text-align:center!important}.es-m-txt-r,.es-m-txt-r h1,.es-m-txt-r h2,.es-m-txt-r h3{text-align:right!important}.es-m-txt-l,.es-m-txt-l h1,.es-m-txt-l h2,.es-m-txt-l h3{text-align:left!important}.es-m-txt-c img,.es-m-txt-l img,.es-m-txt-r img{display:inline!important}.es-button-border{display:inline-block!important}a.es-button,button.es-button{font-size:20px!important;display:inline-block!important}.es-adaptive table,.es-left,.es-right{width:100%!important}.es-content,.es-content table,.es-footer,.es-footer table,.es-header,.es-header table{width:100%!important;max-width:600px!important}.es-adapt-td{display:block!important;width:100%!important}.adapt-img{width:100%!important;height:auto!important}.es-m-p0{padding:0!important}.es-m-p0r{padding-right:0!important}.es-m-p0l{padding-left:0!important}.es-m-p0t{padding-top:0!important}.es-m-p0b{padding-bottom:0!important}.es-m-p20b{padding-bottom:20px!important}.es-hidden,.es-mobile-hidden{display:none!important}table.es-desk-hidden,td.es-desk-hidden,tr.es-desk-hidden{width:auto!important;overflow:visible!important;float:none!important;max-height:inherit!important;line-height:inherit!important}tr.es-desk-hidden{display:table-row!important}table.es-desk-hidden{display:table!important}td.es-desk-menu-hidden{display:table-cell!important}.es-menu td{width:1%!important}.esd-block-html table,table.es-table-not-adapt{width:auto!important}table.es-social{display:inline-block!important}table.es-social td{display:inline-block!important}.es-m-p5{padding:5px!important}.es-m-p5t{padding-top:5px!important}.es-m-p5b{padding-bottom:5px!important}.es-m-p5r{padding-right:5px!important}.es-m-p5l{padding-left:5px!important}.es-m-p10{padding:10px!important}.es-m-p10t{padding-top:10px!important}.es-m-p10b{padding-bottom:10px!important}.es-m-p10r{padding-right:10px!important}.es-m-p10l{padding-left:10px!important}.es-m-p15{padding:15px!important}.es-m-p15t{padding-top:15px!important}.es-m-p15b{padding-bottom:15px!important}.es-m-p15r{padding-right:15px!important}.es-m-p15l{padding-left:15px!important}.es-m-p20{padding:20px!important}.es-m-p20t{padding-top:20px!important}.es-m-p20r{padding-right:20px!important}.es-m-p20l{padding-left:20px!important}.es-m-p25{padding:25px!important}.es-m-p25t{padding-top:25px!important}.es-m-p25b{padding-bottom:25px!important}.es-m-p25r{padding-right:25px!important}.es-m-p25l{padding-left:25px!important}.es-m-p30{padding:30px!important}.es-m-p30t{padding-top:30px!important}.es-m-p30b{padding-bottom:30px!important}.es-m-p30r{padding-right:30px!important}.es-m-p30l{padding-left:30px!important}.es-m-p35{padding:35px!important}.es-m-p35t{padding-top:35px!important}.es-m-p35b{padding-bottom:35px!important}.es-m-p35r{padding-right:35px!important}.es-m-p35l{padding-left:35px!important}.es-m-p40{padding:40px!important}.es-m-p40t{padding-top:40px!important}.es-m-p40b{padding-bottom:40px!important}.es-m-p40r{padding-right:40px!important}.es-m-p40l{padding-left:40px!important}.es-desk-hidden{display:table-row!important;width:auto!important;overflow:visible!important;max-height:inherit!important}}</style></head><body bis_status=\"ok\" bis_frame_id=\"2193\" data-new-gr-c-s-loaded=\"14.1134.0\" style=\"width:100%;font-family:arial,'helvetica neue',helvetica,sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;margin:0\"><div dir=\"ltr\" class=\"es-wrapper-color\" lang=\"en\" style=\"background-color:#fafafa\"><!--[if gte mso 9]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\"><v:fill type=\"tile\" color=\"#fafafa\"></v:fill></v:background><![endif]--><table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;padding:0;margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#fafafa\"><tr><td valign=\"top\" style=\"padding:0;margin:0\"><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%\"><tr><td class=\"es-info-area\" align=\"center\" style=\"padding:0;margin:0\"><table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;background-color:transparent;width:600px\" bgcolor=\"#FFFFFF\" role=\"none\"><tr><td align=\"left\" style=\"padding:20px;margin:0\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" valign=\"top\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" style=\"padding:0;margin:0;display:none\"></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"><tr><td align=\"center\" style=\"padding:0;margin:0\"><table bgcolor=\"#ffffff\" class=\"es-header-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;background-color:transparent;width:600px\"><tr><td align=\"left\" style=\"padding:20px;margin:0\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td class=\"es-m-p0r\" valign=\"top\" align=\"center\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" style=\"padding:0;margin:0;display:none\"></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%\"><tr><td align=\"center\" style=\"padding:0;margin:0\"><table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;background-color:#fff;width:600px\"><tr><td align=\"left\" style=\"padding:0;margin:0;padding-top:15px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" valign=\"top\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" style=\"padding:0;margin:0;padding-top:10px;padding-bottom:10px;font-size:0\"><img src=\"https://fbhjjld.stripocdn.email/content/guids/CABINET_c0e87147643dfd412738cb6184109942/images/151618429860259.png\" alt style=\"display:block;border:0;outline:0;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"100\"></td></tr><tr><td align=\"center\" class=\"es-m-txt-c\" style=\"padding:0;margin:0;padding-bottom:10px\"><h1 style=\"margin:0;line-height:46px;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;font-size:46px;font-style:normal;font-weight:700;color:#333\">Thanks for&nbsp;choosing us!</h1></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%\"><tr><td align=\"center\" style=\"padding:0;margin:0\"><table bgcolor=\"#ffffff\" class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;background-color:#fff;width:600px\"><tr><td align=\"left\" style=\"margin:0;padding-bottom:10px;padding-top:20px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" valign=\"top\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" class=\"es-m-p0r es-m-p0l\" style=\"margin:0;padding-top:5px;padding-bottom:5px;padding-left:40px;padding-right:40px\"><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Your order&nbsp;has now been completed!&nbsp;<br>We’ve attached your <strong>receipt</strong> to this email.</p></td></tr></table></td></tr></table></td></tr>" + generatedHtml + "<tr><td align=\"left\" style=\"padding:0;margin:0;padding-top:10px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;border-top:2px solid #efefef;border-bottom:2px solid #efefef\" role=\"presentation\"><tr><td align=\"right\" class=\"es-m-txt-r\" style=\"padding:0;margin:0;padding-top:10px;padding-bottom:20px\"><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Subtotal: <strong>" + order.getTotal() + "vnđ" + "</strong><br>Shipping: <strong>0vnđ</strong><br>Total: <strong>" + order.getTotal() + "vnđ" + "</strong></p></td></tr></table></td></tr></table></td></tr><tr><td align=\"left\" style=\"margin:0;padding-bottom:10px;padding-top:20px;padding-left:20px;padding-right:20px\"><!--[if mso]><table style=\"width:560px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:280px\" valign=\"top\"><![endif]--><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-left\" align=\"left\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;float:left\"><tr><td class=\"es-m-p0r es-m-p20b\" align=\"center\" style=\"padding:0;margin:0;width:280px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"left\" style=\"padding:0;margin:0\"><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Customer: <strong>" + customer.getName() + "</strong></p><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Invoice date:<strong> " + Generator.getDateTime(order.getCreatedAt(), Generator.DatePattern.DateForwardSlashPattern) + "</strong></p><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Payment method: <strong>COD</strong></p><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Currency: <strong>vnđ</strong></p></td></tr></table></td></tr></table><!--[if mso]><td style=\"width:0\"></td><td style=\"width:280px\" valign=\"top\"><![endif]--><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-right\" align=\"right\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;float:right\"><tr><td class=\"es-m-p0r\" align=\"center\" style=\"padding:0;margin:0;width:280px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"left\" class=\"es-m-txt-l\" style=\"padding:0;margin:0\"><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Shipping Method: <strong>GHTK</strong></p><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\">Shipping address: </p><p style=\"margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial,'helvetica neue',helvetica,sans-serif;line-height:21px;color:#333;font-size:14px\"><strong>" + order.getDeliveryAddress() + "</strong></p></td></tr></table></td></tr></table><!--[if mso]><![endif]--></td></tr><tr><td align=\"left\" style=\"margin:0;padding-bottom:10px;padding-top:15px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"left\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" style=\"padding:0;margin:0;display:none\"></td></tr></table></td></tr></table></td></tr></table></td></tr></table><table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%\"><tr><td class=\"es-info-area\" align=\"center\" style=\"padding:0;margin:0\"><table class=\"es-content-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0;background-color:transparent;width:600px\" bgcolor=\"#FFFFFF\" role=\"none\"><tr><td align=\"left\" style=\"padding:20px;margin:0\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" valign=\"top\" style=\"padding:0;margin:0;width:560px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"mso-table-lspace:0;mso-table-rspace:0;border-collapse:collapse;border-spacing:0\"><tr><td align=\"center\" style=\"padding:0;margin:0;display:none\"></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td></tr></table></div></body></html>";
        return html;
    }

    public String changePhoneNotification() {
        // String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        // String ShopURL = "http://localhost:8080";
        String html = "";

        return html;
    }

    public String getEmailTo() {
        return EmailTo;
    }

    public void setEmailTo(String emailTo) {
        EmailTo = emailTo;
    }
}
