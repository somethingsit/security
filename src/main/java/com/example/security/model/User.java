package com.example.security.model;

import com.example.security.fw.enumerate.StatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", nullable = true, unique = true)
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;
}
