package br.com.leandro.dao;

import br.com.leandro.dao.generic.GenericDAO;
import br.com.leandro.domain.Cliente;
import br.com.leandro.domain.Produto;
import br.com.leandro.domain.Venda;
import br.com.leandro.exceptions.DAOException;
import br.com.leandro.exceptions.TipoChaveNaoEncontradaException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class VendaDAO extends GenericDAO<Venda, Long> implements IVendaDAO {
    public VendaDAO(Class<Venda> persistenteClass) {
        super(persistenteClass);
    }

    @Override
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(venda);
    }

    @Override
    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(venda);
    }

    @Override
    public void excluir(Venda venda) throws  DAOException{
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public Venda cadastrar(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        try {
            openConnection();
            venda.getProdutos().forEach(prod -> {
                Produto prodJpa = entityManager.merge(prod.getProduto());
                prod.setProduto(prodJpa);
            });
            Cliente cliente = entityManager.merge(venda.getCliente());
            venda.setCliente(cliente);
            entityManager.persist(venda);
            entityManager.getTransaction().commit();
            closeConnection();
            return venda;
        } catch (Exception e){
            throw new DAOException("ERRO SALVANDO VENDA ", e);
        }
    }

    @Override
    public Venda consultarComCollection(Long id) {
        openConnection();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venda> query = builder.createQuery(Venda.class);
        Root<Venda> root = query.from(Venda.class);
        root.fetch("cliente");
        root.fetch("produtos");
        query.select(root).where(builder.equal(root.get("id"), id));
        TypedQuery<Venda> tpQuery =
                entityManager.createQuery(query);
        Venda venda = tpQuery.getSingleResult();
        closeConnection();
        return venda;
    }
}
