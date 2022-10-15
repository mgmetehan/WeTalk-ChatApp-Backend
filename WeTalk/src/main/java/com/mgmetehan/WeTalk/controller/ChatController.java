package com.mgmetehan.WeTalk.controller;

import com.mgmetehan.WeTalk.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController  //@Controller ?
@RequiredArgsConstructor
public class ChatController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message msg){
        return msg;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message msg){
        simpMessagingTemplate.convertAndSendToUser(msg.getReceiverName(),"/private",msg);
        System.out.println(msg.toString());
        return msg;
    }
}
