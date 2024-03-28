package br.com.renanferreira.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// Cria um construtor com argumentos
@AllArgsConstructor
public class ErrorMessageDTO {
  
  private String message;
  private String field;
}
