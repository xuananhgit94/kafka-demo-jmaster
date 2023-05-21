package com.xuananh.accountservice.controller;

import com.xuananh.accountservice.model.AccountDTO;
import com.xuananh.accountservice.model.MessageDTO;
import com.xuananh.accountservice.model.StatisticDTO;
import com.xuananh.accountservice.repositories.AccountRepo;
import com.xuananh.accountservice.repositories.MessageRepo;
import com.xuananh.accountservice.repositories.StatisticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private StatisticRepo statisticRepo;

    private void accept(SendResult<String, Object> r, Throwable ex) {
        if (Objects.isNull(ex)) {
            Objects.requireNonNull(ex).printStackTrace();
        } else {
            System.out.println(r.getRecordMetadata());
        }
    }

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO account) {
        StatisticDTO statisticDTO = StatisticDTO
                .builder()
                .message("Account " + account.getEmail() + " is created")
                .createDate(new Date())
                .status(false)
                .build();
        MessageDTO messageDTO = MessageDTO
                .builder()
                .to(account.getEmail())
                .toName(account.getName())
                .status(false)
                .subject("Welcome to Xuan Anh")
                .content("Xuan anh is online learning platform.")
                .build();
        accountRepo.save(account);
        messageRepo.save(messageDTO);
        statisticRepo.save(statisticDTO);
        for (var i = 0; i < 100; i++) {
            kafkaTemplate.send("notification", messageDTO).whenComplete(this::accept);
        }
        kafkaTemplate.send("statistic", statisticDTO);
        return account;
    }
}
