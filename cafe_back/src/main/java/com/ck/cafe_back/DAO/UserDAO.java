package com.ck.cafe_back.DAO;

import com.ck.cafe_back.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
