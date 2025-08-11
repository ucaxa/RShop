package com.rshop.catalogo.repository;


import com.rshop.catalogo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByDescricao(String descricao);
}
