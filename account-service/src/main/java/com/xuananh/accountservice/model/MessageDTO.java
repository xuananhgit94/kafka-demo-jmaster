package com.xuananh.accountservice.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "to_email")
    private String to;
    private String toName;
    private String subject;
    private String content;
    private boolean status;
}
