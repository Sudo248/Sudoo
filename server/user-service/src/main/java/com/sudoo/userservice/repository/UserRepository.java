package com.sudoo.userservice.repository;

import com.sudoo.userservice.repository.entitity.User;
import com.sudoo.userservice.repository.entitity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT ui FROM UserInfo ui WHERE ui.userId = :userId")
    UserInfo getUserInfoById(@Param("userId") String userId);
}
