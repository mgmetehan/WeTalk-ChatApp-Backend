package com.mgmetehan.WeTalk.service.impl;

import com.mgmetehan.WeTalk.model.Message;
import com.mgmetehan.WeTalk.repository.MessageRepository;
import com.mgmetehan.WeTalk.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}