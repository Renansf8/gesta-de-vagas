package br.com.renanferreira.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.renanferreira.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.renanferreira.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.renanferreira.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Username/Password incorreto");
        });

    // Verificar se senha são iguais
    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    // Formarto do algoritmo para o token
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var espiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("javagas")
        .withSubject(company.getId().toString())
        .withExpiresAt(espiresIn)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

        // Objecto de respota da autenticação
        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
        .access_token(token)
        .expires_in(espiresIn.toEpochMilli())
        .build();
    return authCompanyResponseDTO;
  }
}
