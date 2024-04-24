package br.com.renanferreira.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanferreira.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.renanferreira.gestao_vagas.modules.company.entities.JobEntity;
import br.com.renanferreira.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.renanferreira.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.renanferreira.gestao_vagas.modules.company.useCases.ListJobs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {
  
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')") // Apenas o usuário com essa Role vai acessar essa rota
  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(summary = "Cadastro de vagas", description = "Essa função é responsável por cadastrar as vagas dentro da empresa")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = JobEntity.class))
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
   var companyId = request.getAttribute("company_id");

   try {
    var jobEntity = JobEntity.builder()
    .benefits(createJobDTO.getBenefits())
    .companyId(UUID.fromString(companyId.toString()))
    .description(createJobDTO.getDescription())
    .level(createJobDTO.getLevel())
    .build();

    var result = this.createJobUseCase.excute(jobEntity);
    return ResponseEntity.ok().body(result);
   } catch (Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
   }

  //  jobEntity.setCompanyId(UUID.fromString(companyId.toString()));
  }
}
