package br.com.primary.service;

import br.com.primary.dao.IClienteDao;

public class ClienteService {

    private IClienteDao clienteDao;

    public ClienteService(IClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public String salvar() {
        clienteDao.salvar();
        return "Sucesso";
    }
    public String buscar() {
        clienteDao.buscar();
        return "Sucesso";
    }
    public String excluir() {
        clienteDao.excluir();
        return "Sucesso";
    }
    public String atualizar() {
        clienteDao.atualizar();
        return "Sucesso";
    }
}
