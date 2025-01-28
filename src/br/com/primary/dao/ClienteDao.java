package br.com.primary.dao;

public class ClienteDao implements IClienteDao {

    public String salvar() {
        throw new UnsupportedOperationException("Não funciona sem config de banco");
        //return "Sucesso";
    }

    @Override
    public String buscar() {
        throw new UnsupportedOperationException("Não funciona sem config de banco");
        //return "Sucesso";
    }

    @Override
    public String atualizar() {
        throw new UnsupportedOperationException("Não funciona sem config de banco");
        //return "Sucesso";
    }

    @Override
    public String excluir() {
        throw new UnsupportedOperationException("Não funciona sem config de banco");
        //return "Sucesso";
    }
}
