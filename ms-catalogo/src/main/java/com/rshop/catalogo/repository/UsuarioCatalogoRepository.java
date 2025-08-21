package com.rshop.catalogo.repository;

import com.rshop.catalogo.model.UsuarioCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioCatalogoRepository extends JpaRepository<UsuarioCatalogo, Long> {
    Optional<UsuarioCatalogo> findByEmail(String email);
}
