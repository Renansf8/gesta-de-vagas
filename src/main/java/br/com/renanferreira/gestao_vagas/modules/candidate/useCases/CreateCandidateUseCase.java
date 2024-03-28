package br.com.renanferreira.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanferreira.gestao_vagas.exceptions.UserFoundException;
import br.com.renanferreira.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.renanferreira.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
   // Uma forma de lançar o erro caso o usuário exista, poderia usar um if e ResponseEntity
      .ifPresent((user) -> {
        throw new UserFoundException(); 
      });

    return this.candidateRepository.save(candidateEntity);
  }
}
