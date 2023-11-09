package com.ck.cafe_back.DAO;

import com.ck.cafe_back.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {

}
