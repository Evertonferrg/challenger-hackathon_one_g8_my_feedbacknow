package com.fedbacknow.fedbacknow.controller;
import com.fedbacknow.fedbacknow.domain.SentimentType;
import com.fedbacknow.fedbacknow.dto.SentimentRequestDTO;
import com.fedbacknow.fedbacknow.dto.SentimentResponseDTO;
import com.fedbacknow.fedbacknow.service.SentimentAnalysisService; // Nome CORRETO
import com.fedbacknow.fedbacknow.service.SentimentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    private final SentimentAnalysisService service;
    private long idCounter = 1000L; // Use primitivo 'long' em vez de AtomicLong

    public SentimentController(SentimentAnalysisService service) {
        this.service = service;
    }

    @PostMapping("/analyze")
    public SentimentResponseDTO analyze(@Valid @RequestBody SentimentRequestDTO request) {
        SentimentType sentiment = service.analyzeSentiment(request.text());

        // Incrementa o contador
        long generatedId = idCounter++;

        // Se precisar de AtomicLong por thread safety:
        // private final AtomicLong idCounter = new AtomicLong(1000L);
        // long generatedId = idCounter.getAndIncrement();

        return new SentimentResponseDTO(
                generatedId,          // long primitivo (ok para Long no record)
                request.text(),
                request.productName(),
                sentiment,
                LocalDateTime.now()
        );
    }
}
