package com.br.minibanco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MiniBancoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MiniBancoApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

//colar no google http://localhost:8080/swagger-ui/index.html
