package com.example.statisticservice.services;

import com.example.statisticservice.entities.Statistic;
import com.example.statisticservice.repositories.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticRepo statisticRepo;

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
//        statisticRepo.save(statistic);
        throw new RuntimeException();
    }

    @KafkaListener(id = "dltGroup", topics = "statistic.DLT")
    public void dltListen(Statistic statistic) {
        logger.info("Received statistic.DLT" + statistic.getMessage());
    }
}
