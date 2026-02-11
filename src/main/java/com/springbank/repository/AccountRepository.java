package com.springbank.repository;


import com.springbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByUserId(String userId);

    boolean existsByAccountIdAndUserId(int accNo, String userId);

    boolean existsByAccountId(int AccNo);

    Account findByAccountIdAndUserId(int accNo, String userId);
}
