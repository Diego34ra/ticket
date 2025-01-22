package br.edu.ifgoiano.ticket.repository;

import br.edu.ifgoiano.ticket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT CASE WHEN u.tipoUsuario = 'GERENTE' THEN true ELSE false END FROM tb_usuario u WHERE u.id = :id")
    boolean isUsuarioGerente(@Param("id") Long id);

    UserDetails findByEmail(String email);
}
