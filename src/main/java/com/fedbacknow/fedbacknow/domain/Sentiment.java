package com.fedbacknow.fedbacknow.domain;

import com.fedbacknow.fedbacknow.dto.SentimentRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Table(name = "feedbacks")
@Entity(name = "Sentiment")
@Getter
@Setter
public class Sentiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Column(name = "productname")
    private String productName;

    @Enumerated(EnumType.STRING)
    private SentimentType sentiment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // 🔹 OBRIGATÓRIO para JPA
    protected Sentiment() {
    }

    // 🔹 Usado pelo fluxo ONNX
    public Sentiment(String text, String productName, SentimentType sentiment) {
        this.text = text;
        this.productName = productName;
        this.sentiment = sentiment;
    }

    // 🔹 Opcional (se quiser manter criação direta do DTO)
    public Sentiment(SentimentRequestDTO dados, SentimentType sentiment) {
        this.text = dados.text();
        this.productName = dados.productName();
        this.sentiment = sentiment;
    }
}
