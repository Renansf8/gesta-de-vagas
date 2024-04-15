package br.com.renanferreira.gestao_vagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import br.com.renanferreira.gestao_vagas.exceptions.JobNotFoundException;
import br.com.renanferreira.gestao_vagas.exceptions.UserNotFoundException;
import br.com.renanferreira.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.renanferreira.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.renanferreira.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
  
  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void should_not_be_able_to_apply_job_with_candidate_not_found() {
    try {
      applyJobCandidateUseCase.excute(null, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  public void should_not_be_able_to_apply_job_with_job_not_found() {
    var idCandidate = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.excute(idCandidate, null);
    } catch (Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }
}
