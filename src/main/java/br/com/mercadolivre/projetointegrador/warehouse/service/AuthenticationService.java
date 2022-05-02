package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.UserRole;
import br.com.mercadolivre.projetointegrador.warehouse.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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

  public AppUser registerUser(AppUser user) {
    UserRole role = rolesRepository.findById(2L).get();
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
