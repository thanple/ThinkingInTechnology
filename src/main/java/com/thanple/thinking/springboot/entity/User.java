package com.thanple.thinking.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thanple on 2017/2/20.
 */
@Entity // This tells Hibernate to make a table out of this class
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @OneToMany(cascade={CascadeType.ALL})
    @JoinTable(name="friend",joinColumns={@JoinColumn(name="id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="as_id",referencedColumnName="id")})
    private List<User> friends = new ArrayList<>();
}
