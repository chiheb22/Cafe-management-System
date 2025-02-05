package com.ck.cafe_back.RESTImpl;

import com.ck.cafe_back.REST.UserRest;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.UserService;
import com.ck.cafe_back.utils.CafeUtils;
import com.ck.cafe_back.wrapper.userWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        try {
            return userService.signup(requestmap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestmap) {
        try {
            return userService.login(requestmap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<userWrapper>> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<userWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    @Override
    public ResponseEntity<String> checkToken() {

        try {
            return userService.checkToken();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

