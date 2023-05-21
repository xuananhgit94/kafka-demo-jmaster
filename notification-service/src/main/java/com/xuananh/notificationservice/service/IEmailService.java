package com.xuananh.notificationservice.service;

import com.xuananh.notificationservice.model.MessageDTO;

public interface IEmailService {
    void sendEmail(MessageDTO messageDTO);
}
