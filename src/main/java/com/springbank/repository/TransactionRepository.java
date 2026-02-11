package com.springbank.repository;

import com.springbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserIdAndAccId(String uid , int id);
    List<Transaction> getByTypeOrderByUserIdAsc(String type);
}
