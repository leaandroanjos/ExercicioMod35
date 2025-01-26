package br.com.leandro.dao.generic;

import br.com.leandro.dao.Persistente;
import br.com.leandro.exceptions.DAOException;
import br.com.leandro.exceptions.MaisDeUmRegistroException;
import br.com.leandro.exceptions.TableException;
import br.com.leandro.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {

    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public void excluir(T entiy) throws DAOException;

    /*
    Método para alterar um registro no banco de dados
     */

    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException;

    public Collection<T> buscarTodos() throws DAOException;

}
