package br.com.renanferreira.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuário não encontrado");
  }
}
