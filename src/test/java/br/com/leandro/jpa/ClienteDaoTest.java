package br.com.leandro.jpa;

import br.com.leandro.dao.ClienteDAO;
import br.com.leandro.dao.IClienteDAO;
import br.com.leandro.domain.Cliente;
import br.com.leandro.exceptions.DAOException;
import br.com.leandro.exceptions.MaisDeUmRegistroException;
import br.com.leandro.exceptions.TableException;
import br.com.leandro.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ClientInfoStatus;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ClienteDaoTest {

    private IClienteDAO clienteDAO;

    private Random rd;

    public ClienteDaoTest(){
        this.clienteDAO = new ClienteDAO();
        rd = new Random();
    }

    @After
    public void end() throws DAOException{
        Collection<Cliente> list = clienteDAO.buscarTodos();
        list.forEach(cliente -> {
            try {
                clienteDAO.excluir(cliente);
            } catch (DAOException e){
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException{
        Cliente cliente = criarCliente();
        clienteDAO.cadastrar(cliente);

        Cliente clienteConsultado = clienteDAO.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);
    }

    @Test
    public void salvarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cli = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cli);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado = clienteDAO.consultar(retorno.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDAO.excluir(cli);

        Cliente clienteConsultado1 = clienteDAO.consultar(retorno.getId());
        Assert.assertNull(clienteConsultado1);
    }

    @Test
    public void excluirCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado = clienteDAO.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDAO.excluir(cliente);
        clienteConsultado = clienteDAO.consultar(cliente.getId());
        Assert.assertNull(clienteConsultado);
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado = clienteDAO.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteConsultado.setNome("Rodrigo Pires");
        clienteDAO.alterar(clienteConsultado);

        Cliente clienteAlterado = clienteDAO.consultar(clienteConsultado.getId());
        Assert.assertNotNull(clienteAlterado);
        Assert.assertEquals("Rodrigo Pires", clienteAlterado.getNome());

        clienteDAO.excluir(cliente);
        clienteConsultado = clienteDAO.consultar(clienteAlterado.getId());
        Assert.assertNull(clienteConsultado);
    }

    @Test
    public void buscarTodos() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente cliente1 = criarCliente();
        Cliente retorno1 = clienteDAO.cadastrar(cliente1);
        Assert.assertNotNull(retorno1);

        Collection<Cliente> list = clienteDAO.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        list.forEach(cli -> {
            try {
                clienteDAO.excluir(cli);
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Collection<Cliente> list1 = clienteDAO.buscarTodos();
        assertTrue(list1 != null);
        assertTrue(list1.size() == 0);
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(rd.nextLong());
        cliente.setNome("Rodrigo");
        cliente.setCidade("São Paulo");
        cliente.setEnd("End");
        cliente.setEstado("SP");
        cliente.setNumero(10);
        cliente.setTel(1199999999L);
        return cliente;
    }

}
