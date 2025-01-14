package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String usersURL = "http://94.198.50.185:7081/api/users";
        String response = restTemplate.getForObject(usersURL, String.class);
        System.out.println(response);

        ResponseEntity <String> responseEntity = restTemplate.getForEntity(usersURL, String.class);
        String sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(sessionId);

        User user3 = new User(3l, (byte)28, "Brown", "James");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", sessionId);
        HttpEntity <User> httpEntity = new HttpEntity<>(user3, httpHeaders);
        ResponseEntity <String> firstPartOfCode = restTemplate.exchange(usersURL, HttpMethod.POST, httpEntity, String.class);
        System.out.println(firstPartOfCode.getBody());

        user3.setName("Thomas");
        user3.setLastName("Shelby");
        ResponseEntity <String> secondPartOfCode = restTemplate.exchange(usersURL, HttpMethod.PUT, httpEntity, String.class);
        System.out.println(secondPartOfCode.getBody());

        String deleteUserUrl = "http://94.198.50.185:7081/api/users/3";
        ResponseEntity<String> thirdPartOfCode = restTemplate.exchange(deleteUserUrl, HttpMethod.DELETE, httpEntity, String.class);
        System.out.println(thirdPartOfCode.getBody());
        System.out.println("------");
        String fullCode = firstPartOfCode.getBody() + secondPartOfCode.getBody() + thirdPartOfCode.getBody();
        System.out.println(fullCode);
    }
}