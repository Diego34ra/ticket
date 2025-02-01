package br.edu.ifgoiano.ticket.controller.dto.response.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String access_token;
    private String token_type = "Bearer";
    private String refresh_token;
    private int expires_in;
}
