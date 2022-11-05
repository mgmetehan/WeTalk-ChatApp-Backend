package com.mgmetehan.WeTalk.core.service;

import com.mgmetehan.WeTalk.core.entity.BaseEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityService<T extends BaseEntity, P extends Serializable> {
    T save(T entity);

    List<T> saveAll(List<T> entities);

    T put(P id, T forSave);

    List<T> putAll(List<T> forSaveList);

    void delete(P id);

    T getEntity(P id);

    List<T> getEntities(List<P> ids);

    List<T> all();

    List<T> all(Specification<T> spec);

    PageImpl<T> pageable(Pageable pageable);

    PageImpl<T> pageable(Pageable pageable, Specification<T> spec);
}
