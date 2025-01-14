package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);

        ResponseEntity <String> responseEntity = restTemplate.getForEntity(url, String.class);
        String sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(sessionId);

        User user3 = new User(3l, (byte)28, "Brown", "James");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", sessionId);
        HttpEntity <User> httpEntity = new HttpEntity<>(user3, httpHeaders);
        ResponseEntity <String> firstPartOfCode = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(firstPartOfCode.getBody());
    }
}