package br.com.mercadolivre.projetointegrador.security.service;

import br.com.mercadolivre.projetointegrador.enums.UserOrigin;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.model.UserRole;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.security.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;
  private final RolesRepository rolesRepository;

  public AppUser registerUser(AppUser user, UserOrigin origin) {
    UserRole role = rolesRepository.findByName(origin.getRole()).orElseThrow(() -> new NotFoundException("Role não encontrada"));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    user.getAuthorities().add(role);

    return appUserRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AppUser> userOptional = appUserRepository.findByEmail(username);

    if (userOptional.isPresent()) {
      return userOptional.get();
    }

    throw new UsernameNotFoundException("User not found");
  }
}
