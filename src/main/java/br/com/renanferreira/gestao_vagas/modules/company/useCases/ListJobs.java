package br.com.renanferreira.gestao_vagas.modules.company.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.renanferreira.gestao_vagas.modules.company.entities.JobEntity;
import br.com.renanferreira.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ListJobs {
   @Autowired
  private JobRepository jobRepository;
  
  public List<JobEntity> execute() {
    return this.jobRepository.findAll();
  }
}
