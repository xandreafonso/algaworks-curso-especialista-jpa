package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ConsultandoRegistrosTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        Produto produto = entityManager.find(Produto.class, 1);
//        Produto produto= entityManager.getReference(Produto.class, 1);

        Assert.assertNotNull(produto);
        Assert.assertEquals("Kindle", produto.getNome());
    }

    @Test
    public void atualizandoAReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        String nomeEsperado = produto.getNome();

        produto.setNome("Microfone Samson");

        entityManager.refresh(produto);

        Assert.assertEquals(nomeEsperado, produto.getNome());
    }

    @Test
    public void consultaComJPQL() {
        Query query = entityManager.createQuery("select p from Produto p"); // SQL: select * from produto
        List lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        TypedQuery<Produto> typedQuery = entityManager.createQuery("select p from Produto p", Produto.class); // SQL: select * from produto
        List<Produto> listaTipada = typedQuery.getResultList();

        Assert.assertFalse(listaTipada.isEmpty());

        TypedQuery<Produto> typedQueryProduto = entityManager.createQuery("select p from Produto p where p.id = 1", Produto.class); // SQL: select * from produto where id = 1
        Produto produto = typedQueryProduto.getSingleResult();

        Assert.assertNotNull(produto);
    }

    @Test
    public void consultaComCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        criteriaQuery.select(root);
        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        root = criteriaQuery.from(Produto.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
        typedQuery = entityManager.createQuery(criteriaQuery);
        Produto produto = typedQuery.getSingleResult();
        Assert.assertNotNull(produto);
    }
}
