package com.fedbacknow.fedbacknow.dto;

import com.fedbacknow.fedbacknow.domain.SentimentType;

import java.time.LocalDateTime;

public record SentimentResponseDTO(Long id, String text, String productName, SentimentType sentiment,
                                   LocalDateTime createdAt) {
}
