package com.fedbacknow.fedbacknow.controller;


import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import org.springframework.http.ResponseEntity;


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

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(name = "hub.mode", required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge", required = false) String challenge
    ) {
        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(403).body("Invalid token");
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(@RequestBody String payload,
                                             HttpServletRequest request) {

        JSONObject json = new JSONObject(payload);

        String object = json.optString("object");

        if ("page".equals(object)) {
            handleFacebookEvent(json);
        } else if ("instagram".equals(object)) {
            handleInstagramEvent(json);
        }

        return ResponseEntity.ok().build();
    }

    private void handleFacebookEvent(JSONObject json) {

        JSONObject entry = json.getJSONObject("entry").getJSONObject(0);
        JSONObject change = entry.getJSONArray("changes").getJSONObject(0);
        JSONObject value = change.getJSONObject("value");

        if (value.has("comment_id")) {
            String commentId = value.getString("comment_id");
            notifyFront("FACEBOOK", commentId);
        }
    }

    private void handleInstagramEvent(JSONObject json) {

        JSONObject entry = json.getJSONArray("entry").getJSONObject(0);
        JSONObject change = entry.getJSONArray("changes").getJSONObject(0);
        JSONObject value = change.getJSONObject("value");

        if (value.has("comment_id")) {
            String commentId = value.getString("comment_id");
            notifyFront("INSTAGRAM", commentId);
        }
    }

    private void notifyFront(String platform, String externalId) {
        System.out.println("🔔 Novo evento: " + platform + " | ID: " + externalId);
    }
}
