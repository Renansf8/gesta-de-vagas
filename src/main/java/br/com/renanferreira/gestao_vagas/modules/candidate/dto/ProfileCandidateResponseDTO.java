package br.com.renanferreira.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

  @Schema(example = "Desenvolvedora Java")
  private String description;

  @Schema(example = "Maria")
  private String username;

  @Schema(example = "maria@gmail.com")
  private String email;

  private UUID id;

  @Schema(example = "Maria de Souza")
  private String name;
}
