package com.chickencurry.wizardbackend.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage {
    private String sender;
    private String message;
    private String date;
    private Status status;
}
