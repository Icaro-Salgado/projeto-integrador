package br.com.mercadolivre.projetointegrador.warehouse.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.LoginDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RegisterDTO;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.AppUserMapper;
import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import br.com.mercadolivre.projetointegrador.warehouse.service.AuthenticationService;
import br.com.mercadolivre.projetointegrador.warehouse.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthenticationService authService;


    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(token);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO){
        AppUser created = authService.registerUser(AppUserMapper.INSTANCE.toModel(registerDTO));

        if(created == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
