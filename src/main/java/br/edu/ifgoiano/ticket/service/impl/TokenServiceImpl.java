package br.edu.ifgoiano.ticket.service.impl;

import br.edu.ifgoiano.ticket.controller.dto.request.autenticacao.RefreshTokenRequestDTO;
import br.edu.ifgoiano.ticket.controller.dto.response.login.LoginResponseDTO;
import br.edu.ifgoiano.ticket.controller.exception.ResourceForbiddenException;
import br.edu.ifgoiano.ticket.model.Usuario;
import br.edu.ifgoiano.ticket.service.TokenService;
import br.edu.ifgoiano.ticket.service.UsuarioService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private UsuarioService usuarioService;

    private final Integer TEMPO_EXPIRACAO_TOKEN = 2;
    private final Integer TEMPO_EXPIRACAO_REFRESHTOKEN = 3;

    @Override
    public LoginResponseDTO realizarLogin(Usuario usuario) {
        String accessToken = gerarToken(usuario);
        String refreshToken = refreshToken(usuario.getUsername());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccess_token(accessToken);
        loginResponseDTO.setRefresh_token(refreshToken);
        loginResponseDTO.setExpires_in(TEMPO_EXPIRACAO_TOKEN * 24 * 60 * 60);
        return loginResponseDTO;
    }

    @Override
    public LoginResponseDTO realizarRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        String username = getUsernameFromToken(refreshTokenRequestDTO.getRefreshToken());

        UserDetails user = usuarioService.loadUserByUsername(username);

        if (isTokenValid(refreshTokenRequestDTO.getRefreshToken(), user)) {
            return realizarLogin((Usuario) user);
        } else {
            throw new ResourceForbiddenException("Refresh Token inválido!");
        }
    }


    private String gerarToken(Usuario usuario) {
        try{
            String roles = usuario.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(","));

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getId().toString())
                    .withClaim("roles", roles)
                    .withExpiresAt(gerarDataExpiracaoToken())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    private String refreshToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("auth-api")
                .withSubject(username)
                .withExpiresAt(gerarDataExpiracaoRefreshToken())
                .sign(algorithm);
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getExpiresAt()
                    .before(new Date());
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao extrair username do token", exception);
        }
    }

    @Override
    public String getRolesFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("roles").asString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Erro ao extrair roles do token", exception);
        }
    }

    private Instant gerarDataExpiracaoToken(){
        return LocalDateTime.now().plusDays(TEMPO_EXPIRACAO_TOKEN).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant gerarDataExpiracaoRefreshToken(){
        return LocalDateTime.now().plusDays(TEMPO_EXPIRACAO_REFRESHTOKEN).toInstant(ZoneOffset.of("-03:00"));
    }
}
