package com.example.payment.service;

import com.example.payment.dto.PaymentInfoDto;
import com.example.payment.dto.PaymentLogDto;
import com.example.payment.dto.PaymentStatusDto;
import com.example.payment.dto.ValidationResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class PayPalService {
    @Value("${paypal.environment}")
    private String environment;

    @Value("${paypal.client_id}")
    private String clientId;

    @Value("${paypal.client_secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public PayPalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getEndpointUrl() {
        return "sandbox".equals(environment) ? "https://api-m.sandbox.paypal.com" : "https://api-m.paypal.com";
    }

    public String getAccessToken() throws Exception {
        String auth = clientId + ":" + clientSecret;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + Base64Utils.encodeToString(auth.getBytes(StandardCharsets.UTF_8)));
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(getEndpointUrl() + "/v1/oauth2/token", HttpMethod.POST,
                    entity, Map.class);
            Map<String, Object> body = response.getBody();
            System.out.println(response.getBody()); // Simplified; parse JSON to get actual access token

            return body != null ? (String) body.get("access_token") : null;
        } catch (HttpClientErrorException e) {
            throw new Exception("Failed to get access token. Status: " + e.getStatusCode(), e);
        }

    }

    public Map<String, Object> createOrder(double amount) throws Exception {
        String accessToken = getAccessToken();
        String apiUrl = getEndpointUrl() + "/v2/checkout/orders";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("intent", "CAPTURE");
        orderData.put("purchase_units", new Object[] {
                new HashMap<String, Object>() {
                    {
                        put("amount", new HashMap<String, Object>() {
                            {
                                put("currency_code", "USD");
                                put("value", amount);
                            }
                        });
                    }
                }
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(orderData, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new Exception("Failed to create PayPal order. Status: " + e.getStatusCode(), e);
        }
    }

    public Map<String, Object> completeOrder(String orderId)
            throws Exception {
        String accessToken = getAccessToken();
        String apiUrl = getEndpointUrl() + "/v2/checkout/orders/" + orderId + "/" + "CAPTURE".toLowerCase();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");

        Map<String, Object> paymentSource = new HashMap<>();
        paymentSource.put("payment_source", new HashMap<String, Object>() {
            {
                put("card", new HashMap<String, Object>() {
                    {
                        put("name", "Ryan Li");
                        put("number", "4032034462034987");
                        put("expiry", "2024-12");
                        put("security_code", "345");
                        put("last_digits", "213");
                        put("brand", "VISA");
                    }
                });
            }
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(paymentSource, headers);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Failed to complete PayPal order. Status: " + response.getStatusCode());
            }
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new Exception("Error completing PayPal order: " + e.getMessage(), e);
        }
    }

    // public PaymentStatusDto processPayment() {
    // try {
    // Map<String, Object> createdOrder = createOrder();
    // // Map<String, Object> links = (Map<String, Object>)
    // createdOrder.get("links");
    // // //String approveUrl = links != null ? ((List<Map<String, String>>)
    // // links.get("href")).stream()
    // // .filter(link -> "approve".equals(link.get("rel")))
    // // .findFirst()
    // // .map(link -> link.get("href"))
    // // .orElse(null);
    // // Optionally handle the approve URL as needed

    // Map<String, Object> completedOrder = completeOrder((String)
    // createdOrder.get("id"), "CAPTURE");
    // System.out.println(completedOrder);
    // PaymentStatusDto paymentStatus = new PaymentStatusDto();
    // paymentStatus.setPaymentId((String) completedOrder.get("id"));
    // paymentStatus.setSuccess(true);
    // paymentStatus.setAmount(100.0);
    // // paymentStatus.setPaymentMethod(((List<Map<String, String>>)
    // // completedOrder.get("purchase_units")).get(0)
    // // .get("payments").get("captures").get(0).get("payment_method"));
    // // paymentStatus
    // // .setPaymentCompleteTime(((List<Map<String, String>>)
    // // completedOrder.get("purchase_units")).get(0)
    // // .get("payments").get("captures").get(0).get("create_time"));
    // // paymentStatus.setOrder(completedOrder);
    // return paymentStatus;

    // } catch (Exception e) {
    // PaymentStatusDto paymentStatus = new PaymentStatusDto();
    // paymentStatus.setSuccess(false);
    // paymentStatus.setAmount(100.0);
    // paymentStatus.setError(e.getMessage());
    // return paymentStatus;
    // }
    // }

    public Map<String, Object> toMap(PaymentStatusDto paymentStatusDto) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = paymentStatusDto.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Ensure private fields are accessible
            try {
                Object value = field.get(paymentStatusDto);
                result.put(field.getName(), value); // Put the field name and value in the map
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
