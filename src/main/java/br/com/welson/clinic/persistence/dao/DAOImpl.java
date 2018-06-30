package br.com.welson.clinic.persistence.dao;

import br.com.welson.clinic.persistence.model.AbstractEntity;
import br.com.welson.clinic.utils.FileXMLService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DAOImpl<T extends AbstractEntity> implements DAO<T> {

    private EntityManager entityManager;
    private Class<T> classType;
    private static final FileXMLService HQL_QUERY;

    static {
        HQL_QUERY = new FileXMLService("hql.xml");
    }

    public DAOImpl(EntityManager entityManager, Class<T> classType) {
        this.entityManager = entityManager;
        this.classType = classType;
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T getById(Long id) {
        return entityManager.find(classType, id);
    }

    @Override
    public List<T> listAll() {
        return entityManager.createQuery("SELECT entity FROM " + classType.getSimpleName() + " entity", classType).getResultList();
    }

    @Override
    public List<T> findByHQLQuery(int maxResults, String queryId, Object... values) {
        String hql = HQL_QUERY.findValue(queryId);
        Pattern pattern = Pattern.compile("(:\\w+)");
        Matcher matcher = pattern.matcher(hql);
        List<String> params = new ArrayList<>();
        while (matcher.find()) {
            params.add(matcher.group().replace(":", ""));
        }
        TypedQuery<T> query = entityManager.createQuery(hql, classType);
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(params.get(i), values[i]);
        }
        return maxResults == 0 ? query.getResultList() : query.setMaxResults(maxResults).getResultList();
    }
}
