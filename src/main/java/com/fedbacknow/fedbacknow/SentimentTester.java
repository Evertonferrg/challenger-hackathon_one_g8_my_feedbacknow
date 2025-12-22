package com.fedbacknow.fedbacknow;

import com.fedbacknow.fedbacknow.service.SentimentAnalysisService;
import com.fedbacknow.fedbacknow.service.SentimentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SentimentTester {

    @Autowired
    private SentimentService service; // Mudar para SentimentService

    @PostConstruct
    public void testSentiments() {
        System.out.println("\n=== TESTANDO SENTIMENT SERVICE ===");

        String[] testTexts = {
                "adorei o produto",
                "excelente qualidade",
                "não gostei, produto ruim",
                "odiei, péssima experiência"
        };

        for (String text : testTexts) {
            var result = service.analyzeSentiment(text);
            System.out.printf("  \"%s\" -> %s\n",
                    text.length() > 30 ? text.substring(0, 30) + "..." : text,
                    result);
        }
    }
}
