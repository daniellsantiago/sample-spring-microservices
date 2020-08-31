package daniellsantiago.study.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("employee-service", p -> p
					.path("/employee/**")
					.uri("lb://employee-service"))
				.route("department-service", p -> p
					.path("/department/**")
					.uri("lb://department-service"))
				.route("organization-service", p -> p
					.path("/organization/**")
					.uri("lb://organization-service"))
				.build();
	}

}
