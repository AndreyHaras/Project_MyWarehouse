package com.myprogect.mywarehouse.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity()
@Table(name = "операции")
@Data
@Accessors(chain = true)
public class Operations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "вид_операции")
    private String typeOfOperation;

}
