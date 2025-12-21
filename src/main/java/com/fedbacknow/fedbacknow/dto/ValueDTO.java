package com.fedbacknow.fedbacknow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValueDTO {

    @JsonProperty("comment_id")
    private String commentId;

    @JsonProperty("media_id")
    private String mediaId;

    public String getCommentId(){
        return commentId;
    }

    public String getMediaId(){
        return mediaId;
    }


}
