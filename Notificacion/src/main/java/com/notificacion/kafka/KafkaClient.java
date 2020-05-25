package com.notificacion.kafka;

import com.notificacion.model.NotificationObject;
import com.notificacion.utils.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class KafkaClient {

  @KafkaListener(topics = "${modval.topic.name}", groupId = "${modval.group.name}")
  public void listenNotifications(String message) {
    System.out.println("Mensaje Recibido " + message);
    NotificationObject data = JsonConverter.toObject(message, NotificationObject.class);
    sendMail(data);
  }


  public void sendMail(NotificationObject data) {
    System.out.println("Preparado para enviar correo");
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.live.com");
    props.put("mail.smtp.socketFactory.port", "25");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth","true");
    props.put("mail.smtp.port","25");
    props.put("mail.smtp.starttls.enable","true");
    System.out.println("Puse las propiedades");
    Session session = Session.getDefaultInstance(
            props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jrafael.ocampo@javeriana.edu.co", "Bryan3615*");
              }
            }
    );

    try {
      System.out.println("Armando el mensaje");
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("jrafael.ocampo@javeriana.edu.co"));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(data.getEmail()));
      message.setSubject("Notificación de Pago de Factura");
      message.setText("Señor@: " + data.getNombreUsuario() + " Le informamos que la transacción de su factura nro " + data.getReferencia() + " por valor de " + data.getValor()
                      + " tuvo el siguiente resultado " + data.getMensaje());
      Transport.send(message);
      System.out.println("Correo enviado.");
    } catch (MessagingException e) {
      e.printStackTrace();
    }


  }

}
