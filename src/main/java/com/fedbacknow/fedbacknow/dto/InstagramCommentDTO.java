package com.fedbacknow.fedbacknow.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
public class InstagramCommentDTO {

    private String text;
    private String username;
    private String timestamp;

    public LocalDateTime getTimestampAsLocalDateTime() {
        return OffsetDateTime.parse(timestamp).toLocalDateTime();
    }


}
