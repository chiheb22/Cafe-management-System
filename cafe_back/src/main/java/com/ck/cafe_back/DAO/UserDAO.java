package com.ck.cafe_back.DAO;

import com.ck.cafe_back.POJO.User;
import com.ck.cafe_back.wrapper.userWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmailId(@Param("email") String email);
    List<userWrapper> getAllUsers();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status,@Param("id") Integer id);
    List<String> getAllAdmin();
}
