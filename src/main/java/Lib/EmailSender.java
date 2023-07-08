package Lib;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

    public EmailSender() {
    }

    private final String ACCOUNT = "quoczuong2003@gmail.com";
    private final String PASSWORD = "qvcqfobbvbqbkwxm";

    // public final String GENERATE_PASSWORD_SUBJECT = "Your account at XXVI Store
    // has been created!";
    public final String GENERATE_PASSWORD_SUBJECT = "Tài khoản của bạn đã được tạo tại trang web XXVI Store";
    public final String CHANGE_PASSWORD_NOTFICATION = "[XXVI STORE] Mật khẩu đã thay đổi";
    public final String CHANGE_EMAIL_NOTFICATION = "[XXVI STORE] Địa chỉ email đã đổi";

    private String EmailTo;

    public boolean sendToEmail(String subject, String html) throws UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ACCOUNT, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ACCOUNT)); // Set from address of the email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EmailTo)); // Set email recipient
            message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B")); // Set email message subject
            message.setContent(html, "text/html; charset=utf-8");
            message.saveChanges();
            Transport.send(message); // Send email message
            System.out.println("sent email successfully!");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String generatePasswordEmailHTML(String generatePassword) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ClientURL = "http://localhost:8080/Client/User";

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

    public String changePasswordNotifcation(String newPassword) {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
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
                + "												MẬT KHẨU CỦA BẠN ĐÃ BỊ THAY ĐỔI 😜</h1>\n"
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
                + "															<p style=\"margin: 0 0 16px;\">Xin chào "
                + username + ",</p>\n"
                + "															<p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng mật khẩu của bạn đã được thay đổi\n"
                + "																trên XXVI STORE. Nếu bạn không thay đổi mật khẩu, vui lòng liên hệ người quản trị\n"
                + "																website qua email <a href=\"mailto:"
                + ACCOUNT + "\">" + ACCOUNT + "</a>.\n"
                + "															</p>\n"
                + "															<p style=\"margin: 0 0 16px;\">Email này đã được gửi đến <a\n"
                + "																	href=\"mailto:" + username
                + "@fpt.edu.vn\">" + username + "@fpt.edu.vn</a></strong></p>\n"
                + "\n"
                + "															<p style=\"margin: 0 0 16px;\">Xin cảm ơn\n"
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
                + "                                                    ĐỊA CHỈ EMAIL ĐÃ BỊ THAY ĐỔI 😾</h1>\n"
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
                + "                                                                <p style=\"margin: 0 0 16px;\">Xin chào "+username+",</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thông báo này xác nhận rằng email của bạn trên XXIV STORE đã được thay đổi thành <strong><a href=\"mailto:"+newEmail+"\">"+newEmail+"</a></strong>.\n"
                + "                                                                </p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Nếu bạn không thay đổi email, vui lòng liên hệ Quản trị trang web tại <a href=\"mailto:"+ACCOUNT+"\">"+ACCOUNT+"</a></strong>.</p>\n"
                + "                                                                <p style=\"margin: 0 0 16px;\">Thân ái,\n"
                + "                                                                    <br>XXVI STORE\n"
                + "                                                                    <br><a href=\""+ShopURL+"\">"+ShopURL+"</a>\n"
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

    public String changePhoneNotification() {
        String username = EmailTo.substring(0, EmailTo.indexOf("@"));
        String ShopURL = "http://localhost:8080";
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
