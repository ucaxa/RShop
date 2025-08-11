package com.rshop.catalogo.service.impl;
import com.rshop.catalogo.dto.ProdutoDTO;
import com.rshop.catalogo.exception.ResourceNotFoundException;
//import com.rshop.catalogo.kafka.ProdutoEvent;
//import com.rshop.catalogo.kafka.ProdutoProducer;
import com.rshop.catalogo.model.Categoria;
import com.rshop.catalogo.model.Produto;
import com.rshop.catalogo.repository.CategoriaRepository;
import com.rshop.catalogo.repository.ProdutoRepository;
import com.rshop.catalogo.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
   // private final ProdutoProducer produtoProducer;

    private ProdutoDTO toDTO(Produto p) {
        return new ProdutoDTO(p.getId(), p.getDescricao(), p.getValor(), p.getDataCadastro(), p.getCategoria().getId(), p.getCategoria().getDescricao());
    }

    private Produto toEntity(ProdutoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + dto.getCategoriaId()));

        Produto produto = new Produto();
        produto.setDescricao(dto.getDescricao());
        produto.setValor(dto.getValor());
        produto.setDataCadastro(dto.getDataCadastro() != null ? dto.getDataCadastro() : LocalDateTime.now());
        produto.setCategoria(categoria);
        return produto;
    }

    @Override
    public ProdutoDTO salvar(ProdutoDTO dto) {
        Produto produto = toEntity(dto);
        Produto salvo = produtoRepository.save(produto);

     //   ProdutoEvent event = new ProdutoEvent(salvo.getId(), salvo.getDescricao(), salvo.getValor(), salvo.getDataCadastro(), salvo.getCategoria().getId());
   //     produtoProducer.enviarEventoProdutoCriado(event);

        return toDTO(salvo);
    }

    @Override
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + dto.getCategoriaId()));

        existente.setDescricao(dto.getDescricao());
        existente.setValor(dto.getValor());
        existente.setCategoria(categoria);

        Produto atualizado = produtoRepository.save(existente);

   //     ProdutoEvent event = new ProdutoEvent(atualizado.getId(), atualizado.getDescricao(), atualizado.getValor(), atualizado.getDataCadastro(), atualizado.getCategoria().getId());
    //    produtoProducer.enviarEventoProdutoCriado(event);

        return toDTO(atualizado);
    }

    @Override
    public void deletar(Long id) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        produtoRepository.delete(existente);
    }

    @Override
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
        return toDTO(produto);
    }

    @Override
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

