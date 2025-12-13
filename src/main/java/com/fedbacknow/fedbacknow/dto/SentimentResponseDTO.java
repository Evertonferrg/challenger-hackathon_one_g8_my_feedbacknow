package com.fedbacknow.fedbacknow.dto;

import com.fedbacknow.fedbacknow.domain.SentimentType;

public record SentimentResponseDTO(String text, String productName, SentimentType sentiment) {
}
