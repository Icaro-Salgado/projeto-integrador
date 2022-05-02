package br.com.mercadolivre.projetointegrador.test_utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomerUserSecurityContextFactory.class)
public @interface WithMockCustomerUser {

    String id() default "1";

    String userName() default "springuser";

    String name() default "spring";
}
