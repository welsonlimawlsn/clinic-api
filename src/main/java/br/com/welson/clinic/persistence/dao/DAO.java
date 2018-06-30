package br.com.welson.clinic.persistence.dao;

import br.com.welson.clinic.persistence.model.AbstractEntity;

import java.util.List;

public interface DAO<T extends AbstractEntity> {

    void save(T entity);

    void update(T entity);

    void remove(T entity);

    T getById(Long id);

    List<T> listAll();

    List<T> findByHQLQuery(int maxResults, String queryId, Object... values);
}
