package com.duoc.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.models.Item;
import com.duoc.repositories.ItemRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AzureOpenAIService {

    @Value("${ENDPOINT_URL:https://duoc-openai.openai.azure.com/}")
    private String endpoint;

    @Value("${DEPLOYMENT_NAME:gpt-4.1}")
    private String deployment;

    @Value("${AZURE_OPENAI_API_KEY}")
    private String subscriptionKey;

    private final RestTemplate restTemplate = new RestTemplate();



    public String contarBotellas(MultipartFile file) throws Exception {
        // Leer la imagen recibida y convertir a base64
        byte[] imageBytes = file.getBytes();
        String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
        // ItemService itemService = new ItemService();
        // List<Item> items = itemService.findAll();
        String itemListString = ""; 
        // for (Item item : items) {
        //     itemListString += item.getReference() + ", ";
        // }
        
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a material waste counter, based on the image make a json with the number of object founded for each material in the following list: " + itemListString + ".");
        messages.add(systemMsg);

        Map<String, Object> userMsg = new HashMap<>();
        userMsg.put("role", "user");

        List<Object> contentList = new ArrayList<>();
        Map<String, String> textObj = new HashMap<>();
        textObj.put("type", "text");
        // textObj.put("text", "How many plastic bottles are there.");
        contentList.add(textObj);

        Map<String, Object> imageObj = new HashMap<>();
        imageObj.put("type", "image_url");
        Map<String, String> imgUrlObj = new HashMap<>();
        imgUrlObj.put("url", "data:image/jpeg;base64," + encodedImage);
        imageObj.put("image_url", imgUrlObj);
        contentList.add(imageObj);

        userMsg.put("content", contentList);
        messages.add(userMsg);

        // Request body igual que antes
        Map<String, Object> body = new HashMap<>();
        body.put("model", deployment);
        body.put("messages", messages);
        body.put("max_tokens", 100);
        body.put("temperature", 1);
        body.put("top_p", 1);
        body.put("frequency_penalty", 0);
        body.put("presence_penalty", 0);
        body.put("stream", false);

        String url = endpoint + "/openai/deployments/" + deployment + "/chat/completions?api-version=2025-01-01-preview";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", subscriptionKey);

        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(body), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode choices = root.path("choices");
        if (choices.isArray() && choices.size() > 0) {
            JsonNode messageContent = choices.get(0).path("message").path("content");
            if (!messageContent.isMissingNode()) {
                return messageContent.asText();
            }
        }
        return null;
    }
}

