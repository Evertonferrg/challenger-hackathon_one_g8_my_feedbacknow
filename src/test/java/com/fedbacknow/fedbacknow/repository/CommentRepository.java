package com.fedbacknow.fedbacknow.repository;

import com.fedbacknow.fedbacknow.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
