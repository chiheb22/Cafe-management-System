package com.ck.cafe_back.RESTImpl;

import com.ck.cafe_back.REST.UserRest;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.UserService;
import com.ck.cafe_back.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        try {
            userService.signup(requestmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
