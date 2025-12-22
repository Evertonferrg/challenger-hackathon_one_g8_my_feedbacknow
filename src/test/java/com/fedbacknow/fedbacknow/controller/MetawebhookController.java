package com.fedbacknow.fedbacknow.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fedbacknow.fedbacknow.entity.Comment;
import com.fedbacknow.fedbacknow.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/webhook/meta")
public class MetawebhookController {

    private static final String VERIFY_TOKEN = "feedbacknow_webhook_2025";
    private static final Logger logger = LoggerFactory.getLogger(MetawebhookController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;
    private final CommentRepository commentRepository;

    public MetawebhookController(SimpMessagingTemplate messagingTemplate,
                                 CommentRepository commentRepository) {
        this.messagingTemplate = messagingTemplate;
        this.commentRepository = commentRepository;
    }

    // Verificação do webhook
    @GetMapping
    public String verifyWebhook(@RequestParam(name = "hub.mode", required = false) String mode,
                                @RequestParam(name = "hub.verify_token", required = false) String token,
                                @RequestParam(name = "hub.challenge", required = false) String challenge) {
        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            logger.info("Webhook verificado com sucesso!");
            return challenge;
        }
        return "Invalid token";
    }

    // Recebimento de eventos do Meta
    @PostMapping
    public void receiveEvent(@RequestBody String payload) {
        try {
            JsonNode json = objectMapper.readTree(payload);
            String object = json.path("object").asText();

            for (JsonNode entry : json.path("entry")) {
                for (JsonNode change : entry.path("changes")) {
                    JsonNode value = change.path("value");

                    if (value.has("comment_id")) {
                        String commentId = value.path("comment_id").asText();
                        String platform = detectPlatform(value, object);

                        // Envia notificação em tempo real para o front-end
                        messagingTemplate.convertAndSend("/topic/notifications",
                                new NotificationDTO(platform, commentId));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao processar webhook", e);
        }
    }

    // Endpoint para analisar comentário ao clicar no front-end
    @PostMapping("/comments/{commentId}/analyze")
    public Comment analyzeComment(@PathVariable String commentId,
                                  @RequestParam String platform) {
        // Aqui você buscaria o comentário via API do Facebook/Instagram
        String content = "Simulação de texto do comentário"; // substituir por chamada real

        // Simulação de análise de sentimento
        String sentiment = analyzeSentiment(content);

        // Salvar no banco
        Comment comment = new Comment();
        comment.setExternalId(commentId);
        comment.setPlatform(platform);
        comment.setContent(content);
        comment.setSentiment(sentiment);

        return commentRepository.save(comment);
    }

    // Método simples de análise de sentimento (pode chamar API real)
    private String analyzeSentiment(String text) {
        return "POSITIVE"; // ex.: POSITIVE, NEGATIVE, NEUTRAL
    }

    // Detecta plataforma com base no payload
    private String detectPlatform(JsonNode value, String object) {
        if (value.has("instagram_business_account_id")) {
            return "INSTAGRAM";
        } else if ("page".equals(object)) {
            return "FACEBOOK";
        } else {
            return "UNKNOWN";
        }
    }

    // DTO para enviar notificação em tempo real
    public record NotificationDTO(String platform, String commentId) {}
}
