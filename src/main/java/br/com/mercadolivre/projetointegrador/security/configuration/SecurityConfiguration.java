package br.com.mercadolivre.projetointegrador.security.configuration;

import br.com.mercadolivre.projetointegrador.security.filter.TokenAuthenticationFilter;
import br.com.mercadolivre.projetointegrador.security.repository.AppUserRepository;
import br.com.mercadolivre.projetointegrador.security.service.AuthenticationService;
import br.com.mercadolivre.projetointegrador.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthenticationService authenticationService;
  private final TokenService tokenService;
  private final AppUserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder)
        .withUser("springTest")
        .password(passwordEncoder.encode("usertest"))
        .roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/fresh-products/due-date/queryparam=7queryparam=ff")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/auth", "/api/v1/*/auth/register")
        .permitAll()
        .antMatchers("/api/v1/marketplace/**")
        .hasAuthority("CUSTOMER")
        .antMatchers("/api/v1/warehouse/**")
        .access("hasAuthority('MANAGER')")
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new TokenAuthenticationFilter(tokenService, repository),
            UsernamePasswordAuthenticationFilter.class);
  }
}
