package com.xuananh.accountservice.repositories;

import com.xuananh.accountservice.model.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageDTO, Integer> {
    List<MessageDTO> findByStatus(boolean status);
}
