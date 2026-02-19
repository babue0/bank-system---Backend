package com.br.minibanco.service;

import com.br.minibanco.domain.user.User;
import com.br.minibanco.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

  @Autowired
  private RestTemplate restTemplate;

  public void sendNotification(User user, String message) throws Exception{
    String email = user.getEmail();
    NotificationDTO notificationRequest = new NotificationDTO(email, message);

    System.out.println(" NOTIFICAÇÃO ENVIADA PARA: " + email);
    System.out.println(" MENSAGEM: " + message);
  }
}
