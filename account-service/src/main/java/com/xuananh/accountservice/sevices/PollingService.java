package com.xuananh.accountservice.sevices;

import com.xuananh.accountservice.model.MessageDTO;
import com.xuananh.accountservice.model.StatisticDTO;
import com.xuananh.accountservice.repositories.MessageRepo;
import com.xuananh.accountservice.repositories.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@EnableScheduling
public class PollingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private StatisticRepo statisticRepo;

    @Scheduled(fixedDelay = 1000)
    public void producer() {
        List<MessageDTO> messageDTOS = messageRepo.findByStatus(false);
        for (var messageDTO : messageDTOS) {
            kafkaTemplate.send("notification", messageDTO).whenComplete((r, ex) -> {
                if (!Objects.isNull(ex)) {
                    logger.info("Fail " + ex);
                } else {
                    logger.info("SUCCESS");
                    messageDTO.setStatus(true);
                    messageRepo.save(messageDTO);
                }
            });
        }

        List<StatisticDTO> statisticDTOS = statisticRepo.findByStatus(false);
        for (var statisticDTO : statisticDTOS) {
            kafkaTemplate.send("notification", statisticDTO).whenComplete((r, ex) -> {
                if (!Objects.isNull(ex)) {
                    logger.info("Fail " + ex);
                } else {
                    logger.info("SUCCESS");
                    statisticDTO.setStatus(true);
                    statisticRepo.save(statisticDTO);
                }
            });
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        logger.info("delete All");
        List<MessageDTO> messageDTOS = messageRepo.findByStatus(true);
        List<StatisticDTO> statisticDTOS = statisticRepo.findByStatus(true);

        messageRepo.deleteAll(messageDTOS);
        statisticRepo.deleteAll(statisticDTOS);
    }
}
