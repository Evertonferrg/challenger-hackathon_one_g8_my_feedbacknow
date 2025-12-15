package com.fedbacknow.fedbacknow.controller;

import com.fedbacknow.fedbacknow.domain.Sentiment;
import com.fedbacknow.fedbacknow.dto.SentimentRequestDTO;
import com.fedbacknow.fedbacknow.dto.SentimentResponseDTO;
import com.fedbacknow.fedbacknow.repository.SentimentRepository;
import com.fedbacknow.fedbacknow.service.SentimentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    @Autowired
    private SentimentRepository repository;

    @Autowired
    private SentimentService service;

    @PostMapping
    @Transactional
    public void createSentiment(@RequestBody @Valid SentimentRequestDTO dados){
        repository.save(new Sentiment(dados));
    }


    @GetMapping
    public ResponseEntity<Page<SentimentResponseDTO>> list(Pageable paginacao){
        Page<Sentiment> page = repository.findAll(paginacao);
        Page<SentimentResponseDTO> dtoPage = page.map(SentimentResponseDTO::new);
        return ResponseEntity.ok(dtoPage);
    }
}
