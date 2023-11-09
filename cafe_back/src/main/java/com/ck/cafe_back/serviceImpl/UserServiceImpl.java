package com.ck.cafe_back.serviceImpl;

import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.UserService;
import com.ck.cafe_back.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        log.info("Inside signup {}",requestmap);
        if (validateSignUpMap(requestmap)){

        }else {
            return CafeUtils.getResponse(CafeConst.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);
        }
        return CafeUtils.getResponse();
    }
    private boolean validateSignUpMap (Map<String,String> requestmap){
      return requestmap.containsKey("name") && requestmap.containsKey("contactNumber") && requestmap.containsKey("email")
               && requestmap.containsKey("password") && requestmap.containsKey("status") && requestmap.containsKey("role");
    }
}
