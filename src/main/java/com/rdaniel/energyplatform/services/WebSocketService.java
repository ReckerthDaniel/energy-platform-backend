package com.rdaniel.energyplatform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate simpleMessageTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate simpleMessageTemplate) {
        this.simpleMessageTemplate = simpleMessageTemplate;
    }

    public void sendMessage(String username, String payloadMessage) {
        simpleMessageTemplate.convertAndSend("/topic/" + username, payloadMessage);
    }
}
