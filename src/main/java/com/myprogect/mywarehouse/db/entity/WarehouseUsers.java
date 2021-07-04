package com.myprogect.mywarehouse.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "пользователи")
@Data
@Accessors(chain = true)
public class WarehouseUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "имя_пользователя")
    private String userName;

    @Column(name = "пароль_пользователя")
    private String userPassword;

    @Column(name = "роль")
    private String userRole;

    @Column(name = "доступ")
    private Boolean access;
}
