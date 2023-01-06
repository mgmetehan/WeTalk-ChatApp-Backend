package com.mgmetehan.WeTalk.service.impl;

import com.mgmetehan.WeTalk.model.Message;
import com.mgmetehan.WeTalk.repository.MessageRepository;
import com.mgmetehan.WeTalk.service.MessageService;
import com.mgmetehan.WeTalk.shared.encryption.Encryption;
import com.mgmetehan.WeTalk.shared.notification.mail.INotificationSender;
import com.mgmetehan.WeTalk.shared.notification.mail.model.MailNotificationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final INotificationSender notificationSender;
    private Encryption encryption;

    public Message saveMessage(Message message) {
        var encrypted = messageEncrypt(message.getMessage());
        message.setMessage(encrypted.toString());
        var decrypted = messageDecrypt(encrypted);
        return messageRepository.save(message);
    }

    public void mailSender(Message message) {
        List<String> mailList = Arrays.asList("mgmetehan@hotmail.com");

        Set<String> mailSet = new HashSet<>();
        for (String x : mailList) {
            mailSet.add(x);
        }

        var mailNotificationModel = MailNotificationModel.builder()
                .subject("konu")
                .to(mailSet)
                .htmlContent(true)
                .content("bodyMessage")
                .build();

        notificationSender.sendNotification(mailNotificationModel);
    }

    public byte[] messageEncrypt(String message) {
        var secretKey = encryption.getSecretKey();
        var charSet = encryption.getBytes();
        var encryptMessage = encryption.encrypt(message, secretKey, charSet);
        System.out.println("Original message: " + message + "\nEncrypt message: " + encryptMessage);
        return encryptMessage;
    }

    public String messageDecrypt(byte[] encryptMessage) {
        var secretKey = encryption.getSecretKey();
        var charSet = encryption.getBytes();
        var decryptedMessage = encryption.decrypt(encryptMessage, secretKey, charSet);
        System.out.println("Encrypt message: " + encryptMessage + "\nDecrypted message: " + decryptedMessage);
        return decryptedMessage;
    }
}
