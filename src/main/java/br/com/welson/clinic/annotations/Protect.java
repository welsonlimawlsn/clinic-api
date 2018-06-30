package br.com.welson.clinic.annotations;

import br.com.welson.clinic.persistence.model.User;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
@InterceptorBinding
public @interface Protect {

    @Nonbinding
    Class<? extends User>[] value() default {User.class};
}
