package daniellsantiago.study.commonclasses.security.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@Getter
@Setter
public class JwtConfiguration {

    private String loginUrl = "/login/**";
    private int expiration = 3000;
    private String privateKey = "qxBEEQv7E8aviX1KUcd0iF5ve5COUPAr";
    private String type = "signed";
    private Header header = new Header();

    @Getter
    @Setter
    public static class Header {
        private String name = "Authorization";
        private String prefix = "Bearer ";
    }
}
