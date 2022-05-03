package br.com.mercadolivre.projetointegrador.security.controller;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.service.AuthenticationService;
import br.com.mercadolivre.projetointegrador.security.service.TokenService;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.LoginDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RegisterDTO;
import br.com.mercadolivre.projetointegrador.warehouse.exception.StandardError;
import br.com.mercadolivre.projetointegrador.warehouse.mapper.AppUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Autenticador")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthenticationService authService;

    @Operation(summary = "REALIZA O LOGIN", description = "Realiza o login com email e password")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Login efetuado com sucesso",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = LoginDTO.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Login não autorizado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = StandardError.class))
                            })
            })
    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(token);
    }

    @Operation(
            summary = "REGISTRA O USUARIO",
            description = "Registra o usuario manager com email, password, name e username")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Registro efetuado com sucesso",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = LoginDTO.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Registro não autorizado",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = StandardError.class))
                            })
            })
    @PostMapping("/warehouse/auth/register")
    public ResponseEntity<?> registerWarehouseUser(@RequestBody @Valid RegisterDTO registerDTO) {
        AppUser created = authService.registerUser(AppUserMapper.INSTANCE.toModel(registerDTO), "MANAGER");

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/marketplace/auth/register")
    public ResponseEntity<?> registerMarketplaceUser(@RequestBody @Valid RegisterDTO registerDTO) {
        AppUser created = authService.registerUser(AppUserMapper.INSTANCE.toModel(registerDTO), "CUSTOMER");

        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
