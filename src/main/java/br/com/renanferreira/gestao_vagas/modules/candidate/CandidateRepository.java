package br.com.renanferreira.gestao_vagas.modules.candidate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
  // Optional -> para ter acesso a mais operações caso o user não seja encontrado
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
  Optional<CandidateEntity> findByUsername(String username);
}
