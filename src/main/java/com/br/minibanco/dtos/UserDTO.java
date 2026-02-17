package com.br.minibanco.dtos;

import com.br.minibanco.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO (String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
