package com.xuananh.accountservice.repositories;

import com.xuananh.accountservice.model.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountDTO, Integer> {
}
