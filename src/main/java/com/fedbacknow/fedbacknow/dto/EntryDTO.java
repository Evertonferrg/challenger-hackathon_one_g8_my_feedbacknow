package com.fedbacknow.fedbacknow.dto;

import java.util.List;

public class EntryDTO {

    private String id;
    private Long time;
    private List<ChangeDTO> changes;

    public String getId(){
        return id;
    }

    public List<ChangeDTO> getChanges() {
        return changes;
    }
}
