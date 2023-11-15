package com.ck.cafe_back.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Entity
@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "user_table")
@NamedQuery(name = "User.findByEmailId",query = "select u from User u Where u.email=:email")
@NamedQuery(name = "User.getAllUsers",query = "select new com.ck.cafe_back.wrapper.userWrapper() from User u where u.role='user'")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "passowrd")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;


}
