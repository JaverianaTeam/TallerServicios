package com.notificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class NotificacionApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificacionApplication.class, args);
  }

}
