package com.ck.cafe_back.serviceImpl;

import com.ck.cafe_back.DAO.UserDAO;
import com.ck.cafe_back.POJO.User;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.UserService;
import com.ck.cafe_back.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        log.info("Inside signup {}", requestmap);
        try {
            if (validateSignUpMap(requestmap)) {
                User user = userDAO.findByEmail(requestmap.get("email"));
                if (Objects.isNull(user)) {
                    userDAO.save(getUserFromMap(requestmap));
                    log.info("here");
                    return CafeUtils.getResponse("Successfully registered", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponse("Email already exists", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponse(CafeConst.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestmap) {
        return requestmap.containsKey("name") && requestmap.containsKey("contactNumber") && requestmap.containsKey("email")
                && requestmap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestmap) {
        User user = new User();
        user.setName(requestmap.get("name"));
        user.setContactNumber(requestmap.get("contactNumber"));
        user.setEmail(requestmap.get("email"));
        user.setPassword(requestmap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
