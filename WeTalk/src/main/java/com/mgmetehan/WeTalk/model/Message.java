package com.mgmetehan.WeTalk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    // private String date = String.valueOf(new Date());
    private Status status;

    public Message(String senderName, String receiverName, String message) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.message = message;
    }
}
