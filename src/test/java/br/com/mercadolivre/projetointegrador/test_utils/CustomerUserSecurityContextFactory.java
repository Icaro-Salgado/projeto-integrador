package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.security.model.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class CustomerUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomerUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomerUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AppUser user = new AppUser(1L, "mock@user.com", "spring", "springUser", "");
        user.getAuthorities().add(new UserRole(1L, "CUSTOMER"));

        Authentication auth =
                new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}
