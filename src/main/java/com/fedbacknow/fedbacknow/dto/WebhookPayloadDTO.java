package com.fedbacknow.fedbacknow.dto;

import java.util.List;

public class WebhookPayloadDTO {

    private String object;
    private List<EntryDTO> entry;

    public String getObject() {
        return object;
    }

    public List<EntryDTO> getEntry(){
        return entry;
    }
}
