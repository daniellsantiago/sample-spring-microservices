package daniellsantiago.study.gatewayservice.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSecurityContext customSecurityContext;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .and()
                .securityContextRepository(customSecurityContext)
                .authorizeExchange()
                    .pathMatchers("/auth/login").permitAll()
                    .pathMatchers("/employee/**").hasRole("USER")
                    .pathMatchers("/department/**").hasRole("USER")
                    .pathMatchers("/organization/**").hasRole("USER")
                .anyExchange().authenticated();
        return http.build();
    }
}
