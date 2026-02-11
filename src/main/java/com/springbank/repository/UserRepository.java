package com.springbank.repository;

import com.springbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    User findByUserIdAndPassword(String userId, String password);

    boolean existsById(String userId);
}
