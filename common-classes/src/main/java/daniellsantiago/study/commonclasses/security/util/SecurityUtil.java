package daniellsantiago.study.commonclasses.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import daniellsantiago.study.commonclasses.model.ApplicationUser;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SecurityUtil {
    private SecurityUtil() {

    }

    @SneakyThrows
    public static Authentication getAuthentication(SignedJWT signedJWT) {
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String username = claims.getSubject();
        if(username == null)
            throw new JOSEException("Username missing from JWT");

        if(!new Date().before(claims.getExpirationTime()))
            throw new JOSEException("Token Expired!");

        List<String> authorities = claims.getStringListClaim("authorities");

        ApplicationUser user = ApplicationUser.builder()
                .id(claims.getLongClaim("userId"))
                .username(username)
                .role(String.join(",", authorities))
                .build();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                null, createAuthorities(authorities));

        auth.setDetails(signedJWT.serialize());

        return auth;
    }

    private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
}
