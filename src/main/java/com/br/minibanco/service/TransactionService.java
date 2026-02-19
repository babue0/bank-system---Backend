package com.br.minibanco.service;

import com.br.minibanco.domain.transaction.Transaction;
import com.br.minibanco.domain.user.User;
import com.br.minibanco.dtos.TransactionDTO;
import com.br.minibanco.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionRepository repository;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private NotificationService notificationService;

  @Transactional
  public void createTransaction(TransactionDTO transaction) throws Exception{
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());;

    userService.validateTransaction(sender, transaction.value());

    boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
    if(!isAuthorized) {
      throw new Exception("transacao nao autorizada");
    }

    Transaction newTransaction = new Transaction();
    newTransaction.setAmount(transaction.value());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));

    this.repository.save(newTransaction);
    this.userService.saveUser(sender);
    this.userService.saveUser(receiver);

    this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
    this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

  }

  public boolean authorizeTransaction(User sender, BigDecimal value){
    /*ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK){
      String message = (String) authorizationResponse.getBody().get("status");
      return "success".equalsIgnoreCase(message);
    } else return false

     */
    return true;
  }
}