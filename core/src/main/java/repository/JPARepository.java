package repository;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * An utility abstract class for implementing JPA repositories. Based on
 * JPARepository.
 *
 * @param <T>  Entity type that we want to build a repository for.
 * @param <ID> The key type of the entity.
 */
public abstract class JPARepository<T, ID extends Serializable> {

    @PersistenceUnit
    private static EntityManagerFactory entityManagerFactory;
    private final Class<T> entityClass;
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public JPARepository() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    protected EntityManagerFactory entityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName());
        }
        return entityManagerFactory;
    }

    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public T update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        try {
            EntityManager em = entityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(entity);
            tx.commit();
            em.close();
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public T detach(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        try {
            EntityManager em = entityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.detach(entity);
            em.close();
            return entity;
        } catch (Exception e) {
            return null;
        }
    }

    public T add(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = entityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();
        return entity;
    }


    public T findById(ID id) {
        return this.entityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        Query query = entityManager().createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e");
        return (List<T>) query.getResultList();
    }

    protected abstract String persistenceUnitName();
}
