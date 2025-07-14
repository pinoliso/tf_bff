package com.duoc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.duoc.models.User;

public class AzureB2CService {

    public String getAzureB2CToken() {
        String url = "https://login.microsoftonline.com/54b64703-5cae-45cf-8852-ee61cb6d25f8/oauth2/v2.0/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", System.getenv("B2C_CLIENT"));
        map.add("scope", "https://graph.microsoft.com/.default");
        map.add("client_secret", System.getenv("B2C_SECRET"));
        map.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        System.out.println(response.getBody().get("access_token"));
        return (String) response.getBody().get("access_token");
    }
    
    public String registerB2CUser(User user, String accessToken) {
        String url = "https://graph.microsoft.com/v1.0/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        // Armar el body
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("accountEnabled", true);
        userMap.put("displayName", user.getName());
        userMap.put("identities", List.of(
            Map.of(
                "signInType", "emailAddress",
                "issuer", "pinoliso.onmicrosoft.com",
                "issuerAssignedId", user.getEmail()
            )
        ));
        userMap.put("passwordProfile", Map.of(
            "password", user.getPassword(),
            "forceChangePasswordNextSignIn", false
        ));
        userMap.put("passwordPolicies", "DisablePasswordExpiration");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userMap, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        System.err.println(response.getBody().get("id"));
        return (String) response.getBody().get("id");
    }
}
