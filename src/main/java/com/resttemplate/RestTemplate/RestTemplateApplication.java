package com.resttemplate.RestTemplate;

import com.resttemplate.RestTemplate.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
public class RestTemplateApplication {

    private static String url1 = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {

//        SpringApplication.run(RestTemplateApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> getAllUsers = restTemplate.getForEntity(
                url1,
                String.class);

        String sessionId = getAllUsers.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.COOKIE, sessionId);

        User user1 = new User(3L, "James", "Brown", (byte) 19);
        HttpEntity<User> saveUserRequest = new HttpEntity<>(user1, httpHeaders);
        ResponseEntity<String> save = restTemplate.postForEntity(
                url1,
                saveUserRequest,
                String.class);

        User user2 = new User(3L, "Thomas", "Shelby", (byte) 19);
        HttpEntity<User> updateUser = new HttpEntity<>(user2, httpHeaders);
        ResponseEntity<String> update = restTemplate.exchange(
                url1,
                HttpMethod.PUT,
                updateUser,
                String.class);

        String url2 = "http://94.198.50.185:7081/api/users/3";
        ResponseEntity<String> delete = restTemplate.exchange(
                url2,
                HttpMethod.DELETE,
                new HttpEntity<>(httpHeaders),
                String.class);

        System.out.println(getAllUsers.getStatusCode());
        System.out.println(save.getStatusCode());
        System.out.println(update.getStatusCode());
        System.out.println(delete.getStatusCode());


        String concatenatedCode = save.getBody() + update.getBody() + delete.getBody();
        System.out.println(concatenatedCode);


    }

}
