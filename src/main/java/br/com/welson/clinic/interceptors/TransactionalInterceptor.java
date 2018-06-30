package br.com.welson.clinic.interceptors;

import br.com.welson.clinic.annotations.Transactional;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transactional
public class TransactionalInterceptor {

    @Inject
    private EntityManager entityManager;

    @AroundInvoke
    public Object invoke(InvocationContext invocationContext) {
        Object o = null;
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            o = invocationContext.proceed();
            if (!transaction.getRollbackOnly()) {
                transaction.commit();
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return o;
    }
}
