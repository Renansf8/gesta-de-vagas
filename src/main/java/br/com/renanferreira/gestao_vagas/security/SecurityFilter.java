package br.com.renanferreira.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.renanferreira.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Ta fazendo a validação da company

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
      // Antes de fazer qualquer coisa, setar como null
      // SecurityContextHolder.getContext().setAuthentication(null);
      String header = request.getHeader("Authorization");

      if (request.getRequestURI().startsWith("/company")) {
        if (header != null) {
          var subjectToken = this.jwtProvider.validateToken(header);
          if (subjectToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
          }
          // Mandar o company_id por aqui e não no body
          request.setAttribute("company_id", subjectToken);
          UsernamePasswordAuthenticationToken auth = 
          new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }

    filterChain.doFilter(request, response);
  }
  
}
