package com.ck.cafe_back.serviceImpl;

import com.ck.cafe_back.DAO.UserDAO;
import com.ck.cafe_back.JWT.AuthTokenFilter;
import com.ck.cafe_back.JWT.CustomerUserDetailsService;
import com.ck.cafe_back.JWT.JWTUtil;
import com.ck.cafe_back.POJO.User;
import com.ck.cafe_back.constents.CafeConst;
import com.ck.cafe_back.service.UserService;
import com.ck.cafe_back.utils.CafeUtils;
import com.ck.cafe_back.utils.EmailUtils;
import com.ck.cafe_back.wrapper.userWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    AuthTokenFilter authTokenFilter;
    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestmap) {
        log.info("Inside signup {}", requestmap);
        try {
            if (validateSignUpMap(requestmap)) {
                User user = userDAO.findByEmailId(requestmap.get("email"));
                if (Objects.isNull(user)) {
                    String encryptedPass = new BCryptPasswordEncoder().encode(requestmap.get("password"));
                    requestmap.put("password",encryptedPass);
                    userDAO.save(getUserFromMap(requestmap));
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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestmap) {
        log.info("In Login");
        UsernamePasswordAuthenticationToken u= new UsernamePasswordAuthenticationToken(requestmap.get("email"),requestmap.get("password"));
        try {
            Authentication authentication = authenticationManager.authenticate(u);
         log.info("{}",authentication.isAuthenticated());
            if (authentication.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true"))
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail()
                            ,customerUserDetailsService.getUserDetails().getRole())+"\"}",HttpStatus.OK );

                else{
                    return CafeUtils.getResponse("wait for Admin Approval",HttpStatus.BAD_REQUEST);}
            }

        }catch (BadCredentialsException exception) {
            exception.printStackTrace();
        }
        return CafeUtils.getResponse("Bad Credential",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<userWrapper>> getAllUsers() {
        try {
                if (authTokenFilter.isAdmin()){
                    return new ResponseEntity<>(userDAO.getAllUsers(),HttpStatus.OK);
                }else
                    return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (authTokenFilter.isAdmin()){
               Optional<User> optional= userDAO.findById(Integer.parseInt(requestMap.get("id")));
            if (!optional.isEmpty()){
                    userDAO.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmins(requestMap.get("status"),optional.get().getEmail(),userDAO.getAllAdmin());
                    return CafeUtils.getResponse("User Status Updated Successfully",HttpStatus.OK);
            }
            return CafeUtils.getResponse("User id does not exist",HttpStatus.OK);
            }else return CafeUtils.getResponse(CafeConst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private void sendMailToAllAdmins(String status, String user, List<String> allAdmin) {
            allAdmin.remove(authTokenFilter.getCurrentUsername());
            if (status != null && status.equalsIgnoreCase("true")){
                emailUtils.sendSimpleMessage(authTokenFilter.getCurrentUsername(),"Account Approved","USER : -"+user+"\n is approved by \nADMIN : "+authTokenFilter.getCurrentUsername(),allAdmin);
            }else
                emailUtils.sendSimpleMessage(authTokenFilter.getCurrentUsername(),"Account Disabled","USER : -"+user+"\n is disabled by \nADMIN : "+authTokenFilter.getCurrentUsername(),allAdmin);

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
