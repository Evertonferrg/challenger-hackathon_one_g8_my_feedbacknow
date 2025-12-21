package com.fedbacknow.fedbacknow.repository;

import com.fedbacknow.fedbacknow.entity.SocialFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialFeedbackRepository extends JpaRepository<SocialFeedback, Long> {

    boolean existsByExternalId(String externalId);
}
