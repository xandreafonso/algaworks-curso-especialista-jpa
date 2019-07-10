package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Estoque;
import org.junit.Assert;
import org.junit.Test;

public class EstrategiaChavePrimariaTest extends EntityManagerTest {

    @Test
    public void estrategiaAuto() {
        Estoque estoque = new Estoque();
        estoque.setProdutoId(1);
        estoque.setQuantidade(10);

        entityManager.getTransaction().begin();
        entityManager.persist(estoque);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Estoque estoqueVerificacao = entityManager.find(Estoque.class, estoque.getId());
        Assert.assertNotNull(estoqueVerificacao);
    }

    @Test
    public void estrategiaTable() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaVerificacao);
    }
}
