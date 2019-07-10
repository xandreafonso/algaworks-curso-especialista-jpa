package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void abrindoEFechandoATransacao() {
        Produto produto = new Produto(); // Variável criada somente para parar com erros nesse @Test.

        entityManager.getTransaction().begin();

        entityManager.persist(produto);
        entityManager.merge(produto);
        entityManager.remove(produto);

        entityManager.getTransaction().commit();
    }

    @Test
    public void inserindoPrimeiroObjeto() {
        Produto produto = new Produto();

        produto.setId(2);
        produto.setNome("Câmera Canon");
        produto.setDescricao("A melhor definição para suas fotos.");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }

    @Test
    public void removendoObjeto() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();

//        entityManager.clear(); Não precisa pq quando remove do banco o EM remove da memória também.

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNull(produtoVerificacao);
    }

    @Test
    public void atualizandoObjeto() {
        Produto produto = new Produto();

        produto.setId(1);
        produto.setNome("Kindle Paperwhite");
        produto.setDescricao("Conheça o novo Kindle.");

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(produto.getNome(), produtoVerificacao.getNome());
        Assert.assertNull(produtoVerificacao.getDescricao());
    }

    @Test
    public void atualizandoObjetoJaGerenciado() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(produto.getNome(), produtoVerificacao.getNome());
    }

    @Test
    public void inserindoObjetoComMerge() {
        Produto produto = new Produto();

        produto.setId(3);
        produto.setNome("Microfone Rode Videomic Pro");
        produto.setDescricao("A melhor qualidade de som.");

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }

    @Test
    public void diferencaPersistEMerge() {
        Produto produto = new Produto();

        produto.setId(4);
        produto.setNome("Microfone Samson");
        produto.setDescricao("A melhor qualidade de som.");

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        Assert.assertTrue(entityManager.contains(produto));

        entityManager.clear();

        Assert.assertFalse(entityManager.contains(produto));

        produto.setNome("Moldura Digital");

        entityManager.getTransaction().begin();
        Produto produtoMerge = entityManager.merge(produto);
        entityManager.getTransaction().commit();

        Assert.assertFalse(entityManager.contains(produto));
        Assert.assertTrue(entityManager.contains(produtoMerge));
    }

    @Test
    public void impedindoOperacaoComOBanco() {
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle 3ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle", produtoVerificacao.getNome());
    }
}
