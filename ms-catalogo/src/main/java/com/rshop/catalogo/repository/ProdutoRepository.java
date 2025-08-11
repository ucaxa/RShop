package com.rshop.catalogo.repository;


import com.rshop.catalogo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
