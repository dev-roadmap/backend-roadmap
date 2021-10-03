package io.vepo.backend.roadmap.infra;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class BasicAuthIdentityProvider implements IdentityProvider<CredenciaisUsuario> {

    @Override
    public Class<CredenciaisUsuario> getRequestType() {
        return CredenciaisUsuario.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(CredenciaisUsuario request, AuthenticationRequestContext context) {
        return Uni.createFrom().item(
                QuarkusSecurityIdentity.builder().setPrincipal(new QuarkusPrincipal(request.getUsername())).build());
    }

}
