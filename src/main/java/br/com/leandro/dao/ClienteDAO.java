package br.com.leandro.dao;

import br.com.leandro.dao.generic.GenericDAO;
import br.com.leandro.domain.Cliente;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {
    public ClienteDAO() {
        super(Cliente.class);
    }
}
