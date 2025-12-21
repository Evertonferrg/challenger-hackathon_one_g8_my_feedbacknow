package com.fedbacknow.fedbacknow.service;

import com.fedbacknow.fedbacknow.dto.SentimentRequestDTO;
import com.fedbacknow.fedbacknow.dto.SentimentResponseDTO;
import com.fedbacknow.fedbacknow.repository.SentimentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SentimentService {

    private final SentimentRepository repository;

    public SentimentService(SentimentRepository repository){
        this.repository = repository;
    }

    public List<SentimentResponseDTO> list(){
        return repository.findAll()
                .stream()
                .map(SentimentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public String analyze(String text){
        if (text.toLowerCase().contains("péssimo")){
            return "NEGATIVE";
        }

        if (text.toLowerCase().contains("excelente")){
            return "POSITIVE";
        }
        return  "NEUTRAL";
    }
}
