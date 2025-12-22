package com.fedbacknow.fedbacknow.service;

import ai.onnxruntime.*;
import com.fedbacknow.fedbacknow.domain.SentimentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class SentimentAnalysisService {
    public SentimentType analyzeSentiment(String text) {
        System.out.println("📝 Analisando: " + text);

        // Apenas análise por palavras-chave (funciona SEMPRE)
        return analyzeWithKeywords(text);
    }

    private SentimentType analyzeWithKeywords(String text) {
        String lower = text.toLowerCase();

        if (lower.contains("adorei") || lower.contains("excelente") ||
                lower.contains("ótimo") || lower.contains("bom")) {
            return SentimentType.POSITIVE;
        }

        if (lower.contains("odiei") || lower.contains("ruim") ||
                lower.contains("péssimo") || lower.contains("horrível")) {
            return SentimentType.NEGATIVE;
        }

        return SentimentType.NEUTRAL;
    }
}
