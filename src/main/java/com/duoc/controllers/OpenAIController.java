package com.duoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.duoc.services.AzureOpenAIService;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @Autowired
    private final AzureOpenAIService azureOpenAIService;

    public OpenAIController(AzureOpenAIService azureOpenAIService) {
        this.azureOpenAIService = azureOpenAIService;
    }

    @PostMapping("/contar-botellas")
    public ResponseEntity<String> contarBotellas(@RequestParam("file") MultipartFile file) throws Exception {
        // Procesa el archivo y pásalo al servicio
        String respuesta = azureOpenAIService.contarBotellas(file);
        return ResponseEntity.ok(respuesta);
    }
}
