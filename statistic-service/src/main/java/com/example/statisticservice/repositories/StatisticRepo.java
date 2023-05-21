package com.example.statisticservice.repositories;
import com.example.statisticservice.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepo extends JpaRepository<Statistic, Integer> {
}
