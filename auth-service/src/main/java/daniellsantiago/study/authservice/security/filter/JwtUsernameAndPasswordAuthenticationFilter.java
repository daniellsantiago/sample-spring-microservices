package daniellsantiago.study.authservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import daniellsantaigo.study.commonclasses.model.ApplicationUser;
import daniellsantaigo.study.commonclasses.property.JwtConfiguration;
import daniellsantiago.study.authservice.security.token.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenCreator tokenCreator;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        log.info("Attempting authentication..");
        if(user == null)
            throw new UsernameNotFoundException("User not found");

        UsernamePasswordAuthenticationToken userToBeAuthenticated =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        userToBeAuthenticated.setDetails(user);

        return authenticationManager.authenticate(userToBeAuthenticated);

    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        log.info("Successful authentication!");
        SignedJWT signedJwt = tokenCreator.createSignedJwt(authResult);
        String encryptedToken = tokenCreator.encryptToken(signedJwt);

        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());

        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + encryptedToken);
    }
}
