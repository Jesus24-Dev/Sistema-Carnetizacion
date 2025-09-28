
package com.jesus24dev.carnetizacion.controllers;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        return message;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToClients(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
