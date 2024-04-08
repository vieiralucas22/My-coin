package com.example.mycoin.services;

import static com.example.mycoin.constants.Constants.MY_COIN_MAIL;
import static com.example.mycoin.constants.Constants.MY_COIN_PASSWORD;
import static com.example.mycoin.constants.Constants.SMTP_AUTH_KEY;
import static com.example.mycoin.constants.Constants.SMTP_HOST_KEY;
import static com.example.mycoin.constants.Constants.SMTP_HOST_VALUE;
import static com.example.mycoin.constants.Constants.SMTP_PORT_KEY;
import static com.example.mycoin.constants.Constants.SMTP_PORT_VALUE;
import static com.example.mycoin.constants.Constants.SMTP_SSL_KEY;

import android.util.Log;

import com.example.mycoin.gateway.services.EmailService;
import com.example.mycoin.preferences.AppPreferences;
import com.example.mycoin.utils.LogcatUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    public static final String TAG = LogcatUtil.getTag(EmailServiceImpl.class);

    private final AppPreferences mAppPreferences;

    @Inject
    public EmailServiceImpl(AppPreferences appPreferences) {
        mAppPreferences = appPreferences;
    }

    @Override
    public boolean sendForgotPasswordEmail(String userEmail) {
        Properties props = new Properties();
        props.put(SMTP_AUTH_KEY, "true");
        props.put(SMTP_SSL_KEY, "true");
        props.put(SMTP_HOST_KEY, SMTP_HOST_VALUE);
        props.put(SMTP_PORT_KEY, SMTP_PORT_VALUE);

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MY_COIN_MAIL, MY_COIN_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(MY_COIN_MAIL));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

            message.setSubject("Email de Teste com JavaMail");

            String htmlContent = "<h1 style='color: blue;'>Olá,</h1>"
                    + "<p style='font-size: 18px;'>Aqui está seu código!</p>"
                    +  "<p style='font-size: 18px'>"
                    + mAppPreferences.getConfirmationCode() + "</p>";
            message.setContent(htmlContent, "text/html");

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            thread.start();

            mAppPreferences.setUserEmail(userEmail);
            Log.d(TAG, "Success to send a email");
            return true;
        } catch (MessagingException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
        return false;
    }
}
