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
    private String product;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Sentiment(SentimentRequestDTO dados){
        this.text = dados.text();
        this.product = dados.productName();
    }

}
