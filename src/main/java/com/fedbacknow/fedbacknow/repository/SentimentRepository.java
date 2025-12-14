package com.fedbacknow.fedbacknow.repository;

import com.fedbacknow.fedbacknow.domain.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentimentRepository extends JpaRepository<Sentiment, Long> {
}
