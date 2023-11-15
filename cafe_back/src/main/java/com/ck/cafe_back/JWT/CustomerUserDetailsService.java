package com.ck.cafe_back.JWT;

import com.ck.cafe_back.DAO.UserDAO;
import com.ck.cafe_back.POJO.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserDAO userDAO;
    @Getter
    private User userDetails;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetails = userDAO.findByEmailId(username);
        if (!Objects.isNull(userDetails))
            return new org.springframework.security.core.userdetails.User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not Found");
    }

}
