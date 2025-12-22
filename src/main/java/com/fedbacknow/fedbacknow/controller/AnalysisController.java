package com.fedbacknow.fedbacknow.controller;


import com.fedbacknow.fedbacknow.domain.SentimentType;
import com.fedbacknow.fedbacknow.service.SentimentAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analyze")
public class AnalysisController {

    private final SentimentAnalysisService service;

    public AnalysisController(SentimentAnalysisService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> analyze(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        if (text == null || text.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Texto vazio"));
        }

        try {
            // Use o método CORRETO que existe no service
            SentimentType sentiment = service.analyzeSentiment(text);

            // Crie a resposta manualmente
            Map<String, Object> result = new HashMap<>();
            result.put("text", text);
            result.put("sentiment", sentiment.toString());
            result.put("timestamp", LocalDateTime.now());
            result.put("success", true);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Erro na análise",
                            "message", e.getMessage(),
                            "success", false
                    ));
        }
    }

}
