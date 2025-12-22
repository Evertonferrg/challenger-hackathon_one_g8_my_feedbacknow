package com.fedbacknow.fedbacknow.service;

import com.fedbacknow.fedbacknow.domain.SentimentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SentimentService {

    // Palavras-chave com pesos
    private static final Map<String, Integer> POSITIVE_KEYWORDS = new HashMap<>();
    private static final Map<String, Integer> NEGATIVE_KEYWORDS = new HashMap<>();

    static {
        // Palavras positivas com peso
        POSITIVE_KEYWORDS.put("adorei", 3);
        POSITIVE_KEYWORDS.put("excelente", 3);
        POSITIVE_KEYWORDS.put("maravilhoso", 3);
        POSITIVE_KEYWORDS.put("perfeito", 3);
        POSITIVE_KEYWORDS.put("ótimo", 2);
        POSITIVE_KEYWORDS.put("bom", 2);
        POSITIVE_KEYWORDS.put("gostei", 2);
        POSITIVE_KEYWORDS.put("recomendo", 2);
        POSITIVE_KEYWORDS.put("qualidade", 1);
        POSITIVE_KEYWORDS.put("eficiente", 1);

        // Palavras negativas com peso
        NEGATIVE_KEYWORDS.put("odiei", 3);
        NEGATIVE_KEYWORDS.put("horrível", 3);
        NEGATIVE_KEYWORDS.put("péssimo", 3);
        NEGATIVE_KEYWORDS.put("terrível", 3);
        NEGATIVE_KEYWORDS.put("ruim", 2);
        NEGATIVE_KEYWORDS.put("decepcionante", 2);
        NEGATIVE_KEYWORDS.put("problema", 2);
        NEGATIVE_KEYWORDS.put("defeito", 2);
        NEGATIVE_KEYWORDS.put("não gostei", 2);
        NEGATIVE_KEYWORDS.put("caro", 1);
        NEGATIVE_KEYWORDS.put("lento", 1);
    }

    public SentimentType analyzeSentiment(String text) {
        if (text == null || text.trim().isEmpty()) {
            return SentimentType.NEUTRAL;
        }

        String lower = text.toLowerCase().trim();
        System.out.println("🔍 Analisando: \"" + lower + "\"");

        int positiveScore = calculateScore(lower, POSITIVE_KEYWORDS);
        int negativeScore = calculateScore(lower, NEGATIVE_KEYWORDS);

        System.out.println("   Pontuação: +" + positiveScore + " / -" + negativeScore);

        if (positiveScore > negativeScore) {
            return SentimentType.POSITIVE;
        } else if (negativeScore > positiveScore) {
            return SentimentType.NEGATIVE;
        } else {
            return SentimentType.NEUTRAL;
        }
    }

    private int calculateScore(String text, Map<String, Integer> keywords) {
        int score = 0;
        for (Map.Entry<String, Integer> entry : keywords.entrySet()) {
            if (text.contains(entry.getKey())) {
                score += entry.getValue();
                System.out.println("   Encontrado: '" + entry.getKey() + "' (+" + entry.getValue() + ")");
            }
        }
        return score;
    }

    // Outros métodos mantidos...
    public SentimentType analyze(String text) {
        return analyzeSentiment(text);
    }

    public Map<String, Object> analyzeWithDetails(String text) {
        SentimentType sentiment = analyzeSentiment(text);

        Map<String, Object> result = new HashMap<>();
        result.put("text", text);
        result.put("sentiment", sentiment.toString());
        result.put("portugues", translateToPortuguese(sentiment));

        return result;
    }

    private String translateToPortuguese(SentimentType sentiment) {
        switch (sentiment) {
            case POSITIVE:
                return "POSITIVO";
            case NEGATIVE:
                return "NEGATIVO";
            case NEUTRAL:
                return "NEUTRO";
            default:
                return "INDETERMINADO";
        }
    }
}
