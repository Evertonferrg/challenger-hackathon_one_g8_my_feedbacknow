package com.fedbacknow.fedbacknow.service;

import com.fedbacknow.fedbacknow.dto.InstagramCommentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstagramService {

    @Value("${meta.page.token}")
    private String accessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public InstagramCommentDTO getComment(String commentId) {

        String url =" https://graph.facebook.com/v18.0/"+ commentId +"?fields=text,username,timestamp"
                + "&access_token=" + accessToken;

        return restTemplate.getForObject(url, InstagramCommentDTO.class);
    }

}
