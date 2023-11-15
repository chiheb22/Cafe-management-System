package com.ck.cafe_back.service;

import com.ck.cafe_back.wrapper.userWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signup(Map<String, String> requestmap);
    ResponseEntity<String> login(Map<String, String> requestmap);

    ResponseEntity<List<userWrapper>> getAllUsers();
}
