package com.server.ecomm.apigateway.filters;

import com.server.ecomm.apigateway.model.Authorities;
import com.server.ecomm.apigateway.model.ConnValidationResponse;
import com.server.ecomm.apigateway.ultils.SecurityConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;
    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // `exchange` is the incoming request, `chain` is a series of filters
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst(SecurityConstants.HEADER);
            log.info("BearerToken" + bearerToken);

            if (isSecured.test(request)) {
                return webClientBuilder.build().get()                                               // build a get request
                        .uri("lb://authentication-service/api/v1/validateToken")
                        .header("token", bearerToken)
                        .retrieve().bodyToMono(ConnValidationResponse.class)                        // execute the request and deserialize its response body to ConnValidationResponse.class
                        .map(response -> {                                                          // add info to the header of incoming request
                            exchange.getRequest().mutate().header("email", response.getEmail());
                            exchange.getRequest().mutate().header("token", response.getToken());
                            exchange.getRequest().mutate().header("authorities", response.getAuthorities().stream().map(Authorities::getAuthority)
                                    .reduce("", (a,b) -> a + "," + b));
                            return exchange;
                        })
                        .flatMap(chain::filter);                                                    // return the exchange object to the next filter in the chain
            }
            return chain.filter(exchange);
        });
    }

    @Autowired
    @Qualifier("excludedUrls")
    private List<String> excludedUrls;
    public Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    @NoArgsConstructor
    public static class Config{

    }
}


