package daniellsantiago.study.gatewayservice.security.config;

import com.nimbusds.jwt.SignedJWT;
import daniellsantiago.study.commonclasses.security.property.JwtConfiguration;
import daniellsantiago.study.commonclasses.security.token.TokenConverter;
import daniellsantiago.study.commonclasses.security.util.SecurityUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class CustomSecurityContext implements ServerSecurityContextRepository {
    private final JwtConfiguration jwtConfiguration;
    private final TokenConverter tokenConverter;

    public CustomSecurityContext(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        this.jwtConfiguration = jwtConfiguration;
        this.tokenConverter = tokenConverter;
    }

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        List<String> headers = serverWebExchange.getRequest().getHeaders().getOrEmpty(jwtConfiguration.getHeader().getName());
        if(headers.isEmpty())
            return Mono.empty();

        String token = headers.get(0);

        if(!token.startsWith(jwtConfiguration.getHeader().getPrefix()))
            return Mono.empty();

        token = token.replace(jwtConfiguration.getHeader().getPrefix(), "").trim();

        Authentication authentication = SecurityUtil.getAuthentication(decryptValidating(token));

        return Mono.just(new SecurityContextImpl(authentication));
    }

    @SneakyThrows
    private SignedJWT decryptValidating(String encryptedToken) {
        String signedToken = tokenConverter.decryptToken(encryptedToken);
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }
}
