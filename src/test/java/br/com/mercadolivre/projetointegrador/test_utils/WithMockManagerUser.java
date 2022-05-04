package br.com.mercadolivre.projetointegrador.test_utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = ManagerUserSecurityContextFactory.class)
public @interface WithMockManagerUser {

  String id() default "1";

  String userName() default "springuser";

  String name() default "spring";
}
