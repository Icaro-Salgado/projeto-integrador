package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.warehouse.model.AppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import java.util.Collections;

public class CustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AppUser user = new AppUser(
                1L,
                "mock@user.com",
                "spring",
                "springUser",
                ""
        );

        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, "password", Collections.emptyList());

        context.setAuthentication(auth);
        return context;
    }
}
