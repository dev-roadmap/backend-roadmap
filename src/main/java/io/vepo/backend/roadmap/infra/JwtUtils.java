package io.vepo.backend.roadmap.infra;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.vepo.backend.roadmap.usuarios.Usuario;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class JwtUtils {

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    PrivateKey privateKey;

    @Inject
    @ConfigProperty(name = "mp.jwt.expiration.time.in.minutes", defaultValue = "1000")
    int expirationTime;

    public String generate(Usuario usuario) {
        try {
            JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
                    .issuer(issuer)
                    .subject(usuario.getId().toString())
                    .issueTime(Date.from(Instant.now()))
                    .notBeforeTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plus(expirationTime, ChronoUnit.MINUTES)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("nickname", usuario.getUsername())
                    .claim("email", usuario.getEmail())
                    .claim("groups", usuario.getRoles())
                    .build();

            JWSSigner signer = new RSASSASigner(privateKey);
            JWSHeader jwtHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();

            SignedJWT signedJWT = new SignedJWT(jwtHeader, jwtClaims);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
