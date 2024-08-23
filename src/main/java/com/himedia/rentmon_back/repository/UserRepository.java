package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserid(String userid);

    Optional<User> findByUseridAndEmail(String userid, String email);
}
