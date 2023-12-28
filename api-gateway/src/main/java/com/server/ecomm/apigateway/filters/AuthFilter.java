package com.server.ecomm.apigateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.ecomm.apigateway.model.Authorities;
import com.server.ecomm.apigateway.model.ConnValidationResponse;
import com.server.ecomm.apigateway.model.ErrorResponse;
import com.server.ecomm.apigateway.ultils.SecurityConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
            log.info(SecurityConstants.HEADER + " " + bearerToken);

            if (isSecured.test(request)) {
                return webClientBuilder.build().get()                                               // build a get request
                        .uri("lb://authentication-service/api/v1/validateToken")
                        .header(SecurityConstants.HEADER, bearerToken)
                        .retrieve().bodyToMono(ConnValidationResponse.class)                        // execute the request and deserialize its response body to ConnValidationResponse.class
                        .map(response -> {                                                          // add info to the header of incoming request
                            exchange.getRequest().mutate().header("email", response.getEmail());
                            exchange.getRequest().mutate().header("token", response.getToken());
                            exchange.getRequest().mutate().header("authorities", response.getAuthorities().stream().map(Authorities::getAuthority)
                                    .reduce("", (a,b) -> a + "," + b));
                            return exchange;
                        })
                        .flatMap(chain::filter).onErrorResume(error ->{
                            log.info("Error happened " + error.toString());
                            HttpStatusCode errorCode;
                            String errorMsg;
                            if(error instanceof WebClientResponseException){
                                WebClientResponseException webClientResponseException = (WebClientResponseException) error;
                                errorCode = webClientResponseException.getStatusCode();
                                errorMsg = webClientResponseException.getMessage();
                            } else {
                                errorCode = HttpStatus.BAD_GATEWAY;
                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
                            return onError(exchange, String.valueOf(errorCode.value()), errorMsg, errorCode);
                        });                                                    // return the exchange object to the next filter in the chain
            }
            log.info("Route not secured!");
            return chain.filter(exchange);
        });
    }

    private Mono<? extends Void> onError(ServerWebExchange exchange, String errCode, String errMsg, HttpStatusCode errorStatusCode) {
        log.error("JWT Authentication Failed");
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorStatusCode);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(errCode, errMsg);
        byte[] errorResponseByte = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            errorResponseByte = objectMapper.writeValueAsBytes(errorResponse);
        }
        catch (JsonProcessingException e) {
            log.error("Error while serialize error response", e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(errorResponseByte != null ? errorResponseByte : new byte[0]);
        return response.writeWith(Mono.just(buffer));
    }

    @Autowired
    @Qualifier("excludedUrls")
    private List<String> excludedUrls;
    public Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));

    @NoArgsConstructor
    public static class Config{

    }
}


