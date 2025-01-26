package br.com.leandro.dao;

import br.com.leandro.dao.generic.GenericDAO;
import br.com.leandro.domain.Produto;

public class ProdutoDAO extends GenericDAO<Produto, Long> implements IProdutoDAO {

    public ProdutoDAO(){
        super(Produto.class);
    }

}
