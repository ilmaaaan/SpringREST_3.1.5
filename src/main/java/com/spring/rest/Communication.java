package com.spring.rest;


import com.spring.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private String result = "";
    private String sessionId;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private RestTemplate restTemplate;

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        sessionId = responseEntity.getHeaders().get("Set-Cookie").stream().collect(Collectors.toList()).toString();
        sessionId = sessionId.substring(1, sessionId.length()-1);
        List<User> users = responseEntity.getBody();
        return users;
    }
    public void saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);

        HttpEntity<User> httpUser = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, httpUser, String.class);
        result+=responseEntity.getBody();
    }

    public void updateUser(User editUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);

        HttpEntity<User> httpUser = new HttpEntity<>(editUser, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpUser, String.class);
        result+=responseEntity.getBody();
    }

    public void deleteUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> httpUser = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, httpUser,String.class);
        result+=responseEntity.getBody();
        System.out.println(result);
    }
}
