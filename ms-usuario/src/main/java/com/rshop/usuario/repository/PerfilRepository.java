package com.rshop.usuario.repository;

import com.rshop.usuario.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByUsuarioId(Long usuarioId);
    boolean existsByCpf(String cpf);
}
