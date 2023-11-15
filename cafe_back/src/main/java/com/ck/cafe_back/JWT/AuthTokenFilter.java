package com.ck.cafe_back.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomerUserDetailsService service;

    Claims claims = null;
    String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().matches("user/login|/user/forgotPassword|/user/signup")){
            filterChain.doFilter(request,response);
        }else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUserName(token);
                claims = jwtUtil.extractAllClaims(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = service.loadUserByUsername(username);
                if (jwtUtil.validateToken(token,userDetails));{
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request,response);

        }


    }
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }
    public boolean isUser(){
        return "User".equalsIgnoreCase((String) claims.get("role"));
    }
    public String getCurrentUsername(){
        return username;
    }
}
