package com.mgmetehan.WeTalk.core.service;

import com.mgmetehan.WeTalk.core.entity.BaseEntity;
import com.mgmetehan.WeTalk.core.exception.NotFoundException;
import com.mgmetehan.WeTalk.core.repository.BaseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractEntityService<T extends BaseEntity, P extends Serializable> {
    private Specification<T> spec = null;

    BaseEntity entity = new BaseEntity() {
        @Override
        public <B extends BaseEntity> void update(B entity) {
            throw new UnsupportedOperationException();
        }
    };

    public abstract BaseJpaRepository<T, P> getJpaRepository();

    protected T preSave(T entity) {
        return entity;
    }

    protected void saveRollback(T entity) {
    }

    protected void putRollback(P id, T forSave) {
    }

    protected T prePut(T theReal, T forSave) {
        log.debug("The read: {} and for save: {}", theReal, forSave);
        return forSave;
    }

    protected T preDelete(T entity) {
        return entity;
    }

    protected void postSave(T entity) {
        log.debug("PostSave running... {}", entity);
    }

    protected void postPut(T entity) {
        log.debug("PostSave running... {}", entity);
    }

    @Transactional
    public T save(T entity) {
        try {
            this.preSave(entity);
            this.getJpaRepository().save(entity);
            this.postSave(entity);
            return entity;
        } catch (Exception ex) {
            saveRollback(entity);
            throw ex;
        }
    }

    public List<T> saveAll(List<T> entities) {
        for (T e : entities) {
            this.preSave(e);
        }
        this.getJpaRepository().saveAll(entities);
        for (T e : entities) {
            this.postSave(e);
        }
        return entities;
    }

    @Transactional
    public T put(P id, T forSave) {
        var theReal = this.getEntity(id);
        this.prePut(theReal, forSave);

        try {
            theReal.update(forSave);
            this.getJpaRepository().save(theReal);
            this.postPut(theReal);
            return theReal;
        } catch (Exception ex) {
            putRollback(id, forSave);
            throw ex;
        }
    }

    @Transactional
    public List<T> putAll(List<T> forSaveList) {
        List<T> updatedList = new ArrayList<>();
        forSaveList.forEach(forSave -> {
            var theReal = this.getEntity((P) forSave.getId());
            this.prePut(theReal, forSave);
            theReal.update(forSave);
            updatedList.add(theReal);
        });
        return this.getJpaRepository().saveAll(updatedList);
    }

    @Transactional
    public void delete(P id) {
        var byId = this.getEntity(id);
        this.preDelete(byId);

        this.getJpaRepository().delete(byId);
    }

    public T getEntity(P id) {
        Optional<T> entityOpt = this.getJpaRepository().findById(id);
        if (!entityOpt.isPresent()) {
            String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
            throw new NotFoundException(className + " not found.");
        } else {
            return entityOpt.get();
        }
    }

    public List<T> getEntities(List<P> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return this.getJpaRepository().findAllById(ids);
    }

    public Specification<T> getCustomSpecification() {
        return this.spec;
    }

    private void setSpecification(Specification<T> spec) {
        this.spec = spec;
    }

    public List<T> all() {
        Specification<T> spcf = getCustomSpecification();
        if (Objects.isNull(spcf))
            return this.getJpaRepository().findAll();
        else
            return this.getJpaRepository().findAll(spcf);
    }

    public List<T> all(Specification<T> spec) {
        synchronized (this) {
            setSpecification(spec);
            List<T> all = all();
            setSpecification(null);
            return all;
        }
    }

    public PageImpl<T> pageable(Pageable pageable) {
        Specification<T> spcf = getCustomSpecification();
        Page<T> resp;
        long count = 0;
        if (Objects.isNull(spcf)) {
            resp = this.getJpaRepository().findAll(pageable);
            count = this.getJpaRepository().count();
        } else {
            resp = this.getJpaRepository().findAll(spcf, pageable);
            count = this.getJpaRepository().count(spcf);
        }
        return new PageImpl<>(resp.getContent(), pageable, count);
    }

    public PageImpl<T> pageable(Pageable pageable, Specification<T> spec) {
        synchronized (this) {
            setSpecification(spec);

            PageImpl<T> resp = pageable(pageable);

            setSpecification(null);

            return resp;
        }
    }
}