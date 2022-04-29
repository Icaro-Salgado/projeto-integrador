package br.com.mercadolivre.projetointegrador.warehouse.filter;

import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final AppUserRepository repository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromHeader(request);
    String idFromToken = tokenService.isTokenValid(token);

    if (idFromToken != null) {
      this.authenticate(Long.valueOf(idFromToken));
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return "";
    }

    return token.replace("Bearer ", "");
  }

  private void authenticate(Long userId) {
    Optional<AppUser> userOptional = repository.findById(userId);

    if (userOptional.isPresent()) {
      AppUser user = userOptional.get();
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
  }
}
