package br.com.welson.clinic.interceptors;

import br.com.welson.clinic.annotations.Transactional;
import br.com.welson.clinic.errors.DefaultError;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Response;

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
            return new DefaultError(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR).getResponse();
        }
        return o;
    }
}
