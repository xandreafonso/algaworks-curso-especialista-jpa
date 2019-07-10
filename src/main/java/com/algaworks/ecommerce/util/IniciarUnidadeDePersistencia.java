package com.algaworks.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Faça seus testes aqui.

        entityManager.close();
        entityManagerFactory.close();
    }
}
