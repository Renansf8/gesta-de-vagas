package br.com.renanferreira.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanferreira.gestao_vagas.exceptions.JobNotFoundException;
import br.com.renanferreira.gestao_vagas.exceptions.UserNotFoundException;
import br.com.renanferreira.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.renanferreira.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.renanferreira.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.renanferreira.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  // ID do candidato
  // ID da vaga
  public ApplyJobEntity excute(UUID idCandidate, UUID idJob) {
    // Validar se o usuário existe
    this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UserNotFoundException();
        });
    // Validar se a vaga existe
    this.jobRepository.findById(idJob)
        .orElseThrow(() -> {
          throw new JobNotFoundException();
        });

    // Candidato se inscrever na vaga
    var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob).build();

    applyJob = applyJobRepository.save(applyJob);
    return applyJob;
  }
}
