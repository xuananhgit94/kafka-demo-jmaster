package com.xuananh.accountservice.repositories;

import com.xuananh.accountservice.model.StatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticRepo extends JpaRepository<StatisticDTO, Integer> {
    List<StatisticDTO> findByStatus(boolean status);
}
