package br.com.primary.usercase;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.primary.domain.Cliente;
import br.com.primary.repository.IVendaRepository;

@Service
public class BuscaCliente {
    private IVendaRepository vendaRepository;

    public BuscaCliente(IVendaRepository produtoRepository) {
        this.vendaRepository = produtoRepository;
    }

    public Page<Cliente> buscar(Pageable pageable) {
        return vendaRepository.findAll(pageable);
    }

    public Cliente buscarPorId(String id) {
        return vendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado para o id informado"));
    }

    public Boolean isCadastrado(String id) {
        Optional<Cliente> cliente = vendaRepository.findById(id);
        return cliente.isPresent() ? true : false;
    }

    public Cliente buscarPorCpf(String cpf) {
        return  vendaRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado para CPF informado"));
    }
}
