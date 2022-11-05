package com.mgmetehan.WeTalk.core.repository;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

public abstract class AbstractBaseRepository {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getCurrentSession().getCriteriaBuilder();
    }
}
