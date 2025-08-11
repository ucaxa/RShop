package com.ms.pedido.service.impl;

import com.ms.pedido.dto.*;
import com.ms.pedido.exception.ResourceNotFoundException;
//import com.ms.pedido.kafka.*;
import com.ms.pedido.model.*;
import com.ms.pedido.repository.*;
import com.ms.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ModelMapper modelMapper;
  //  private final PedidoProducer pedidoProducer;

    @Override
    @Transactional
    public PedidoDTO criarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNumeroControle(dto.getNumeroControle() != null ? dto.getNumeroControle() : gerarNumeroControle());
        pedido.setDataCadastro(LocalDateTime.now());
        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setCodigoCliente(dto.getCodigoCliente());

        // monta itens
        List<ItemPedido> itens = dto.getItens().stream().map(iDto -> {
            ItemPedido item = new ItemPedido();
            item.setProdutoId(iDto.getProdutoId());
            item.setProdutoNome(iDto.getProdutoNome());
            item.setQuantidade(iDto.getQuantidade());
            item.setValorUnitario(iDto.getValorUnitario());
            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);

        // calcula totais
        BigDecimal valorTotal = itens.stream()
                .map(it -> it.getValorUnitario().multiply(BigDecimal.valueOf(it.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int quantidadeTotal = itens.stream().mapToInt(ItemPedido::getQuantidade).sum();

        pedido.setValorTotal(valorTotal);
        pedido.setQuantidadeTotal(quantidadeTotal);

        Pedido salvo = pedidoRepository.save(pedido);

        // envia evento Kafka pedido-criado
    //    PedidoEvent event = toEvento(salvo);
    //    pedidoProducer.enviarPedidoCriado(event);

        return modelMapper.map(salvo, PedidoDTO.class);
    }

    private String gerarNumeroControle() {
        return "RSHP-" + System.currentTimeMillis();
    }

  /*  private PedidoEvent toEvento(Pedido p) {
        List<PedidoEvent.ItemEvent> itens = p.getItens().stream()
                .map(it -> new PedidoEvent.ItemEvent(it.getProdutoId(), it.getProdutoNome(), it.getQuantidade(), it.getValorUnitario()))
                .collect(Collectors.toList());

        return new PedidoEvent(p.getId(), p.getNumeroControle(), p.getDataCadastro(), p.getNomeCliente(), p.getValorTotal(), p.getQuantidadeTotal(), p.getCodigoCliente(), itens);
    }*/

    @Override
    public PedidoDTO buscarPorId(Long id) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + id));
        return modelMapper.map(p, PedidoDTO.class);
    }

    @Override
    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDTO.class))
                .collect(Collectors.toList());
    }
}

