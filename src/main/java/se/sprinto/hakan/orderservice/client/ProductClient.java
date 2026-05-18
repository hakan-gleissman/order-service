package se.sprinto.hakan.orderservice.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ProductInfo> decreaseStock(List<ProductStockItemRequest> items, String bearerToken) {
        try {
            return restClient.post()
                    .uri("/products/stock/decrease")
                    .header(HttpHeaders.AUTHORIZATION, bearerToken)
                    .body(new ProductStockUpdateRequest(items))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().is4xxClientError()) {
                throw new ResponseStatusException(exception.getStatusCode(), exception.getResponseBodyAsString());
            }
            throw new ResponseStatusException(BAD_GATEWAY, "Could not call product-service");
        }
    }
}
