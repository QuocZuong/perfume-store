package Lib;

import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;

public class OAuth2 {
  /** Name of the application. */
  public static final String APP_NAME = "Perfume Store";

  /** Directory path for storing token. */
  private static final String TOKEN_DIR_PATH = String.format("%s/%s",
      System.getProperty("user.dir").replace("\\", "/"),
      "auth");

  /** Path of the credential file. */
  private static final String CRED_FILE_PATH = String.format("%s/%s",
      System.getProperty("user.dir").replace("\\", "/"),
      "auth/credentials.json");

  /** Application scope for requesting permission. */
  private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);

  /** Turn on for verbose logging */
  private static final boolean DEBUG = true;

  public static Credential getCredential(final JsonFactory JSON_FACTORY, HttpTransport HTTP_TRANSPORT)
      throws Exception {
    // Load client secrets.
    InputStream in = new FileInputStream(CRED_FILE_PATH);

    if (DEBUG)
      System.out.println(in.toString());

    Reader inputStreamReader = new InputStreamReader(in);
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, inputStreamReader);

    // Build the flow for installed application
    DataStoreFactory DATA_STORE_FACTORY = new FileDataStoreFactory(new File(TOKEN_DIR_PATH));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        clientSecrets, SCOPES)
        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build();

    /** Receiver for credential verrification code. */
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

    Credential cred = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

    return cred;
  }

  private static void sendEmail(HttpTransport HTTP_TRANSPORT, JsonFactory JSON_FACTORY, HttpRequestInitializer CRED)
      throws Exception {
    // Get email name for sending email
    final String ACCOUNT = new PropsLoader().getBotUserName();
    final String EMAIL_TO = ACCOUNT;

    // Build Gmail service
    Gmail mailService = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, CRED)
        .setApplicationName(APP_NAME)
        .build();

    // Create email
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);
    final String SUBJECT = "Test mail API";
    final String BODY_TEXT = "Send email successfully! 😎";

    message.setFrom(new InternetAddress(ACCOUNT)); // Set from address of the email
    message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO)); // Set email recipient
    message.setSubject(MimeUtility.encodeText(SUBJECT, "utf-8", "B")); // Set email message subject
    message.setText(BODY_TEXT);
    message.saveChanges();

    // Encode and create a gmail message
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    message.writeTo(buffer);

    byte[] messageBytes = buffer.toByteArray();
    String encodedMessage = Base64.encodeBase64URLSafeString(messageBytes);

    Message gmailMessage = new Message();
    gmailMessage.setRaw(encodedMessage);

    // Send the message
    if (DEBUG)
      System.out.println("Sending email to " + EMAIL_TO);

    gmailMessage = mailService.users().messages().send("me", gmailMessage).execute();

    System.out.println("sent email successfully!");
    System.out.println("Message id: " + gmailMessage.getId());
  }

  public static void main(String[] args) {
    try {
      JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
      HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      Credential CRED = getCredential(JSON_FACTORY, HTTP_TRANSPORT);

      sendEmail(HTTP_TRANSPORT, JSON_FACTORY, CRED);
    } catch (Exception e) {
      System.err.println(e);
    }

  }
}
