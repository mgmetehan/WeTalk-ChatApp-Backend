package com.mgmetehan.WeTalk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Messages")
@Builder
public class Message extends BaseMessage {
    private String senderName;
    private String receiverName;
    private String message;
    private Status status;
}
