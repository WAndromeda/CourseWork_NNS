import Entity.Client;
import Entity.ConfirmationKey;
import Exceptions.InputDataException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EmailSender {

    public static String sendConfirmationEmail(Client client, ConfirmationKey confirmationKey) throws InputDataException {

        // Некорректные входные данные
        if (client == null)
            throw new NullPointerException("Client is NULL");
        if (confirmationKey == null)
            throw new NullPointerException("СonfirmationKey is NULL");
        if (client.getEmail() == null)
            throw new NullPointerException("Client.email is NULL");
        if (confirmationKey.getConfirmation_key() == null)
            throw new NullPointerException("СonfirmationKey.confirmation_key is NULL");
        if (client.getId() != confirmationKey.getId_client() )
            throw new InputDataException("Check input data to send confirmation email to client" +
                    "\nConfirmationKey.id_client must be equal Client.id");
        if ( confirmationKey.getConfirmation_key().length() != ConfirmationKey.ConfirmationKeyLength )
            throw new InputDataException("Check input data to send confirmation email to client" +
                        "\nConfirmationKey.length must be equal " + ConfirmationKey.ConfirmationKeyLength);
        if (client.getEmail().length() < 5)
            throw new InputDataException("Check input data to send confirmation email to client" +
                        "\nClient.email must be longer than 4 symbols");

        //ConfirmationKey confirmationKey = new ConfirmationKey(client.getId(), RandomStringUtils.randomAlphanumeric(30));


        String to = client.getEmail().trim().toLowerCase();         // receiver email
        String from = "nns.shop@yandex.ru";       // sender email
        String fromMail = "nns.shop@bk.ru";
        String host = "smtp.yandex.ru";            // mail server host
        String hostMail = "smtp.mail.ru";            // mail server host
        String href = "http://localhost/email-confirmation?id=".trim();
        String hrefUnsubscribe = ("http://localhost/unsubscribe?id=" + client.getId()).trim();
        String passwordFrom = "SEF_poLUQ9I3/4!1_AD2_sdf3";

        final String SMTP_PORT = "465";
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", hostMail);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", SMTP_PORT);
        props.setProperty("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        Session session = null;
        try {

            session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(fromMail, passwordFrom);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            return ThreadConnectedClient.httpCode500;
        }

        //confirmationKeyService.addConfirmationKey(confirmationKey);
        // actual mail body

        String sumHREF = (href + client.getId() + "_" + confirmationKey.getConfirmation_key().trim()).trim();
        String messageHTML = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"+
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0; padding:0\">" +
                    "<tr>" +
                        "<td>" +
                            "<center style=\"max-width: 600px; width: 100%;\">" +
                                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0; padding:0\" width=\"100%\">" +
                                    "<tr>" +
                                        "<td>" +
                                            "<img src=\"cid:nns_shop_image\" width=\"220\" height=\"170\" style=\"display:block;\"> <br>" +
                                        "</td>" +
                                    "<tr>" +
                                        "<td style=\"font-size: 16pt\">" +
                                            "<b>Подтвердите регистрацию</b> <br>" +
                                        "<td>" +
                                    "<tr>" +
                                    "<tr>" +
                                        "<td>" +
                                            "<span>Спасибо за регистрацию на сайте NNS-SHOP.ru</span> <br>" +
                                            "<span>Для её завершения, пожалуйста, перейдите по ссылки ниже: </span><br><br>" +
                                        "</td>" +
                                    "<tr>" +
                                    "<tr>" +
                                        "<td>" +
                                            "<a href=\"" + sumHREF + "\">" + sumHREF + "</a> <br><br>" +
                                            "<span>С уважением, ваш NNS-SHOP!</span> <br><br>" +
                                        "</td>" +
                                    "<tr>" +
                                "</table>" +
                                "<table background=\"#edeae6\" bgcolor=\"#edeae6\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0; padding:0\" width=\"100%\">" +
                                    "<tr>" +
                                        "<td bgcolor=\"#edeae6\">" +
                                            "<span>Это письмо было отправлено автоматически</span> <br>"  +
                                            "<span>Если Вы считаете, что Вы получили письмо по ошибке, просто проигнорируйте его</span> <br><br>"+
                                            "<span>Коснитесь ссылки ниже, чтобы ОТПИСАТЬСЯ и не получать сообщения от " + fromMail +" </span>" +
                                        "</td>" +
                                    "</tr>" +
                                    "</tr>" +
                                        "<td bgcolor=\"#edeae6\">" +
                                            "<a href=\"" + hrefUnsubscribe + "\">" + hrefUnsubscribe + "</a>" +
                                        "<td>" +
                                    "</tr>" +
                                "</table>" +
                            "</center>" +
                        "</td>" +
                    "</tr>" +
                "</table>";
        String imagePath = "./src/images/nns_shop_image.jpg";

        try {
            MimeMessage msg = new MimeMessage(session); // email message
            msg.setFrom(new InternetAddress(fromMail)); // setting header fields
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageHTML, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);
            //multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            //MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            BufferedImage img = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", byteArrayOutputStream);

            DataSource source = new ByteArrayDataSource(byteArrayOutputStream.toByteArray(), "image/jpg");
            //DataSource source = new FileDataSource(attachment);
            //attachmentBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<nns_shop_image>");
            multipart.addBodyPart(messageBodyPart);
            //multipart.addBodyPart(attachmentBodyPart);

            //msg.setContent(messageHTML, "text/html; charset=utf-8");
            msg.setSubject("Пожалуйста, подтвердите вашу регистрацию"); // subject line
            msg.setSentDate(new Date());

            msg.setContent(multipart);
            // Send message
            Transport.send(msg);
            System.out.println("Email Sent successfully....");
            confirmationKey.setIs_email_sent(ConfirmationKey.IS_EMAIL_SENT.да);
            return ThreadConnectedClient.httpCode200;
        } catch (MessagingException  mex) {
            mex.printStackTrace();
            if (mex.toString().contains("550 Message was not accepted -- invalid mailbox") || mex.toString().contains("550 Message was not accepted"))
                return ThreadConnectedClient.httpCode550;
            else
                return ThreadConnectedClient.httpCode500;
        }catch (IOException IOex){
            IOex.printStackTrace();
            return ThreadConnectedClient.httpCode500;
        }
    }
}
