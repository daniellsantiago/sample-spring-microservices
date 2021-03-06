package daniellsantiago.study.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("daniellsantiago.study")
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("employee-service", p -> p
					.path("/employee/**")
						.filters(f -> f
							 .hystrix(config -> config.setName("employeeCB")
									.setFallbackUri("forward:/employee/fallback")))
						.uri("lb://employee-service"))
				.route("department-service", p -> p
					.path("/department/**")
						.filters(f -> f
							.hystrix(config -> config.setName("departmentCB")
									.setFallbackUri("forward:/department/fallback")))
					.uri("lb://department-service"))
				.route("organization-service", p -> p
					.path("/organization/**")
						.filters(f -> f
								.hystrix(config -> config.setName("organizationCB")
									.setFallbackUri("forward:/organization/fallback")))
					.uri("lb://organization-service"))
				.route("auth-service", p -> p
						.path("/auth/**")
						.filters(f -> f.rewritePath("/auth/login", "/login"))
						.uri("lb://auth-service"))
				.build();
	}
}
