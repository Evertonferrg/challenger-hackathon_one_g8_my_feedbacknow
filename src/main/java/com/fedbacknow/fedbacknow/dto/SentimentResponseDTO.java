package com.fedbacknow.fedbacknow.dto;

import com.fedbacknow.fedbacknow.domain.Sentiment;
import com.fedbacknow.fedbacknow.domain.SentimentType;

import java.time.LocalDateTime;

public record SentimentResponseDTO(Long id, String text, String productName, SentimentType sentiment,
                                   LocalDateTime createdAt) {
    public SentimentResponseDTO(Sentiment entity){
        this(
                entity.getId(),
                entity.getText(),
                entity.getProductName(),
                entity.getSentiment(),
                entity.getCreatedAt()
        );
    }
}
