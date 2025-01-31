package br.edu.ifgoiano.ticket.controller.dto.request.usuario;

import br.edu.ifgoiano.ticket.controller.dto.request.TelefoneDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioInputDTO {
    @NotNull(message = "Nome é obrigatório.")
    private String nome;

    @NotNull(message = "Email é obrigatório.")
    @Email(message = "Email deve estar no formato válido.")
    private String email;

    @NotNull(message = "CPF é obrigatório.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX.")
    private String cpf;

    @NotNull(message = "Senha é obrigatória.")
    private String senha;

    private List<TelefoneDTO> contatos;
}
