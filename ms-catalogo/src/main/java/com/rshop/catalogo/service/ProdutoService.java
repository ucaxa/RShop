package com.rshop.catalogo.service;

import com.rshop.catalogo.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {

    ProdutoDTO salvar(ProdutoDTO dto);

    ProdutoDTO atualizar(Long id, ProdutoDTO dto);

    void deletar(Long id);

    ProdutoDTO buscarPorId(Long id);

    List<ProdutoDTO> listarTodos();
}

