package com.fedbacknow.fedbacknow.controlller;

import com.fedbacknow.fedbacknow.dto.SentimentRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    @PostMapping
    public void create(@RequestBody SentimentRequestDTO dados){
        System.out.println(dados);

    }
}
